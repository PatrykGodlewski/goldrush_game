package edu.io;

public interface PlacementStrategy {
    Board.Coords determinePosition(Board board);
}