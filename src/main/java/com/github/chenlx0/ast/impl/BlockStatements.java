package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Node;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.lexer.Token;

import java.util.List;

public class BlockStatements implements Node {
    List<Statement> statements;

    public Token currentToken() {
        if (statements.isEmpty())
            return null;
        return statements.get(0).currentToken();
    }
}
