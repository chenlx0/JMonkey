package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class InfixExpression implements Expression {
    private Expression LeftExpression;

    private Expression RightExpression;

    public InfixExpression(Expression left, Expression right) {
        this.LeftExpression = left;
        this.RightExpression = right;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.INFIX;
    }

    public Expression getLeftExpression() {
        return LeftExpression;
    }

    public Expression getRightExpression() {
        return RightExpression;
    }
}
