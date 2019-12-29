package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-26
 */
public class OperatorPrefixExpression implements Expression {

    private Expression nextExpression;

    @Override
    public NodeType nodeType() {
        return NodeType.PREFIX;
    }

    public Expression getNextExpression() {
        return nextExpression;
    }

    public OperatorPrefixExpression(Expression nextExpression) {
        this.nextExpression = nextExpression;
    }
}
