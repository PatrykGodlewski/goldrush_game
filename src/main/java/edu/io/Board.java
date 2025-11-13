package edu.io;

import edu.io.token.EmptyToken;
import edu.io.token.Token;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Board {
    private final int size;
    private final Token[][] grid;

    public Board() {
        this.size = 6;
        grid = new Token[size][size];
        clean();
    }

    public record Coords(int col, int row){
    }

    public int size(){
        return size;
    }

    public void clean() {
        Token token = new EmptyToken();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                placeToken(col, row, token);
            }
        }

    };

    public void placeToken(int col, int row, Token token) {
        grid[row][col] = token;
    };


    public Token peekToken(int col, int row) {
        return grid[row][col];
    };

    public String display() {
        // Probably should use JPanel here
        return Arrays.stream(grid)
                .map(row -> Arrays.stream(row)
                        .map(Token::label)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }
}
