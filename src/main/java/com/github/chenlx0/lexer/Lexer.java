package com.github.chenlx0.lexer;

public class Lexer {

    private String text;

    private int cursor;

    public Lexer(String text) {
        cursor = 0;
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
        this.cursor = 0;
    }

    private char getchar() {
        if (cursor >= text.length()) return 0;
        cursor++;
        return text.charAt(cursor-1);
    }

    private void unsetchar() {
        if (cursor > 0) cursor--;
    }

    private void parseWhitespaces() {
        char c;
        do { c = getchar(); } while(c == ' ' || c == '\n' || c == '\t');
        if (c == 0) {
            return;
        }
        unsetchar();
    }

    public Token nextToken() {
        parseWhitespaces();
        if (cursor >= text.length()) return Token.buildToken(Token.TokenType.EOF, "eof");
        Token retToken;

        if ((retToken = readTwoChar()) != null) {
            return retToken;
        } else if ((retToken = readOneChar()) != null) {
            return retToken;
        } else if ((retToken = readNumber()) != null) {
            return retToken;
        } else if ((retToken = readWord()) != null) {
            return retToken;
        } else if ((retToken = readString()) != null) {
            return retToken;
        }

        return Token.buildToken(Token.TokenType.UNKNOWN, "unknown");
    }

    private boolean isValidLetter(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')
                || ('0' <= c && c <= '9') || (c == '_');
    }

    private boolean isNumChar(char c) {
        return ('0' <= c && c <= '9') || c == '.';
    }

    private Token readOneChar() {
        char c = getchar();

        switch (c) {
            case ';':
                return Token.buildToken(Token.TokenType.SEMI, ";");
            case '&':
                return Token.buildToken(Token.TokenType.AMPER, "&");
            case '|':
                return Token.buildToken(Token.TokenType.VBAR, "|");
            case '^':
                return Token.buildToken(Token.TokenType.XOR, "^");
            case '=':
                return Token.buildToken(Token.TokenType.ASSIGN, "=");
            case '+':
                return Token.buildToken(Token.TokenType.PLUS, "+");
            case '-':
                return Token.buildToken(Token.TokenType.MINUS, "-");
            case '*':
                return Token.buildToken(Token.TokenType.MULTI, "*");
            case '/':
                return Token.buildToken(Token.TokenType.DIV, "/");
            case '!':
                return Token.buildToken(Token.TokenType.EXCL, "!");
            case ',':
                return Token.buildToken(Token.TokenType.COMMA, ",");
            case '{':
                return Token.buildToken(Token.TokenType.LBRACE, "{");
            case '}':
                return Token.buildToken(Token.TokenType.RBRACE,  "}");
            case '[':
                return Token.buildToken(Token.TokenType.LSQB, "[");
            case ']':
                return Token.buildToken(Token.TokenType.RSQB, "]");
            case '(':
                return Token.buildToken(Token.TokenType.LPAR, "(");
            case ')':
                return Token.buildToken(Token.TokenType.RPAR, ")");
            case '>':
                return Token.buildToken(Token.TokenType.GREATER, ">");
            case '<':
                return Token.buildToken(Token.TokenType.LESS, "<");
        }

        unsetchar();
        return null;
    }

    private Token readTwoChar() {
        char c = getchar();

        switch (c) {
            case '&':
                if (getchar() == '&')
                    return Token.buildToken(Token.TokenType.AND, "&&");
                break;
            case '|':
                if (getchar() == '|')
                    return Token.buildToken(Token.TokenType.OR, "||");
                break;
            case '=':
                if (getchar() == '=')
                    return Token.buildToken(Token.TokenType.EQEQUAL, "==");
                break;
            case '!':
                if (getchar() == '=')
                    return Token.buildToken(Token.TokenType.NOTEQUAL, "!=");
                break;
            case '>':
                if (getchar() == '=')
                    return Token.buildToken(Token.TokenType.GREATEQ, ">=");
                break;
            case '<':
                if (getchar() == '=')
                    return Token.buildToken(Token.TokenType.LESSEQ, "<=");
                break;
            default:
                cursor++;
                break;
        }

        unsetchar(); unsetchar();
        return null;
    }

    private Token readNumber() {
        char c = getchar();
        if (!isNumChar(c)) {
            unsetchar();
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(c);
        while (isNumChar(c = getchar()))
            builder.append(c);
        unsetchar();

        return Token.buildToken(Token.TokenType.NUMBER, builder.toString());
    }

    private Token readString() {
        if (getchar() != '"') {
            unsetchar();
            return null;
        }

        char c;
        StringBuilder result = new StringBuilder();
        while ((c = getchar()) != '"')
            result.append(c);

        return Token.buildToken(Token.TokenType.STRING, result.toString());
    }

    private Token readWord() {
        StringBuilder builder = new StringBuilder();
        char nextChar;
        String result = null;

        while ((nextChar = getchar()) != 0) {
            if (isValidLetter(nextChar)) {
                builder.append(nextChar);
            } else {
                unsetchar();
                result = builder.toString();
                break;
            }
        }

        if (result == null || result.length() == 0) {
            return null;
        }

        switch (result) {
            case "let":
                return Token.buildToken(Token.TokenType.LET, result);
            case "fn":
                return Token.buildToken(Token.TokenType.FUNC, result);
            case "true":
                return Token.buildToken(Token.TokenType.TRUE, result);
            case "false":
                return Token.buildToken(Token.TokenType.FALSE, result);
            case "return":
                return Token.buildToken(Token.TokenType.RET, result);
            case "if":
                return Token.buildToken(Token.TokenType.IF, result);
            case "else":
                return Token.buildToken(Token.TokenType.ELSE, result);
            case "while":
                return Token.buildToken(Token.TokenType.WHILE, result);
            default:
                return Token.buildToken(Token.TokenType.VAR, result);
        }
    }
}
