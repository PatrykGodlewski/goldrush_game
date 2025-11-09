public class TokenFactory {
    private int counter = 0;

    public Token createToken(String label) {
        Token token = new Token(counter++, label);
        return token;
    }
}