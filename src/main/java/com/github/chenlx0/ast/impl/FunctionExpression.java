package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;

import java.util.List;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-28
 */
public class FunctionExpression implements Expression {

    private List<VariableExpression> parameters;

    private BlockStatements blockStatements;

    public FunctionExpression(List<VariableExpression> parameters, BlockStatements blockStatements) {
        this.parameters = parameters;
        this.blockStatements = blockStatements;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.FUNCTION;
    }

    public List<VariableExpression> getParameters() {
        return parameters;
    }

    public BlockStatements getBlockStatements() {
        return blockStatements;
    }
}
