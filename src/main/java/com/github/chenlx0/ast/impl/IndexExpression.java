package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class IndexExpression implements Expression {
    public Token token;

    public Token number;

    public IndexExpression(Token token, Token number) {
        assert token.isToken(Token.TokenType.VAR);
        assert number.isToken(Token.TokenType.NUMBER);
        this.token = token;
        this.number = number;
    }

    public Token currentToken() {
        return token;
    }
}
