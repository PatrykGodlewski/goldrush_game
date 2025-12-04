package edu.io.player;

import edu.io.token.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    public final Gold gold = new Gold();
    public final Shed shed = new Shed();
    public final Vitals vitals = new Vitals();

    private PlayerToken playerToken;

    public void assignToken(PlayerToken token) {
        this.playerToken = Objects.requireNonNull(token);
    }

    public PlayerToken token() {
        return playerToken;
    }

    public void interactWithToken(Token token) {
        Objects.requireNonNull(token);

        if (!vitals.isAlive()) {
            throw new IllegalStateException("Player is dead");
        }

        if (token instanceof EmptyToken) {
            vitals.dehydrate(VitalsValues.DEHYDRATION_MOVE);
        }
        else if (token instanceof WaterToken water) {
            vitals.hydrate(water.amount());
        }
        else if (token instanceof GoldToken goldToken) {
            vitals.dehydrate(VitalsValues.DEHYDRATION_GOLD);
            useToolsOnGold(goldToken);
        }
        else if (token instanceof AnvilToken) {
            vitals.dehydrate(VitalsValues.DEHYDRATION_ANVIL);
            useAnvil();
        }
        else if (token instanceof Tool tool) {
            shed.add(tool);
        }
    }

    private void useToolsOnGold(GoldToken goldToken) {
        List<Tool> tools = shed.getAllTools();
        List<Tool> brokenTools = new ArrayList<>();

        final double[] totalMultiplier = {1.0};

        for (Tool tool : tools) {
            tool.useWith(goldToken).ifWorking(() -> {
                double factor = 1.0;

                if (tool instanceof PickaxeToken p) factor = p.gainFactor();
                else if (tool instanceof SluiceboxToken s) factor = s.gainFactor();

                totalMultiplier[0] *= factor;
            });

            if (tool.isBroken()) {
                brokenTools.add(tool);
            }
        }

        gold.gain(goldToken.amount() * totalMultiplier[0]);

        for (Tool broken : brokenTools) {
            shed.removeTool(broken);
        }
    }

    private void useAnvil() {
        for (Tool tool : shed.getAllTools()) {
            if (tool instanceof Repairable r) {
                r.repair();
            }
        }
    }
}