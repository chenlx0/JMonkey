package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-26
 */
public class PrefixExpression implements Expression {

    private Expression nextExpression;

    private Token.TokenType operatorTokenType;

    @Override
    public NodeType nodeType() {
        return NodeType.PREFIX;
    }

    public Expression getNextExpression() {
        return nextExpression;
    }

    public Token.TokenType getOperatorTokenType() {
        return operatorTokenType;
    }

    public PrefixExpression(Expression nextExpression, Token.TokenType operatorTokenType) {
        this.nextExpression = nextExpression;
        this.operatorTokenType = operatorTokenType;
    }
}
