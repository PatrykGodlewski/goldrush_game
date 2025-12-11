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

        switch (token) {
            case EmptyToken emptyToken -> vitals.dehydrate(VitalsValues.DEHYDRATION_MOVE);
            case WaterToken waterToken -> vitals.hydrate(waterToken.amount());
            case GoldToken goldToken -> {
                vitals.dehydrate(VitalsValues.DEHYDRATION_GOLD);
                useToolsOnGold(goldToken);
            }
            case Tool tool -> shed.add(tool);
            case AnvilToken anvil -> {
                vitals.dehydrate(VitalsValues.DEHYDRATION_ANVIL);
                useAnvil();
            }
            case null, default -> {
            }
        }
    }

    private void useToolsOnGold(GoldToken goldToken) {
        List<Tool> tools = shed.getAllTools();
        List<Tool> brokenTools = new ArrayList<>();

        final double[] totalMultiplier = {1.0};

        for (Tool tool : tools) {
            tool.useWith(goldToken).ifWorking(() -> {
                double factor = switch (tool) {
                    case PickaxeToken p -> p.gainFactor();
                    case SluiceboxToken s -> s.gainFactor();
                    default -> 1.0;
                };

                totalMultiplier[0] *= factor;
            }).ifBroken(() -> {
                brokenTools.add(tool);
            });

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