package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class ExpressionStatement implements Statement {
    private Token token; // first token of expression

    private Expression expression;

    @Override
    public Token currentToken() {
        return token;
    }

    public ExpressionStatement(Token token, Expression expression) {
        this.token = token;
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
