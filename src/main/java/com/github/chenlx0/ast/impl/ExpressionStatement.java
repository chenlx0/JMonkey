package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.ast.NodeType;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class ExpressionStatement implements Statement {

    private Expression expression;

    @Override
    public NodeType nodeType() {
        return NodeType.EXPRESSION_STATEMENT;
    }

    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
