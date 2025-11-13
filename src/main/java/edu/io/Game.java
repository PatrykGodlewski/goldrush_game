package edu.io;

import javax.swing.*;

public class Game {

    public static void run() {
        System.out.println("Game is on!");
        Board board = new Board(6);

        // Create frame
        JFrame frame = new JFrame("Board Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JLabel label = new JLabel("<html><pre>" + board.draw() + "</pre></html>", SwingConstants.CENTER);
        frame.add(label);
        frame.setVisible(true);

        new Thread(() -> {
            while (true) {
                label.setText("<html><pre>" + board.draw() + "</pre></html>");
            }
        }).start();
    }
}
