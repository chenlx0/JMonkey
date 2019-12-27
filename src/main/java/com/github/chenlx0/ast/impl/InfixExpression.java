package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class InfixExpression implements Expression {
    private Token token;

    private Expression LeftExpression;

    private Expression RightExpression;

    public InfixExpression(Token token, Expression left, Expression right) {
        this.token = token;
        this.LeftExpression = left;
        this.RightExpression = right;
    }

    @Override
    public Token currentToken() {
        return token;
    }

    public Expression getLeftExpression() {
        return LeftExpression;
    }

    public Expression getRightExpression() {
        return RightExpression;
    }
}
