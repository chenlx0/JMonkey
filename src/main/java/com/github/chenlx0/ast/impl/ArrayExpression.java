package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;

import java.util.List;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-26
 */
public class ArrayExpression implements Expression {
    private Token token;

    private List<Expression> arrayMembers;

    @Override
    public Token currentToken() {
        return token;
    }

    public ArrayExpression(Token token, List<Expression> arrayMembers) {
        this.token = token;
        this.arrayMembers = arrayMembers;
    }

    public List<Expression> getArrayMembers() {
        return arrayMembers;
    }
}
