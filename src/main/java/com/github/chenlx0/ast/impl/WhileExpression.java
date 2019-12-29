package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class WhileExpression implements Expression {
    public Expression condition;

    public BlockStatements blocks;

    public WhileExpression(Expression condition, BlockStatements blocks) {
        this.condition = condition;
        this.blocks = blocks;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.WHILE;
    }

    public BlockStatements getBlocks() {
        return blocks;
    }

    public Expression getCondition() {
        return condition;
    }
}
