package src.main.java.edu.io;

public class Game {

    public static void run() {
        System.out.println("Game is on!");
        Board board = new Board(6);

        while (true) {
            System.out.print("\033[H"); // move cursor to top-left
            System.out.flush();
            System.out.println(board.draw());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
