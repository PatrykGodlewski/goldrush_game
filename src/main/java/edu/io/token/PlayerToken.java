package edu.io.token;

import edu.io.Board;

public class PlayerToken extends Token {
    Board board;
    int col = 0;
    int row = 0;

    public PlayerToken(Board board) {
        super(Label.PLAYER_TOKEN_LABEL);
        this.board = board;

        this.col = 1;
        this.row = 1;
        board.placeToken(col, row, this);
    }

    public Board.Coords pos() {
        return new Board.Coords(col, row);
    }

    public void move(Move dir) {
        if (dir == Move.NONE) return;

        int newCol = col;
        int newRow = row;

        switch (dir) {
            case UP:
                newRow--;
                break;
            case DOWN:
                newRow++;
                break;
            case LEFT:
                newCol--;
                break;
            case RIGHT:
                newCol++;
                break;
        }

        if (newCol < 0 || newRow < 0 || newCol >= board.size() || newRow >= board.size()) {
            throw new IllegalArgumentException();
        }

        board.placeToken(col, row, new EmptyToken());

        this.col = newCol;
        this.row = newRow;

        board.placeToken(col, row, this);
    }

    public enum Move {
        UP, DOWN, LEFT, RIGHT, NONE
    }
}