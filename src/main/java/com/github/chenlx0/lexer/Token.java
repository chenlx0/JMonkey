package com.github.chenlx0.lexer;

public class Token {
    public enum TokenType { EOF, NUMBER, STRING, LET, FUNC, TRUE, FALSE, RET, VAR, UNKNOWN,
                            IF, ELSE, LBRACE, RBRACE, EQEQUAL, NOTEQUAL, AND, OR, XOR, COMMA,
                            ASSIGN, AMPER, VBAR, PLUS, MINUS, MULTI, DIV, GREATER, LESS, EXCL,
                            LSQB, RSQB, LPAR, RPAR, SEMI, GREATEQ, LESSEQ, WHILE, COLON }

    private TokenType type;

    private String val;

    public static Token buildToken(TokenType type, String val) {
        Token token = new Token();
        token.type = type;
        token.val = val;
        return token;
    }

    public boolean isToken(Token.TokenType tokenType) {
        return this.type == tokenType;
    }

    public String toString() {
        if (type == TokenType.NUMBER) {
            return "[token_type: " + "number" + ", " + "val: " + val + "]";
        } else if (type == TokenType.VAR) {
            return "[token_type: " + "variable" + ", " + "val: " + val + "]";
        }

        return "[token_type: " + val + "]";
    }

    public TokenType getType() {
        return type;
    }

    public String getVal() {
        return val;
    }
}
