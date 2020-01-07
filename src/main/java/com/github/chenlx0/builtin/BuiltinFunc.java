package com.github.chenlx0.builtin;

import java.util.List;

/**
 * @author: Chen Lixiang
 * @date: 2020-01-07
 */
public interface BuiltinFunc {
    public Object eval(List<Object> params);
}
