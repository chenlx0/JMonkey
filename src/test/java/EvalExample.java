import com.github.chenlx0.ast.impl.Program;
import com.github.chenlx0.evaluator.Evaluator;
import com.github.chenlx0.lexer.Lexer;
import com.github.chenlx0.parser.Parser;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-31
 */
public class EvalExample {
    public static void main(String[] args) {
        String text = "let y = [1, 3 * (-20086 + 10086), \"hello, world\", !!!true];" +
                "let z = {\"hi\":1.5 * 6, \"kkk\": !(10086 != 10086), \"ddd\": -(y[0] + 3) * 5};" +
                "let k = z[\"hi\"];";
        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        Program test = parser.parseProgram();
        Evaluator evaluator = new Evaluator();
        evaluator.evalProgram(test);
    }
}
