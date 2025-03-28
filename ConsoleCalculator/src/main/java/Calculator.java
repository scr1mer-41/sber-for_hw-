import java.util.List;

public class Calculator {

    private final Lexer lexer = new Lexer();
    private final PostfixConverter converter = new PostfixConverter();
    private final StackMachine stackMachine = new StackMachine();

    public int calculate(String expression) {
        List<Token> tokens = lexer.getTokens(expression);
        List<Token> postfixExpression = converter.convertToPostfix(tokens);
        int result = stackMachine.evaluate(postfixExpression);
        return result;
    }
}