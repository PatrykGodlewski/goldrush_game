package src.main.java.edu.io;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Board {
    private final int size;
    private final Token[][] grid;

    public Board(int size) {
        this.size = size;
        grid = new Token[size][size]; // <-- initialize the 2D array
        TokenFactory factory = new TokenFactory();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                String label = "Token " + row + "-" + col;
                grid[row][col] = factory.createToken(label);
            }
        }
    }

    public String draw() {
        return Arrays.stream(grid) // Stream<Token[]>
                .map(row -> Arrays.stream(row)           // Stream<Token>
                        .map(Token::getLabel)           // Stream<String>
                        .collect(Collectors.joining(" "))) // join labels in this row
                .collect(Collectors.joining("\n"));      // join rows with newline
    }
}
