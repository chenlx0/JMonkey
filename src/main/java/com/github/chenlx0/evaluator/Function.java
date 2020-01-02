package com.github.chenlx0.evaluator;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.impl.BlockStatements;

/**
 * @author: Chen Lixiang
 * @date: 2020-01-02
 */
public class Function {
    private Expression conditionExpression;

    private BlockStatements blockStatements;

    public Function(Expression cond, BlockStatements blocks) {
        this.conditionExpression = cond;
        this.blockStatements = blocks;
    }

    public void setConditionExpression(Expression conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public Expression getConditionExpression() {
        return conditionExpression;
    }

    public void setBlockStatements(BlockStatements blockStatements) {
        this.blockStatements = blockStatements;
    }

    public BlockStatements getBlockStatements() {
        return blockStatements;
    }

    @Override
    public String toString() {
        return "function";
    }
}
