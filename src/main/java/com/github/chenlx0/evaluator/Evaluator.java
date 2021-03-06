package com.github.chenlx0.evaluator;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.Node;
import com.github.chenlx0.ast.NodeType;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.ast.impl.*;
import com.github.chenlx0.builtin.BuiltinFunc;
import com.github.chenlx0.builtin.BuiltinFuncFactory;
import com.github.chenlx0.lexer.Token;
import com.github.chenlx0.util.Consts;
import com.github.chenlx0.util.EvalException;

import java.util.*;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-30
 */
public class Evaluator {

    private Environment globalEnv;

    private static Map<String, String> typeMap;

    static {
        typeMap = new HashMap<>();
        typeMap.put(Consts.ARRAY, "array");
        typeMap.put(Consts.DICT, "dict");
        typeMap.put(Consts.INTEGER, "int");
        typeMap.put(Consts.FLOAT, "float");
        typeMap.put(Consts.STRING, "string");
        typeMap.put(Consts.BOOL, "bool");
    }

    public Evaluator() {
        globalEnv = new Environment();
    }

    public Object evalProgram(Program program) {
        List<Statement> nodes = program.getStatements();
        return evalStatements(nodes, this.globalEnv);
    }

    private Object evalStatements(List<Statement> nodes, Environment env) {
        Object ret = null;
        for (Statement st : nodes) {
            if (st.nodeType() == NodeType.EXPRESSION_STATEMENT) {
                ExpressionStatement est = (ExpressionStatement) st;
                ret = eval(est.getExpression(), env);
            } else {
                ret = eval(st, env);
            }

            if (ret != null && Consts.RET.equals(ret.getClass().getName())) {
                return ret;
            }
        }

        return ret;
    }

    public Object eval(Node node, Environment env) {
        switch (node.nodeType()) {
            case BLOCK_STATEMENT:
                BlockStatements blockStatements = (BlockStatements) node;
                List<Statement> nodes = blockStatements.getStatements();
                return evalStatements(nodes, env);
            case NUMBER:
            case STRING:
            case BOOLEAN:
                return evalSingle((Expression) node);
            case IF:
                return evalIf((IfExpression) node, env);
            case WHILE:
                return evalWhile((WhileExpression) node, env);
            case LET:
                return evalLet((LetStatement) node, env);
            case VARIABLE:
                return evalVariable((VariableExpression) node, env);
            case ARRAY:
                return evalArray((ArrayExpression) node, env);
            case DICT:
                return evalDict((DictionaryExpression) node, env);
            case PREFIX:
                return evalPrefix((PrefixExpression) node, env);
            case INFIX:
                return evalInfix((InfixExpression) node, env);
            case INDEX:
                return evalIndex((IndexExpression) node, env);
            case FUNCTION:
                return evalFunction((FunctionExpression) node, env);
            case CALL:
                return evalCall((CallExpression) node, env);
            case RETURN:
                return evalReturn((ReturnStatement) node, env);
        }

        throw new EvalException("can not eval node type: " + node.nodeType().name());
    }

    private Object evalSingle(Expression exp) {
        switch (exp.nodeType()) {
            case NUMBER:
                NumberExpression numberExp = (NumberExpression) exp;
                if (numberExp.isInteger()) return numberExp.getIntegerVal();
                else return numberExp.getDoubleVal();
            case STRING:
                StringExpression stringExp = (StringExpression) exp;
                return stringExp.getVal();
            case BOOLEAN:
                BooleanExpression boolExp = (BooleanExpression) exp;
                return boolExp.getVal();
        }

        return null;
    }

    private Object evalVariable(VariableExpression varExp, Environment env) {
        String varName = varExp.getLiteral();
        Object res = env.getEnvVar(varName);
        if (res == null) {
            res = BuiltinFuncFactory.getBuiltinFunc(varName);
        }
        return res;
    }

    private Object evalPrefix(PrefixExpression prefixExpression, Environment env) {
        Object val = eval(prefixExpression.getNextExpression(), env);
        String valType = val.getClass().getName();

        switch (prefixExpression.getOperatorTokenType()) {
            case MINUS: {
                if (valType.equals(Consts.FLOAT)) {
                    return -1 * (Double) val;
                } else if (valType.equals(Consts.INTEGER)) {
                    return -1 * (Integer) val;
                } else {
                    throw new EvalException("can not apply prefix '-' to " + typeMap.get(valType));
                }
            }

            case EXCL: {
                if (valType.equals(Consts.BOOL)) {
                    return !((Boolean) val);
                } else {
                    throw new EvalException("can not apply prefix '!' to " + typeMap.get(valType));
                }
            }
        }

        return null;
    }

