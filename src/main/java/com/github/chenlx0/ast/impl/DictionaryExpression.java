package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.NodeType;

import java.util.Map;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-30
 */
public class DictionaryExpression implements Expression {

    private Map<StringExpression, Expression> dict;

    public DictionaryExpression(Map<StringExpression, Expression> dict) {
        this.dict = dict;
    }

    public Map<StringExpression, Expression> getDict() {
        return dict;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.DICT;
    }
}
