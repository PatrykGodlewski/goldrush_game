package edu.io.player;

import edu.io.token.*;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public final Gold gold = new Gold();
    public final Shed shed = new Shed();
    private PlayerToken playerToken;

    public void assignToken(PlayerToken token) {
        this.playerToken = token;
    }

    public PlayerToken token() {
        return playerToken;
    }

    public void interactWithToken(Token token) {
        switch (token) {
            case GoldToken goldToken -> useToolsOnGold(goldToken);
            case PickaxeToken pickaxe -> shed.add(pickaxe);
            case SluiceboxToken sluice -> shed.add(sluice);
            case AnvilToken anvil -> useAnvil();
            case null, default -> {
            }
        }
    }

    private void useToolsOnGold(GoldToken goldToken) {
        List<Tool> tools = shed.getAllTools();
        List<Tool> brokenTools = new ArrayList<>();

        // TODO: refactor
        final double[] totalFactorWrapper = {1.0};

        for (Tool tool : tools) {
            tool.useWith(goldToken).ifWorking(() -> {
                double factor = switch (tool) {
                    case PickaxeToken p -> p.gainFactor();
                    case SluiceboxToken s -> s.gainFactor();
                    default -> 1.0;
                };

                // TODO: refactor
                totalFactorWrapper[0] *= factor;

                if (tool.isBroken()) {
                    brokenTools.add(tool);
                }
            });
        }

        gold.gain(goldToken.amount() * totalFactorWrapper[0]);

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