    private Object evalInfix(InfixExpression infixExpression, Environment env) {
        switch (infixExpression.getOperatorTokenType()) {
            case PLUS:
            case MULTI:
            case MINUS:
            case DIV:
            case EQEQUAL:
            case NOTEQUAL:
            case LESS:
            case GREATER:
            case LESSEQ:
            case GREATEQ:
            case AMPER:
            case XOR:
            case VBAR:
                return evalInfixPuzzle(infixExpression, env);
        }

        throw new EvalException(String.format("can not eval infix operator '%s'", infixExpression.getOperatorTokenType()));
    }

    private Object evalInfixPuzzle(InfixExpression infixExpression, Environment env) {
        Object leftVal = eval(infixExpression.getLeftExpression(), env);
        Object rightVal = eval(infixExpression.getRightExpression(), env);
        String leftType = leftVal.getClass().getName();
        String rightType = rightVal.getClass().getName();

        // if type a number type is double and another type is int
        // convert int to double
        if (leftType.equals(Consts.INTEGER) && rightType.equals(Consts.FLOAT)) {
            leftType = Consts.FLOAT;
            leftVal = Double.valueOf((Integer) leftVal);
        } else if (leftType.equals(Consts.FLOAT) && rightType.equals(Consts.INTEGER)) {
            rightType = Consts.FLOAT;
            rightVal = Double.valueOf((Integer) rightVal);
        }

        if (leftType.equals(rightType)) {
            if (leftType.equals(Consts.INTEGER)) {
                return calculateInteger((Integer) leftVal, (Integer) rightVal, infixExpression.getOperatorTokenType());
            } else if (leftType.equals(Consts.FLOAT)) {
                return calculateDouble((Double) leftVal, (Double) rightVal, infixExpression.getOperatorTokenType());
            } else if (leftType.equals(Consts.BOOL)) {
                Boolean leftValBool = (Boolean) leftVal;
                Boolean rightValBool = (Boolean) rightVal;
                switch (infixExpression.getOperatorTokenType()) {
                    case EQEQUAL:
                        return leftValBool.booleanValue() == rightValBool.booleanValue();
                    case NOTEQUAL:
                        return leftValBool.booleanValue() != rightValBool.booleanValue();
                }
            }

            throw new EvalException(String.format("can not apply infix '%s' to %s and %s",
                    infixExpression.getOperatorTokenType().name(), typeMap.get(leftType), typeMap.get(rightType)));
        } else {
            throw new EvalException(String.format("can not apply infix '%s' to %s and %s",
                    infixExpression.getOperatorTokenType().name(), typeMap.get(leftType), typeMap.get(rightType)));
        }
    }

    private Object calculateInteger(Integer leftValInt, Integer rightValInt, Token.TokenType opToken) {
        switch (opToken) {
            case PLUS:
                return leftValInt + rightValInt;
            case MULTI:
                return leftValInt * rightValInt;
            case MINUS:
                return leftValInt - rightValInt;
            case DIV:
                return leftValInt / rightValInt;
            case EQEQUAL:
                return leftValInt.intValue() == rightValInt.intValue();
            case NOTEQUAL:
                return leftValInt.intValue() != rightValInt.intValue();
            case GREATEQ:
                return leftValInt >= rightValInt;
            case GREATER:
                return leftValInt > rightValInt;
            case LESSEQ:
                return leftValInt <= rightValInt;
            case LESS:
                return leftValInt < rightValInt;
            case AMPER:
                return leftValInt & rightValInt;
            case VBAR:
                return leftValInt | rightValInt;
            case XOR:
                return leftValInt ^ rightValInt;
        }

        throw new EvalException(String.format("can not apply infix '%s' to int", opToken));
    }

    private Object calculateDouble(Double leftValDouble, Double rightValDouble, Token.TokenType opToken) {
        switch (opToken) {
            case PLUS:
                return leftValDouble + rightValDouble;
            case MULTI:
                return leftValDouble * rightValDouble;
            case MINUS:
                return leftValDouble - rightValDouble;
            case DIV:
                return leftValDouble / rightValDouble;
            case EQEQUAL:
                return leftValDouble.doubleValue() == rightValDouble.doubleValue();
            case NOTEQUAL:
                return leftValDouble.doubleValue() != rightValDouble.doubleValue();
            case GREATEQ:
                return leftValDouble >= rightValDouble;
            case GREATER:
                return leftValDouble > rightValDouble;
            case LESSEQ:
                return leftValDouble <= rightValDouble;
            case LESS:
                return leftValDouble < rightValDouble;
        }

        throw new EvalException(String.format("can not apply infix '%s' to float", opToken));
    }

