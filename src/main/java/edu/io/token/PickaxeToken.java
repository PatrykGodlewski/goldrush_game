package edu.io.token;

import edu.io.player.Repairable;
import edu.io.player.Tool;

public class PickaxeToken extends Token implements Tool, Repairable {
    private final double gainFactor;
    private final int maxDurability;
    private int durability;
    private boolean isWorkingOnTarget = false;
    private boolean isBrokenState = false;

    public PickaxeToken() {
        this(1.5, 3);
    }

    public PickaxeToken(double gainFactor) {
        this(gainFactor, 3);
    }

    public PickaxeToken(double gainFactor, int durability) {
        super(Label.PICKAXE_TOKEN_LABEL);
        if (gainFactor <= 0) throw new IllegalArgumentException("Gain factor must be positive");
        if (durability <= 0) throw new IllegalArgumentException("Durability must be positive");

        this.gainFactor = gainFactor;
        this.maxDurability = durability;
        this.durability = durability;
    }

    public double gainFactor() {
        return gainFactor;
    }

    public int durability() {
        return durability;
    }

    public void use() {
        if (durability > 0) {
            durability--;
        }
    }

    @Override
    public boolean isBroken() {
        return durability <= 0;
    }

    @Override
    public void repair() {
        this.durability = maxDurability;
    }

    @Override
    public Tool useWith(Token token) {
        this.isWorkingOnTarget = false;
        this.isBrokenState = false;

        if (token instanceof GoldToken) {
            if (!isBroken()) {
                isWorkingOnTarget = true;
            } else {
                isBrokenState = true;
            }
        }
        return this;
    }

    @Override
    public Tool ifWorking(Runnable action) {
        if (isWorkingOnTarget) {
            action.run();
            use();
        }
        return this;
    }

    @Override
    public Tool ifBroken(Runnable action) {
        if (isBrokenState) action.run();
        return this;
    }

    @Override
    public Tool ifIdle(Runnable action) {
        if (!isWorkingOnTarget && !isBrokenState) action.run();
        return this;
    }
}