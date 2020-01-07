package com.github.chenlx0.util;

public class ParseException extends MonkeyException {
    private String msg;

    public ParseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "[PARSE CODE FAILED] " + msg;
    }
}
