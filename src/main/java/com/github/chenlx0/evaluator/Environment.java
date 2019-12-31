package com.github.chenlx0.evaluator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-30
 */
public class Environment {

    private Environment outer;

    private Map<String, Object> store;

    public Environment() {
        this.store = new HashMap<>();
    }

    public Environment getOuter() {
        return outer;
    }

    public void setOuter(Environment outer) {
        this.outer = outer;
    }

    public Map<String, Object> getStore() {
        return store;
    }

    public void setStore(Map<String, Object> store) {
        this.store = store;
    }
}
