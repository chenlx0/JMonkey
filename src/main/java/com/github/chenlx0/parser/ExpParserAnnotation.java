package com.github.chenlx0.parser;

import com.github.chenlx0.lexer.Token.TokenType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value=METHOD)
@Retention(value=RUNTIME)
public @interface ExpParserAnnotation {

    int getPrefixType();
    TokenType[] type();

}
