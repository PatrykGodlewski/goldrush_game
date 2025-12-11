package edu.io;

import edu.io.token.EmptyToken;

import java.util.Random;

public class RandomPlacementStrategy implements PlacementStrategy {
    private final Random random = new Random();

    @Override
    public Board.Coords determinePosition(Board board) {
        int size = board.size();
        int totalCells = size * size;

        int startIndex = random.nextInt(totalCells);

        for (int i = 0; i < totalCells; i++) {
            int currentIndex = (startIndex + i) % totalCells;

            int row = currentIndex / size;
            int col = currentIndex % size;

            Board.Coords currentPos = new Board.Coords(col, row);

            if (board.peekToken(currentPos) instanceof EmptyToken) {
                return currentPos;
            }
        }

        throw new IllegalStateException("Board is full");
    }
}