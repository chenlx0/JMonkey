package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Node;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.lexer.Token;

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

    public Token currentToken() {
        if (statements.isEmpty())
            return null;
        return statements.get(0).currentToken();
    }
}
