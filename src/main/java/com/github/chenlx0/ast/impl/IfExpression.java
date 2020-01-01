package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class IfExpression implements Expression {
    private Expression condition;

    private BlockStatements blocks;

    public IfExpression(Expression condition, BlockStatements blocks) {
        this.condition = condition;
        this.blocks = blocks;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.IF;
    }

    public Expression getCondition() {
        return condition;
    }

    public BlockStatements getBlocks() {
        return blocks;
    }
}
