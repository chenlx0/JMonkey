package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class InfixExpression implements Expression {
    private Token token;

    public Expression LeftExpression;

    public Expression RightExpression;

    public InfixExpression(Token token, Expression left, Expression right) {
        this.token = token;
        this.LeftExpression = left;
        this.RightExpression = right;
    }

    public Token currentToken() {
        return token;
    }
}
