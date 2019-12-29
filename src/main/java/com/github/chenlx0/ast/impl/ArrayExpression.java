package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;
import com.github.chenlx0.lexer.Token;

import java.util.List;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-26
 */
public class ArrayExpression implements Expression {
    private List<Expression> arrayMembers;

    @Override
    public NodeType nodeType() {
        return NodeType.ARRAY;
    }

    public ArrayExpression(List<Expression> arrayMembers) {
        this.arrayMembers = arrayMembers;
    }

    public List<Expression> getArrayMembers() {
        return arrayMembers;
    }
}
