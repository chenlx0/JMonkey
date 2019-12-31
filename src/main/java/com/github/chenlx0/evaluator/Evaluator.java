package com.github.chenlx0.evaluator;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.Node;
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

    public void evalProgram(Program program) {

    }

    public Object eval(Node node, Environment env) {
        switch (node.nodeType()) {
            case NUMBER: case STRING: case BOOLEAN:
                return evalSingle((Expression) node);
            case LET:
                return evalLet((LetStatement) node, env);
            case ARRAY:
                return evalArray((ArrayExpression) node, env);
            case DICT:
                return evalDict((DictionaryExpression) node, env);
            case PREFIX:
                return evalPrefix((PrefixExpression) node, env);
            case INFIX:
                return evalInfix((InfixExpression) node, env);
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
            case PLUS:
                return evalInfixPlus(infixExpression, env);
            case MULTI:

        }

        return null;
    }

    private Object evalInfixPlus(InfixExpression infixExpression, Environment env) {
        Object leftVal = eval(infixExpression.getLeftExpression(), env);
        Object rightVal = eval(infixExpression.getRightExpression(), env);
        String leftType = leftVal.getClass().getName();
        String rightType = rightVal.getClass().getName();

        if (leftType.equals(rightType)) {
            if (leftType.equals("java.lang.Integer")) {
                return (Integer) leftVal + (Integer) rightVal;
            } else if (leftType.equals("java.lang.Double")) {
                return (Double) leftVal + (Double) rightVal;
            } else if (leftType.equals("java.lang.String")) {
                return (String) leftVal + (String) rightVal;
            }

            throw new EvalException(String.format("can not apply infix '%s' to %s and %s",
                    infixExpression.getOperatorTokenType().name(), typeMap.get(leftType), typeMap.get(rightType)));
        } else {
            // only available when left and right are both number (Double and Integer)
            if (leftType.equals("java.lang.Integer") && rightType.equals("java.lang.Double")) {
                return (Integer) leftVal + (Double) rightVal;
            } else if (rightType.equals("java.lang.Integer") && leftType.equals("java.lang.Double")) {
                return (Double) leftVal + (Integer) rightVal;
            }

            throw new EvalException(String.format("can not apply infix '%s' to %s and %s",
                    infixExpression.getOperatorTokenType().name(), typeMap.get(leftType), typeMap.get(rightType)));
        }
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
}
