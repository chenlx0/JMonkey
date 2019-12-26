package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-26
 */
public class OperatorPrefixExpression implements Expression {

    private Token token;

    private Expression nextExpression;

    public Token currentToken() {
        return token;
    }

    public Expression getNextExpression() {
        return nextExpression;
    }

    public OperatorPrefixExpression(Token token, Expression nextExpression) {
        this.token = token;
        this.nextExpression = nextExpression;
    }
}
