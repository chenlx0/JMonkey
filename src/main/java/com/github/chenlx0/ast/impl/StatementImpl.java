package com.github.chenlx0.ast.impl;

import com.github.chenlx0.ast.Expression;
import com.github.chenlx0.ast.Statement;
import com.github.chenlx0.lexer.Token;

public class StatementImpl {

    public static class ReturnStatement implements Statement {

        private Token token;

        private Expression expression;

        public ReturnStatement(Token token, Expression expression) {
            this.token = token;
            this.expression = expression;
        }

        public Token currentToken() {
            return token;
        }

        public Expression getExpression() {
            return expression;
        }
    }

    public static class LetStatement implements Statement {

        private Token token;

        private Token variable;

        private Expression expression;

        public LetStatement(Token token, Token variable, Expression expression) {
            this.token = token;
            this.variable = variable;
            this.expression = expression;
        }

        public Token currentToken() {
            return token;
        }

        public String getIdentifier() {
            return variable.getVal();
        }

        public Expression getExpression() {
            return expression;
        }
    }
}
