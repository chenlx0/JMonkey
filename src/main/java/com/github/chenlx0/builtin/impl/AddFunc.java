package com.github.chenlx0.builtin.impl;

import com.github.chenlx0.builtin.BuiltinFunc;
import com.github.chenlx0.util.EvalException;

import java.util.List;

/**
 * @author: Chen Lixiang
 * @date: 2020-01-08
 */
public class AddFunc implements BuiltinFunc {
    @Override
    public Object eval(List<Object> params) {
        if (params.size() != 2) {
            throw new EvalException("add only accepts 2 parameters");
        }

        Object array = params.get(0);
        Object member = params.get(1);

        if (!(array instanceof List)) {
            throw new EvalException("add only apply to array");
        }

        ((List) array).add(member);

        return array;
    }
}
