package com.github.chenlx0.evaluator;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.impl.BlockStatements;
import com.github.chenlx0.ast.impl.VariableExpression;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: Chen Lixiang
 * @date: 2020-01-02
 */
public class Function {
    private List<String> varNames;

    private BlockStatements blockStatements;

    private Environment env;

    public Function(List<VariableExpression> vars, BlockStatements blocks, Environment env) {
        this.varNames = new LinkedList<>();
        for (VariableExpression var : vars) varNames.add(var.getLiteral());
        this.blockStatements = blocks;
        this.env = env;
    }

    public BlockStatements getBlockStatements() {
        return blockStatements;
    }

    public Environment getEnv() {
        return env;
    }

    public List<String> getVarNames() {
        return varNames;
    }

    @Override
    public String toString() {
        return "function";
    }
}
