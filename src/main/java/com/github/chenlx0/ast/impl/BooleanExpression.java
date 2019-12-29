package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-25
 */
public class BooleanExpression implements Expression {
    private Boolean val;

    public BooleanExpression(Boolean val) {
        this.val = val;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.BOOLEAN;
    }

    public Boolean getVal() {
        return val;
    }
}
