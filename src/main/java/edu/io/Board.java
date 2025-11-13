package edu.io;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Board {
    private final int size;
    private final Token[][] grid;

    public Board(int size) {
        this.size = size;
        grid = new Token[size][size];
        TokenFactory factory = new TokenFactory();
        Token token = factory.createToken(".");

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = token;
            }
        }
    }

    public String draw() {
        return Arrays.stream(grid) // Stream<Token[]>
                .map(row -> Arrays.stream(row)
                        .map(Token::getLabel)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }
}
