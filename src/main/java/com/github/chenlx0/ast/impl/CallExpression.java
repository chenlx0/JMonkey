package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;

import java.util.List;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-30
 */
public class CallExpression implements Expression {

    private List<Expression> parameters;

    private Expression leftExp;

    public CallExpression(Expression leftExp, List<Expression> parameters) {
        this.parameters = parameters;
        this.leftExp = leftExp;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.CALL;
    }

    public Expression getLeftExp() {
        return leftExp;
    }

    public List<Expression> getParameters() {
        return parameters;
    }
}
