package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class WhileExpression implements Expression {
    private Token token;

    public Expression condition;

    public BlockStatements blocks;

    public WhileExpression(Token token, Expression condition, BlockStatements blocks) {
        this.token = token;
        this.condition = condition;
        this.blocks = blocks;
    }

    @Override
    public Token currentToken() {
        return token;
    }

    public BlockStatements getBlocks() {
        return blocks;
    }

    public Expression getCondition() {
        return condition;
    }
}
