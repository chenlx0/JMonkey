package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class LetStatement implements Statement {
    private Token token; // The Token.LET

    private Token variable;

    private Expression expression;

    public LetStatement(Token token, Token variable, Expression expression) {
        this.token = token;
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public Token currentToken() {
        return token;
    }

    public String getIdentifier() {
        return variable.getVal();
    }

    public Expression getExpression() {
        return expression;
    }
}
