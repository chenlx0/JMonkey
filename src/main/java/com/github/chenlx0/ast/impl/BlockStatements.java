package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Node;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.ast.NodeType;

import java.util.List;

public class BlockStatements implements Node {
    List<Statement> statements;

    public BlockStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.BLOCK_STATEMENT;
    }

    public List<Statement> getStatements() {
        return statements;
    }
}
