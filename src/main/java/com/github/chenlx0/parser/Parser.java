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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Parser {

    private Lexer lexer;

    private Token curToken, peekToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();
    }

    private void expect(TokenType type) throws MonkeyException {
        if (!peekToken.isToken(type)) {
            throw new ParseException("Except " + type.name() + " after " + curToken.getVal());
        }
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
        if (curToken.isToken(TokenType.SEMI)) {
            nextToken();
        }
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
        return result == null ? Consts.LOWEST : result;
    }

    private Expression parsePrefix() {
        switch (curToken.getType()) {
            case NUMBER:
                return parseNumberLiteral();
            case STRING:
                return parseStringLiteral();
            case VAR:
                return parseVariableLiteral();
            case TRUE: case FALSE:
                return parseBooleanLiteral();
            case MINUS: case EXCL:
                return parseOperatorPrefix();
            case LSQB:
                return parseArrayLiteral();
            default:
                throw new ParseException("can not parse prefix token: " + curToken.getVal());
        }
    }

    private Expression parseInfix(Expression leftExp) {
        switch (curToken.getType()) {
            case PLUS: case MINUS: case MULTI: case DIV:
                return parseNormalInfixOperator(leftExp);
            default:
                return null;
        }
    }

    private Expression parseExpression() {
        return parseExpression(Consts.LOWEST);
    }

    private Expression parseExpression(short precedence) {
        Expression leftExp = parsePrefix();

        while (!peekToken.isToken(TokenType.SEMI) && precedence < infixPrecedence(peekToken.getType())) {
            nextToken();
            Expression nextExp = parseInfix(leftExp);
            if (nextExp == null) {
                return leftExp;
            }

            leftExp = nextExp;
        }

        return leftExp;
    }

    private Statement parseLetStatement() {
        Token letToken = curToken;
        expect(TokenType.VAR);
        nextToken();
        Token varToken = curToken;
        expect(TokenType.ASSIGN);
        nextToken();
        nextToken();
        Expression signExpression = parseExpression();
        if (peekToken.isToken(TokenType.SEMI)) {
            nextToken();
        }
        return new LetStatement(letToken, varToken, signExpression);
    }

    private Statement parseReturnStatement() {
        Token retToken = curToken;
        nextToken();
        Expression retExpression = parseExpression();
        if (peekToken.isToken(TokenType.SEMI)) {
            nextToken();
        }
        return new ReturnStatement(retToken, retExpression);
    }

    private Statement parseExpressionStatement() {
        Token start = curToken;
        Expression exp = parseExpression();
        Statement stmt = new ExpressionStatement(start, exp);
        if (peekToken.isToken(TokenType.SEMI)) {
            nextToken();
        }
        return stmt;
    }

    private Expression parseNumberLiteral() {
        try {
            if (curToken.getVal().contains(".")) {
                Double val = Double.valueOf(curToken.getVal());
                return new NumberExpression(curToken, val);
            } else {
                Integer val = Integer.valueOf(curToken.getVal());
                return new NumberExpression(curToken, val);
            }
        } catch (NumberFormatException e) {
            throw new ParseException("Parse number: " + curToken.getVal() + " failed.");
        }
    }

    private Expression parseStringLiteral() {
        return new StringExpression(curToken);
    }

    private Expression parseBooleanLiteral() {
        Expression result;
        if (curToken.isToken(TokenType.TRUE)) {
            result = new BooleanExpression(curToken, true);
        } else {
            result = new BooleanExpression(curToken,false);
        }
        return result;
    }

    private Expression parseVariableLiteral() {
        Expression result = new VariableExpression(curToken);
        return result;
    }

    private Expression parseOperatorPrefix() {
        Token tmpToken = curToken;
        nextToken();
        Expression nextExpression = parseExpression(Consts.PREFIX);
        return new OperatorPrefixExpression(tmpToken, nextExpression);
    }

    private Expression parseArrayLiteral() {
        Token bracket = curToken;
        List<Expression> members = new LinkedList<>();

        if (peekToken.isToken(TokenType.RSQB)) {
            nextToken();
            return new ArrayExpression(bracket, members);
        }

        nextToken();
        members.add(parseExpression());
        nextToken();

        while (curToken.isToken(TokenType.COMMA)) {
            nextToken();
            members.add(parseExpression());
            nextToken();
        }

        if (!curToken.isToken(TokenType.RSQB)) {
            throw new ParseException("Array should end with ']'");
        }

        nextToken();

        return new ArrayExpression(bracket, members);
    }

    private Expression parseNormalInfixOperator(Expression leftExp) {
        Token infixOp = curToken;
        nextToken();
        Expression rightExp = parseExpression(infixPrecedence(infixOp.getType()));
        return new InfixExpression(infixOp, leftExp, rightExp);
    }

    private Expression parseDictLiteral() {
        Token brace = curToken;
        Map<String, Expression> dict = new HashMap<>();
        return null;
    }
}
