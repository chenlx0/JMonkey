package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-26
 */
public class VariableExpression implements Expression {
    Token token;

    public VariableExpression(Token token) {
        this.token = token;
    }

    @Override
    public Token currentToken() {
        return token;
    }

    public String getLiteral() {
        return token.getVal();
    }
}