    private Object evalIndex(IndexExpression indexExpression, Environment env) {
        Object leftVal = eval(indexExpression.getLeftExp(), env);
        String leftType = leftVal.getClass().getName();
        Object indexVal = eval(indexExpression.getIndex(), env);

        try {
            if (leftType.equals(Consts.ARRAY)) {
                List<Object> leftArray = (ArrayList<Object>) leftVal;
                if (!indexVal.getClass().getName().equals(Consts.INTEGER)) {
                    throw new EvalException("Array index must be Int, got " + typeMap.get(indexVal.getClass().getName()));
                }
                Integer indexInt = (Integer) indexVal;
                return leftArray.get(indexInt);
            } else if (leftType.equals(Consts.DICT)) {
                Map<String, Object> leftMap = (HashMap<String, Object>) leftVal;
                if (!indexVal.getClass().getName().equals(Consts.STRING)) {
                    throw new EvalException("Array index must be Int, got " + typeMap.get(indexVal.getClass().getName()));
                }
                String indexStr = (String) indexVal;
                return leftMap.get(indexStr);
            }
        } catch (java.lang.IndexOutOfBoundsException except) {
            throw new EvalException("Index out of range");
        }

        throw new EvalException(String.format("can not apply index operate to type '%s'", leftType));
    }

    private Object evalLet(LetStatement letStatement, Environment env) {
        Object val = eval(letStatement.getExpression(), env);
        String varName = letStatement.getVariable().getLiteral();
        env.setEnvVar(varName, val);
        return null;
    }

    private Object evalArray(ArrayExpression arrayExpression, Environment env) {
        List<Expression> astArray = arrayExpression.getArrayMembers();
        List<Object> retArray = new ArrayList<>();

        for (Expression exp : astArray) {
            Object val = eval(exp, env);
            retArray.add(val);
        }

        return retArray;
    }

    private Object evalDict(DictionaryExpression dictExpression, Environment env) {
        Map<StringExpression, Expression> astDict = dictExpression.getDict();
        Map<String, Object> retDict = new HashMap<>();

        for (StringExpression strExp : astDict.keySet()) {
            Object val = eval(astDict.get(strExp), env);
            retDict.put(strExp.getVal(), val);
        }

        return retDict;
    }

    private Object evalIf(IfExpression ifExpression, Environment env) {
        Boolean condition = (Boolean) eval(ifExpression.getCondition(), env);

        if (condition) {
            return eval(ifExpression.getBlocks(), env);
        }

        return null;
    }

    private Object evalWhile(WhileExpression whileExpression, Environment env) {
        Expression conditionExp = whileExpression.getCondition();
        Object ret = null;

        for (boolean cond = (Boolean) eval(conditionExp, env);
             cond;
             cond = (Boolean) eval(conditionExp, env)) {
            ret = eval(whileExpression.getBlocks(), env);
        }

        return ret;
    }

    private Object evalFunction(FunctionExpression functionExpression, Environment env) {
        return new Function(functionExpression.getParameters(),
                functionExpression.getBlockStatements(), env);
    }

    private Object evalCall(CallExpression callExpression, Environment env) {
        Object leftExp = eval(callExpression.getLeftExp(), env);

        if (leftExp == null) {
            throw new EvalException("dose not exist function");
        }

        if (leftExp instanceof BuiltinFunc) {
            BuiltinFunc func = (BuiltinFunc) leftExp;
            List<Object> params = new LinkedList<>();
            for (Expression e : callExpression.getParameters()) {
                params.add(eval(e, env));
            }
            return func.eval(params);
        }

        if (!Consts.FUNC.equals(leftExp.getClass().getName())) {
            throw new EvalException("variable to be called must be function");
        }

        Function func = (Function) leftExp;
        Environment newEnv = extendFunctionEnv(func, callExpression.getParameters(), env);
        Object ret = eval(func.getBlockStatements(), newEnv);

        // unwrap return object
        if (ret != null && Consts.RET.equals(ret.getClass().getName())) {
            return ((RetObject) ret).getObject();
        }

        return ret;
    }

    private Environment extendFunctionEnv(Function func, List<Expression> parameters, Environment env) {
        Environment newEnv = new Environment();
        newEnv.setOuter(func.getEnv());

        int parametersSize = parameters.size();
        if (func.getVarNames().size() != parametersSize) {
            throw new EvalException("parameters do not match function");
        }

        for (int i = 0; i < parametersSize; i++) {
            newEnv.setEnvVar(func.getVarNames().get(i), eval(parameters.get(i), env));
        }

        return newEnv;
    }

    private Object evalReturn(ReturnStatement returnStatement, Environment env) {
        return new RetObject(eval(returnStatement.getExpression(), env));
    }
}
