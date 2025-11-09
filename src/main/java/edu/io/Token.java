public class Token {
    private final int id;
    private final String label;

    public Token(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }

    @Override
    public String toString() {
        return "Token{" + "id=" + id + ", label='" + label + '\'' + '}';
    }
}