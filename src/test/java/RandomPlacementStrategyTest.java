import edu.io.Board;
import edu.io.RandomPlacementStrategy;
import edu.io.token.EmptyToken;
import edu.io.token.GoldToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomPlacementStrategyTest {

    private Board board;
    private RandomPlacementStrategy strategy;

    @BeforeEach
    void setUp() {
        board = new Board();
        strategy = new RandomPlacementStrategy();
        board.setPlacementStrategy(strategy);
    }

    @Test
    void should_return_valid_coords_on_empty_board() {
        Board.Coords coords = strategy.determinePosition(board);

        Assertions.assertTrue(coords.col() >= 0 && coords.col() < board.size());
        Assertions.assertTrue(coords.row() >= 0 && coords.row() < board.size());

        Assertions.assertInstanceOf(EmptyToken.class, board.peekToken(coords));
    }

    @Test
    void should_find_the_last_empty_spot() {
        int size = board.size();
        int targetCol = 3;
        int targetRow = 3;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (col == targetCol && row == targetRow) continue;
                board.placeToken(col, row, new GoldToken());
            }
        }

        Board.Coords coords = strategy.determinePosition(board);

        Assertions.assertEquals(targetCol, coords.col());
        Assertions.assertEquals(targetRow, coords.row());
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