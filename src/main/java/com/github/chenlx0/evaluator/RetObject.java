package com.github.chenlx0.evaluator;

/**
 * @author: Chen Lixiang
 * @date: 2020-01-02
 */
public class RetObject {
    private Object object;

    public RetObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "return object";
    }
}
