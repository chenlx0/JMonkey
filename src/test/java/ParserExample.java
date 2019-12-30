import com.github.chenlx0.ast.impl.Program;
import com.github.chenlx0.lexer.Lexer;
import com.github.chenlx0.parser.Parser;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-26
 */
public class ParserExample {

    public static void main(String[] args) {
        String text = "let y = [-1, true == true, !false, 19880]; " +
                "let z = y[1];" +
                "let abc = fn(p, k) { if (p == 100) { let p = 3 * (p + 10); } return p; };" +
                "let edf = fn() { return 100.7 + abc(z, 1+1); };" +
                "let k = z / k * 10.8 * 1;" +
                "let y = {\"3\": 123};";
        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        Program test = parser.parseProgram();
    }
}
