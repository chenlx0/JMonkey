package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.lexer.Token;


/**
 * @author: Chen Lixiang
 * @date: 2019-12-21
 */
public class NumberExpression implements Expression {
    private Token token;

    private boolean isInteger;

    private Double doubleVal;

    private Integer integerVal;

    @Override
    public Token currentToken() {
        return token;
    }

    public NumberExpression(Token token, Double val) {
        this.token = token;
        this.isInteger = false;
        this.doubleVal = val;
    }

    public NumberExpression(Token token, Integer val) {
        this.token = token;
        this.isInteger = true;
        this.integerVal = val;
    }

    public Double getDoubleVal() {
        assert !isInteger;
        return doubleVal;
    }

    public Integer getIntegerVal() {
        assert isInteger;
        return integerVal;
    }

    public boolean isInteger() {
        return isInteger;
    }
}
