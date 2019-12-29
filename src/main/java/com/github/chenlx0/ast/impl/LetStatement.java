package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class LetStatement implements Statement {
    private VariableExpression variable;

    private Expression expression;

    public LetStatement(VariableExpression variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.LET;
    }

    public VariableExpression getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }
}
