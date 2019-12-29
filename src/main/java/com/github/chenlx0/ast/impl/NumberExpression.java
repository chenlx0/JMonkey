package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;


/**
 * @author: Chen Lixiang
 * @date: 2019-12-21
 */
public class NumberExpression implements Expression {
    private boolean isInteger;

    private Double doubleVal;

    private Integer integerVal;

    @Override
    public NodeType nodeType() {
        return NodeType.NUMBER;
    }

    public NumberExpression(Double val) {
        this.isInteger = false;
        this.doubleVal = val;
    }

    public NumberExpression(Integer val) {
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
