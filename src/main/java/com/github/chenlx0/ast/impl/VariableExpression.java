package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;
import com.github.chenlx0.ast.NodeType;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-26
 */
public class VariableExpression implements Expression {

    private String variableName;

    public VariableExpression(Token token) {
        this.variableName = token.getVal();
    }

    @Override
    public NodeType nodeType() {
        return NodeType.VARIABLE;
    }

    public String getLiteral() {
        return variableName;
    }
}
