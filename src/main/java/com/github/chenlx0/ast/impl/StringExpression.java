package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class StringExpression implements Expression {
    private String val;

    public StringExpression(Token token) {
        this.val = token.getVal();
    }

    @Override
    public NodeType nodeType() {
        return NodeType.STRING;
    }

    public String getVal() {
        return val;
    }
}
