package edu.io;

import edu.io.token.EmptyToken;

public class SequentialPlacementStrategy implements PlacementStrategy {
    @Override
    public Board.Coords determinePosition(Board board) {
        int size = board.size();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Board.Coords currentPos = new Board.Coords(col, row);

                if (board.peekToken(currentPos) instanceof EmptyToken) {
                    return currentPos;
                }
            }
        }
        throw new IllegalStateException("Board is full");
    }
}