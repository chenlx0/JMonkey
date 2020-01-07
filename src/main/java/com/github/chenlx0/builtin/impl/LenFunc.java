package com.github.chenlx0.builtin.impl;

import com.github.chenlx0.builtin.BuiltinFunc;
import com.github.chenlx0.util.EvalException;

import java.util.List;
import java.util.Map;

/**
 * @author: Chen Lixiang
 * @date: 2020-01-07
 */
public class LenFunc implements BuiltinFunc {
    @Override
    public Object eval(List<Object> params) {
        assert params.size() == 1;
        Object obj = params.get(0);

        if (obj instanceof List) {
            return ((List) obj).size();
        } else if (obj instanceof Map) {
            return ((Map) obj).size();
        }

        throw new EvalException("builtin function 'len' only can apply to array and dict");
    }

    @Override
    public String toString() {
        return "builtin func: len";
    }
}
