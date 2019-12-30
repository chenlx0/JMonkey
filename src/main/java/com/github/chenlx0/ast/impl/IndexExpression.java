package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class IndexExpression implements Expression {
    private Expression index;

    private Expression leftExp;

    public IndexExpression(Expression leftExp, Expression index) {
        this.leftExp = leftExp;
        this.index = index;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.INDEX;
    }

    public Expression getLeftExp() {
        return leftExp;
    }

    public Expression getIndex() {
        return index;
    }
}
