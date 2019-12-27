package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class IfExpression implements Expression {
    private Token token;

    public Expression Condition;

    public BlockStatements Blocks;

    public IfExpression(Token token, Expression condition, BlockStatements blocks) {
        this.token = token;
        this.Condition = condition;
        this.Blocks = blocks;
    }

    @Override
    public Token currentToken() {
        return token;
    }
}
