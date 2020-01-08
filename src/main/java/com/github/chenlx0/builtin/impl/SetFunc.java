package com.github.chenlx0.builtin.impl;

import com.github.chenlx0.builtin.BuiltinFunc;
import com.github.chenlx0.util.EvalException;

import java.util.List;
import java.util.Map;

/**
 * @author: Chen Lixiang
 * @date: 2020-01-08
 */
public class SetFunc implements BuiltinFunc {
    @Override
    public Object eval(List<Object> params) {
        if (params.size() != 3) {
            throw new EvalException("set only accepts 3 parameters");
        }
        Object dict = params.get(0);
        Object key = params.get(1);
        Object val = params.get(2);

        if (!(dict instanceof Map)) {
            throw new EvalException("set only apply to dict");
        }
        if (!(key instanceof String)) {
            throw new EvalException("dict key must be string");
        }

        ((Map) dict).put(((String) key), val);

        return dict;
    }
}
