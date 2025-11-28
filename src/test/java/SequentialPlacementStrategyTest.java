import edu.io.Board;
import edu.io.SequentialPlacementStrategy;
import edu.io.token.GoldToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SequentialPlacementStrategyTest {

    private Board board;
    private SequentialPlacementStrategy strategy;

    @BeforeEach
    void setUp() {
        board = new Board();
        strategy = new SequentialPlacementStrategy();
        board.setPlacementStrategy(strategy);
    }

    @Test
    void should_return_first_position_on_empty_board() {
        Board.Coords coords = strategy.determinePosition(board);

        Assertions.assertEquals(0, coords.col());
        Assertions.assertEquals(0, coords.row());
    }

    @Test
    void should_return_next_position_if_first_is_occupied() {
        board.placeToken(0, 0, new GoldToken());

        Board.Coords coords = strategy.determinePosition(board);

        Assertions.assertEquals(1, coords.col());
        Assertions.assertEquals(0, coords.row());
    }

    @Test
    void should_move_to_next_row_if_current_row_is_full() {
        for (int col = 0; col < board.size(); col++) {
            board.placeToken(col, 0, new GoldToken());
        }

        Board.Coords coords = strategy.determinePosition(board);

        Assertions.assertEquals(0, coords.col());
        Assertions.assertEquals(1, coords.row());
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
            strategy.determinePosition(board);
        });
    }
}