package edu.io.token;

import edu.io.Board;
import edu.io.player.Player;

public class PlayerToken extends Token {
    private final Board board;
    private final Player player;
    private Board.Coords currentCoords;

    public PlayerToken(Board board) {
        this(new Player(), board);
    }

    public PlayerToken(Player player, Board board) {
        super(Label.PLAYER_TOKEN_LABEL);
        this.board = board;
        this.player = player;

        this.player.assignToken(this);

        this.currentCoords = board.getAvailableSquare();
        board.placeToken(this.currentCoords, this);
    }

    public Board.Coords pos() {
        return currentCoords;
    }

    public void move(Move dir) {
        if (dir == Move.NONE) return;

        int newCol = currentCoords.col();
        int newRow = currentCoords.row();

        switch (dir) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case LEFT -> newCol--;
            case RIGHT -> newCol++;
        }

        if (newCol < 0 || newRow < 0 || newCol >= board.size() || newRow >= board.size()) {
            throw new IllegalArgumentException("Cannot move outside board boundaries");
        }

        Board.Coords newCoords = new Board.Coords(newCol, newRow);

        Token targetToken = board.peekToken(newCoords);
        player.interactWithToken(targetToken);

        board.placeToken(currentCoords, new EmptyToken());
        this.currentCoords = newCoords;
        board.placeToken(currentCoords, this);
    }

    public enum Move {
        UP, DOWN, LEFT, RIGHT, NONE
    }
}