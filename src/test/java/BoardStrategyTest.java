import edu.io.Board;
import edu.io.PlacementStrategy;
import edu.io.SequentialPlacementStrategy;
import edu.io.token.GoldToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardStrategyTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void should_use_custom_strategy_when_set() {
        PlacementStrategy fixedStrategy = b -> new Board.Coords(5, 5);

        board.setPlacementStrategy(fixedStrategy);

        Board.Coords result = board.getAvailableSquare();

        Assertions.assertEquals(5, result.col());
        Assertions.assertEquals(5, result.row());
    }

    @Test
    void should_follow_sequential_strategy_logic() {
        board.setPlacementStrategy(new SequentialPlacementStrategy());

        Assertions.assertEquals(new Board.Coords(0, 0), board.getAvailableSquare());

        board.placeToken(0, 0, new GoldToken());

        Assertions.assertEquals(new Board.Coords(1, 0), board.getAvailableSquare());
    }

    @Test
    void should_throw_exception_when_board_is_full() {
        int size = board.size();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board.placeToken(col, row, new GoldToken());
            }
        }

        Assertions.assertThrows(IllegalStateException.class, () -> {
            board.getAvailableSquare();
        });
    }
}