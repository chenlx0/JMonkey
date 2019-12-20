import com.github.chenlx0.lexer.Lexer;
import com.github.chenlx0.lexer.Token;

/**
 * @author: Chen Lixiang
 * @date: 2019-12-20
 */
public class TokenGenerateExample {

    public static void main(String[] args) {
        String text = "let x = 3;" +
                "fn(y) {" +
                "return y + 100;" +
                "}";
        Lexer lexer = new Lexer(text);
        Token token;
        while ((token = lexer.nextToken()).getType() != Token.TokenType.EOF) {
            System.out.println(token.toString());
        }
    }

}
