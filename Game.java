public class Game {

    public static void main(String[] args) {
        System.out.println("Game is on!");
        int i = 0;
        while (true) {
            System.out.println(i++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }        }
    }
}
