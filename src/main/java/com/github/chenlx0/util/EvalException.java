package com.github.chenlx0.util;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-31
 */
public class EvalException extends MonkeyException {
    private String msg;

    public EvalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "[EVAL PROGRAM FAILED] " + msg;
    }
}
