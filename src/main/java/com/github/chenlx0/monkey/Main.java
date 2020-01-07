package com.github.chenlx0.monkey;

import com.github.chenlx0.ast.impl.Program;
import com.github.chenlx0.evaluator.Evaluator;
import com.github.chenlx0.lexer.Lexer;
import com.github.chenlx0.parser.Parser;
import com.github.chenlx0.util.Consts;
import com.github.chenlx0.util.MonkeyException;

import java.util.Scanner;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-31
 */
public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("");
        Parser parser = new Parser(lexer);
        Evaluator evaluator = new Evaluator();
        Scanner scanner = new Scanner(System.in);
        String code;
        Object object;
        System.out.printf("JMonkey Version %s\n", Consts.VERSION);
        while (true) {
            try {
                System.out.print(">>");
                code = scanner.nextLine();
                if (code.startsWith("exit()")) break;
                Program program = parser.parseProgram(code);
                object = evaluator.evalProgram(program);
                if (object != null) {
                    System.out.println(object);
                }
            } catch (MonkeyException mke) {
                System.out.println(mke.toString());
            }
        }

    }
}
