package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class ReturnStatement implements Statement {
    private Token token; // The Token.RET

    private Expression expression;

    public ReturnStatement(Token token, Expression expression) {
        this.token = token;
        this.expression = expression;
    }

    @Override
    public Token currentToken() {
        return token;
    }

    public Expression getExpression() {
        return expression;
    }
}
