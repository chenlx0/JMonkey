package com.github.chenlx0.evaluator;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.Node;
import com.github.chenlx0.ast.NodeType;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.ast.impl.*;
import com.github.chenlx0.util.EvalException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-30
 */
public class Evaluator {

    private Environment globalEnv;

    private static Map<String, String> typeMap;
    static
    {
        typeMap = new HashMap<>();
        typeMap.put("java.util.ArrayList", "Array");
        typeMap.put("java.util.HashMap", "Dict");
        typeMap.put("java.lang.Integer", "Int");
        typeMap.put("java.lang.Double", "Float");
        typeMap.put("java.lang.String", "String");
        typeMap.put("java.lang.Boolean", "Bool");
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
        }

        return ret;
    }

    public Object eval(Node node, Environment env) {
        switch (node.nodeType()) {
            case NUMBER: case STRING: case BOOLEAN:
                return evalSingle((Expression) node);
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
        return env.getStore().get(varName);
    }

    private Object evalPrefix(PrefixExpression prefixExpression, Environment env) {
        Object val = eval(prefixExpression.getNextExpression(), env);
        String valType = val.getClass().getName();

        switch (prefixExpression.getOperatorTokenType()) {
            case MINUS: {
                if (valType.equals("java.lang.Double")) {
                    return -1 * (Double) val;
                } else if (valType.equals("java.lang.Integer")) {
                    return -1 * (Integer) val;
                } else {
                    throw new EvalException("can not apply prefix '-' to " + typeMap.get(valType));
                }
            }

            case EXCL: {
                if (valType.equals("java.lang.Boolean")) {
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
            case PLUS: case MULTI: case MINUS: case DIV:
            case EQEQUAL: case NOTEQUAL: case LESS: case GREATER:
            case LESSEQ: case GREATEQ:
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
        if (leftType.equals("java.lang.Integer") && rightType.equals("java.lang.Double")) {
            leftType = "java.lang.Double";
            leftVal = Double.valueOf((Integer) leftVal);
        } else if (leftType.equals("java.lang.Double") && rightType.equals("java.lang.Integer")) {
            rightType = "java.lang.Double";
            rightVal = Double.valueOf((Integer) rightVal);
        }

        if (leftType.equals(rightType)) {
            if (leftType.equals("java.lang.Integer")) {
                Integer leftValInt = (Integer) leftVal;
                Integer rightValInt = (Integer) rightVal;
                switch (infixExpression.getOperatorTokenType()) {
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
                }
            } else if (leftType.equals("java.lang.Double")) {
                Double leftValDouble = (Double) leftVal;
                Double rightValDouble = (Double) rightVal;
                switch (infixExpression.getOperatorTokenType()) {
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
            } else if (leftType.equals("java.lang.Boolean")) {
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

    private Object evalIndex(IndexExpression indexExpression, Environment env) {
        Object leftVal = eval(indexExpression.getLeftExp(), env);
        String leftType = leftVal.getClass().getName();
        Object indexVal = eval(indexExpression.getIndex(), env);

        try {
            if (leftType.equals("java.util.ArrayList")) {
                List<Object> leftArray = (ArrayList<Object>) leftVal;
                if (!indexVal.getClass().getName().equals("java.lang.Integer")) {
                    throw new EvalException("Array index must be Int, got " + indexVal.getClass().getName());
                }
                Integer indexInt = (Integer) indexVal;
                return leftArray.get(indexInt);
            } else if (leftType.equals("java.util.HashMap")) {
                Map<String, Object> leftMap = (HashMap<String, Object>) leftVal;
                if (!indexVal.getClass().getName().equals("java.lang.String")) {
                    throw new EvalException("Array index must be Int, got " + indexVal.getClass().getName());
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
        env.getStore().put(varName, val);
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

    private void evalIf(IfExpression ifExpression, Environment env) {

    }
}
