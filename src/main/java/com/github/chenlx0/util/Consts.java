package com.github.chenlx0.util;

import com.github.chenlx0.lexer.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-21
 */
public class Consts {

    // Operator order
    public static short NONE = -1,
            LOWEST = 0,
            EQEQUAL = 1,     // ==
            LEGT = 2,       // <, >, <=, >=
            SUM = 3,        // =
            PRODUCT = 4,    // *
            PREFIX = 5,     // -X or !X
            CALL = 6;       // Function Call

    public static final Map<Token.TokenType, Short> InfixPrecedence;
    static
    {
        InfixPrecedence = new HashMap<>();
        InfixPrecedence.put(Token.TokenType.EQEQUAL, EQEQUAL);
        InfixPrecedence.put(Token.TokenType.GREATEQ, LEGT);
        InfixPrecedence.put(Token.TokenType.LESSEQ, LEGT);
        InfixPrecedence.put(Token.TokenType.GREATER, LEGT);
        InfixPrecedence.put(Token.TokenType.LESS, LEGT);
        InfixPrecedence.put(Token.TokenType.XOR, LEGT);
        InfixPrecedence.put(Token.TokenType.OR, LEGT);
        InfixPrecedence.put(Token.TokenType.AND, LEGT);
        InfixPrecedence.put(Token.TokenType.AMPER, LEGT);
        InfixPrecedence.put(Token.TokenType.VBAR, LEGT);
        InfixPrecedence.put(Token.TokenType.PLUS, SUM);
        InfixPrecedence.put(Token.TokenType.MINUS, SUM);
        InfixPrecedence.put(Token.TokenType.MULTI, PRODUCT);
        InfixPrecedence.put(Token.TokenType.DIV, PRODUCT);
        InfixPrecedence.put(Token.TokenType.ASSIGN, SUM);
        InfixPrecedence.put(Token.TokenType.LSQB, PREFIX);
        InfixPrecedence.put(Token.TokenType.NOTEQUAL, EQEQUAL);
        InfixPrecedence.put(Token.TokenType.LPAR, CALL);
    }

    // type name
    public static String INTEGER = "java.lang.Integer",
            FLOAT = "java.lang.Double",
            BOOL = "java.lang.Boolean",
            DICT = "java.util.HashMap",
            STRING = "java.lang.String",
            ARRAY = "java.util.ArrayList",
            FUNC = "com.github.chenlx0.evaluator.Function";

    // Infix or Prefix Operator
    public static final short INFIX_OP = 0, PREFIX_OP = 1;

    // version
    public static final String VERSION = "0.0.1";
}
