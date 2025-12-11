package edu.io;

import edu.io.token.EmptyToken;
import edu.io.token.Token;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Board {
    private final int size;
    private final Token[][] grid;

    private PlacementStrategy placementStrategy;

    public Board() {
        this.size = 18;
        grid = new Token[size][size];

        this.placementStrategy = new SequentialPlacementStrategy();

        clean();
    }

    public void setPlacementStrategy(PlacementStrategy placementStrategy) {
        this.placementStrategy = placementStrategy;
    }

    public int size() {
        return size;
    }

    public void clean() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                placeToken(new Coords(col, row), new EmptyToken());
            }
        }
    }

    public void placeToken(Board.Coords coords, Token token) {
        Objects.requireNonNull(token, "Token cannot be null");
        grid[coords.row()][coords.col()] = token;
    }

    public Token peekToken(Board.Coords coords) {
        return grid[coords.row()][coords.col()];
    }

    // --- (Legacy Support for Tests) ---
    public void placeToken(int col, int row, Token token) {
        placeToken(new Coords(col, row), token);
    }

    public Token peekToken(int col, int row) {
        return peekToken(new Coords(col, row));
    }
    // ----------------------------------

    public Coords getAvailableSquare() {
        return placementStrategy.determinePosition(this);
    }

    public String display() {
        return Arrays.stream(grid).map(row -> Arrays.stream(row).map(Token::label).collect(Collectors.joining(" "))).collect(Collectors.joining("\n"));
    }

    public record Coords(int col, int row) {
    }
}