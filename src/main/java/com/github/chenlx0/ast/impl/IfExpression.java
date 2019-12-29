package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class IfExpression implements Expression {
    public Expression Condition;

    public BlockStatements Blocks;

    public IfExpression(Expression condition, BlockStatements blocks) {
        this.Condition = condition;
        this.Blocks = blocks;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.IF;
    }
}
