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

    public Object getEnvVar(String varName) {
        Object ret = store.get(varName);

        if (ret != null) return ret;
        else if (outer == null) return null;

        return outer.getEnvVar(varName);
    }

    public void setEnvVar(String varName, Object var) {
        store.put(varName, var);
    }
}
