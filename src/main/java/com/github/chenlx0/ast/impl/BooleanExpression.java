package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-25
 */
public class BooleanExpression implements Expression {
    private Boolean val;

    private Token token;

    public BooleanExpression(Boolean val) {
        this.val = val;
    }

    public Token currentToken() {
        return token;
    }

    public Boolean getVal() {
        return val;
    }
}
