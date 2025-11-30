package edu.io;

import edu.io.player.Player;
import edu.io.token.*;

import javax.swing.*;
import java.awt.*;

public class Game {
    private final Board board;
    private Player player;

    public Game() {
        this.board = new Board();
        board.setPlacementStrategy(new RandomPlacementStrategy());
        initializeBoard();
    }

    public void join(Player player) {
        this.player = player;
        new PlayerToken(player, board);
    }

    public void start() {
        if (player == null) {
            throw new IllegalStateException("Nie można rozpocząć gry bez gracza! Użyj metody join().");
        }

        System.out.println("Game is on!");
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Gold Rush");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);

        JLabel scoreLabel = new JLabel("Gold: " + player.gold.amount(), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(scoreLabel, BorderLayout.NORTH);


        JLabel toolLabel = new JLabel("Tools [ " + player.shed.toString() + " ]", SwingConstants.CENTER );
        frame.add(toolLabel, BorderLayout.SOUTH);

        JLabel boardLabel = new JLabel("<html><pre>" + board.display() + "</pre></html>", SwingConstants.CENTER);
        frame.add(boardLabel, BorderLayout.CENTER);

        JPanel contentPane = (JPanel) frame.getContentPane();
        new Controls(contentPane, player.token());

        frame.setVisible(true);

        Timer refreshTimer = new Timer(50, e -> {
            boardLabel.setText("<html><pre>" + board.display() + "</pre></html>");
            scoreLabel.setText("Gold: " + player.gold.amount());
            toolLabel.setText("Tools [ " + player.shed.toString() + " ]");
        });
        refreshTimer.start();
    }

    private void initializeBoard() {
        board.placeToken(board.getAvailableSquare(), new GoldToken(1.0));
//        board.placeToken(board.getAvailableSquare(), new GoldToken(1.0));
//        board.placeToken(board.getAvailableSquare(), new GoldToken(2));
//        board.placeToken(board.getAvailableSquare(), new PyriteToken());

        board.placeToken(board.getAvailableSquare(), new PickaxeToken(2));
        board.placeToken(board.getAvailableSquare(), new SluiceboxToken());
        board.placeToken(board.getAvailableSquare(), new AnvilToken());
    }
}