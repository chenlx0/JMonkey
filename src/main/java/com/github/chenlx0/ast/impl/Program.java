package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Node;
import com.github.chenlx0.ast.NodeType;
import com.github.chenlx0.ast.Statement;

import java.util.LinkedList;
import java.util.List;

public class Program implements Node {

    private List<Statement> statements;

    public Program() {
        statements = new LinkedList<>();
    }

    public void add(Statement statement) {
        statements.add(statement);
    }

    public NodeType nodeType() {
        if (statements.isEmpty())
            return null;
        return statements.get(0).nodeType();
    }

    public List<Statement> getStatements() {
        return statements;
    }
}
