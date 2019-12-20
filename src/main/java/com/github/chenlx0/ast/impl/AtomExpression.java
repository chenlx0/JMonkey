package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class AtomExpression implements Expression {
    public Token token;

    public AtomExpression(Token token) {
        this.token = token;
    }

    public Token currentToken() {
        return token;
    }
}
