package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class StringExpression implements Expression {
    private Token token;

    private String val;

    public StringExpression(Token token) {
        this.token = token;
        this.val = token.getVal();
    }

    public Token currentToken() {
        return token;
    }

    public String getVal() {
        return val;
    }
}
