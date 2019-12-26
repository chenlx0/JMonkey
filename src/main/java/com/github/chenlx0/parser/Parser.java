package com.github.chenlx0.parser;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.ast.impl.*;
import com.github.chenlx0.lexer.Lexer;
import com.github.chenlx0.lexer.Token;
import com.github.chenlx0.lexer.Token.TokenType;
import com.github.chenlx0.util.Consts;
import com.github.chenlx0.util.MonkeyException;
import com.github.chenlx0.util.ParseException;


public class Parser {

    private Lexer lexer;

    private Token curToken, peekToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();
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

        while ((tmpStatement = parseStatement()) != null) {
            program.add(tmpStatement);
        }

        return program;
    }

    private Statement parseStatement() {
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

    private short infixPrecedence(TokenType tokenType) {
        Short result = Consts.InfixPrecedence.get(tokenType);
        return result == null ? -1 : result;
    }

    private Expression parsePrefix() {
        switch (curToken.getType()) {
            case NUMBER:
                return parseNumberLiteral();
            case STRING:
                return parseStringLiteral();
            default:
                throw new ParseException("can not parse token: " + curToken.getVal());
        }
    }

    private Expression parseExpression() {
        return parseExpression(Consts.LOWEST);
    }

    private Expression parseExpression(short precedence) {
        Expression leftExp = parsePrefix();

        while (!peekToken.isToken(TokenType.SEMI)) {

        }

        return leftExp;
    }

    private Statement parseLetStatement() {
        Token letToken = curToken;
        expect(TokenType.VAR);
        Token varToken = curToken;
        expect(TokenType.ASSIGN);
        Expression signExpression = parseExpression();
        return new LetStatement(letToken, varToken, signExpression);
    }

    private Statement parseReturnStatement() {
        Token retToken = curToken;
        Expression retExpression = parseExpression();
        return new ReturnStatement(retToken, retExpression);
    }

    private Statement parseExpressionStatement() {
        return null;
    }

    private Expression parseNumberLiteral() {
        try {
            if (curToken.getVal().contains(".")) {
                Double val = Double.valueOf(curToken.getVal());
                return new NumberExpression(val);
            } else {
                Integer val = Integer.valueOf(curToken.getVal());
                return new NumberExpression(val);
            }
        } catch (NumberFormatException e) {
            throw new ParseException("Parse number: " + curToken.getVal() + " failed.");
        }
    }

    private Expression parseStringLiteral() {
        Expression result = new StringExpression(curToken);
        return result;
    }

    private Expression parseBooleanLiteral() {
        Expression result;
        if (curToken.getType() == TokenType.TRUE) {
            result = new BooleanExpression(true);
        } else {
            result = new BooleanExpression(false);
        }
        return result;
    }

    private Expression parseVariableLiteral() {
        Expression result = new VariableExpression(curToken);
        return result;
    }

    private Expression parseOperatorPrefix() {
        Token tmpToken = curToken;
        Expression nextExpression = parseExpression(Consts.PREFIX);
        return new OperatorPrefixExpression(tmpToken, nextExpression);
    }
}
