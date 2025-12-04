package edu.io;

import edu.io.player.Player;
import edu.io.token.*;
import edu.io.token.Label;

import javax.swing.*;
import java.awt.*;

public class Game {
    private final Board board;
    private Player player;

    private JLabel[][] gridLabels;

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
        frame.setSize(600, 600);

        JLabel scoreLabel = new JLabel(getScoreText(), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        frame.add(scoreLabel, BorderLayout.NORTH);

        JLabel toolLabel = new JLabel("Tools [ " + player.shed.toString() + " ]", SwingConstants.CENTER);
        toolLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        frame.add(toolLabel, BorderLayout.SOUTH);

        int size = board.size();
        JPanel boardPanel = new JPanel(new GridLayout(size, size));
        gridLabels = new JLabel[size][size];

        Font cellFont = new Font("Monospaced", Font.PLAIN, 20);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                JLabel cell = new JLabel();
                cell.setHorizontalAlignment(SwingConstants.CENTER);
                cell.setVerticalAlignment(SwingConstants.CENTER);
                cell.setFont(cellFont);
                cell.setOpaque(true);
                cell.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

                gridLabels[row][col] = cell;
                boardPanel.add(cell);
            }
        }

        updateBoardGrid();

        frame.add(boardPanel, BorderLayout.CENTER);

        JPanel contentPane = (JPanel) frame.getContentPane();
        new Controls(contentPane, player.token());

        frame.setVisible(true);

        Timer refreshTimer = new Timer(50, e -> {
            updateBoardGrid();
            scoreLabel.setText(getScoreText());
            toolLabel.setText("Tools [ " + player.shed.toString() + " ]");
        });
        refreshTimer.start();
    }

    private void updateBoardGrid() {
        int size = board.size();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Token token = board.peekToken(col, row);
                gridLabels[row][col].setText(token.label());

                if (token.label().equals(Label.PLAYER_TOKEN_LABEL)) {
                    gridLabels[row][col].setForeground(Color.RED);
                } else if (token.label().equals(Label.WATER_TOKEN_LABEL)) {
                    gridLabels[row][col].setForeground(Color.BLUE);
                } else {
                    gridLabels[row][col].setForeground(Color.BLACK);
                }
            }
        }
    }

    private String getScoreText() {
        return "Gold: " + player.gold.amount() + " | Water: " + player.vitals.hydration() + "%";
    }

    private void initializeBoard() {
        board.placeToken(board.getAvailableSquare(), new GoldToken(1.0));
        board.placeToken(board.getAvailableSquare(), new GoldToken(1.0));
        board.placeToken(board.getAvailableSquare(), new GoldToken(2.0));
        board.placeToken(board.getAvailableSquare(), new GoldToken(1.0));
        board.placeToken(board.getAvailableSquare(), new GoldToken(1.0));
        board.placeToken(board.getAvailableSquare(), new GoldToken(2.0));
        board.placeToken(board.getAvailableSquare(), new PyriteToken());

        board.placeToken(board.getAvailableSquare(), new PickaxeToken(2));
        board.placeToken(board.getAvailableSquare(), new SluiceboxToken());
        board.placeToken(board.getAvailableSquare(), new AnvilToken());

        board.placeToken(board.getAvailableSquare(), new WaterToken());
        board.placeToken(board.getAvailableSquare(), new WaterToken());
        board.placeToken(board.getAvailableSquare(), new WaterToken(20));
    }
}