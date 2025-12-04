import edu.io.player.Player;
import edu.io.token.AnvilToken;
import edu.io.token.GoldToken;
import edu.io.token.SluiceboxToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SluiceboxTest {

    @Test
    void sluicebox_has_correct_initial_parameters() {
        SluiceboxToken sluice = new SluiceboxToken();
        Assertions.assertEquals(1.2, sluice.gainFactor(), 0.0001);
        Assertions.assertEquals(1.2, sluice.durability());
    }

    @Test
    void sluicebox_increases_gold_collection() {
        Player player = new Player();
        player.interactWithToken(new SluiceboxToken());
        player.interactWithToken(new GoldToken(10.0));

        // 10.0 * 1.2 = 12.0
        Assertions.assertEquals(12.0, player.gold.amount(), 0.0001);
    }

    @Test
    void gain_decreases_after_use() {
        SluiceboxToken sluice = new SluiceboxToken();
        double initialGain = sluice.gainFactor();

        sluice.use();

        // 1.2 - 0.04 = 1.16
        Assertions.assertEquals(initialGain - 0.04, sluice.gainFactor(), 0.0001);
        Assertions.assertEquals(initialGain - 0.04, sluice.durability());
    }

    @Test
    void cannot_repair_sluicebox() {
        Player player = new Player();
        SluiceboxToken sluice = new SluiceboxToken();
        player.interactWithToken(sluice);

        sluice.use();
        double durabilityAfterUse = sluice.durability();

        player.interactWithToken(new AnvilToken());

        Assertions.assertEquals(durabilityAfterUse, sluice.durability());
    }
}