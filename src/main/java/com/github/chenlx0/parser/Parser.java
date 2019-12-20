package com.github.chenlx0.parser;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.ast.impl.*;
import com.github.chenlx0.ast.impl.Program;
import com.github.chenlx0.lexer.Lexer;
import com.github.chenlx0.lexer.Token;
import com.github.chenlx0.lexer.Token.TokenType;
import com.github.chenlx0.util.MonkeyException;
import com.github.chenlx0.util.ParseException;


public class Parser {

    public static int L1 = 0, L2 = 1, L3 = 2, L4 = 3;

    public static int INFIX = 0, PREFIX = 1;

    private Lexer lexer;

    private Token curToken, peekToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();
    }

    private void genAnnotations() {
    }

    private void expect(TokenType type) throws MonkeyException {
        if (!peekToken.isToken(type))
            throw new ParseException("Except " + type.name() + " after " + curToken.getVal());
    }

    private void nextToken() {
        curToken = peekToken;
        peekToken = lexer.nextToken();
    }

    public Program parseProgram() throws MonkeyException {
        Program program = new Program();
        Statement tmpStatement;

        while ((tmpStatement = parseStatement()) != null)
            program.add(tmpStatement);

        return program;
    }

    private Statement parseStatement() throws MonkeyException{
        switch (curToken.getType()) {
            case EOF:
                return null;
            case LET:
                return parseLetStatement();
            case RET:
                return parseReturnStatement();
            default:
                return parseExpressionStatement();
        }
    }

    private Statement parseLetStatement() throws MonkeyException {
        assert curToken.isToken(TokenType.LET);
        Token letToken = curToken;
        expect(TokenType.VAR);
        Token varToken = curToken;
        expect(TokenType.ASSIGN);
        Expression signExpression = parseExpression();
        return new LetStatement(letToken, varToken, signExpression);
    }

    private Statement parseReturnStatement() {
        assert curToken.isToken(Token.TokenType.RET);
        Token retToken = curToken;
        Expression retExpression = parseExpression();
        return new ReturnStatement(retToken, retExpression);
    }

    private Statement parseExpressionStatement() throws MonkeyException {
        return null;
    }

    private Expression parseExpression() {
        switch (curToken.getType()) {
            case SEMI:
                return null; // parse one line expression end
            case NUMBER: case STRING:
                return new AtomExpression(curToken);
        }
        return null;
    }
}
