package com.github.chenlx0.builtin;

import com.github.chenlx0.builtin.impl.AddFunc;
import com.github.chenlx0.builtin.impl.LenFunc;
import com.github.chenlx0.builtin.impl.SetFunc;

/**
 * @author: Chen Lixiang
 * @date: 2020-01-07
 */
public class BuiltinFuncFactory {
    public static BuiltinFunc getBuiltinFunc(String name) {
        switch (name) {
            case "len":
                return new LenFunc();
            case "set":
                return new SetFunc();
            case "add":
                return new AddFunc();
        }

        return null;
    }
}
