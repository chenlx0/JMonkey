package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class InfixExpression implements Expression {
    private Expression LeftExpression;

    private Expression RightExpression;

    private Token.TokenType operatorTokenType;

    public InfixExpression(Expression left, Expression right, Token.TokenType operatorTokenType) {
        this.LeftExpression = left;
        this.RightExpression = right;
        this.operatorTokenType = operatorTokenType;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.INFIX;
    }

    public Expression getLeftExpression() {
        return LeftExpression;
    }

    public Expression getRightExpression() {
        return RightExpression;
    }

    public Token.TokenType getOperatorTokenType() {
        return operatorTokenType;
    }
}
