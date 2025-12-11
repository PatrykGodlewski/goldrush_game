package edu.io.token;

import edu.io.player.Tool;

public class SluiceboxToken extends Token implements Tool {

    private static final double DECAY_RATE = 0.04;
    private double currentGainFactor;
    private boolean isWorkingOnTarget = false;
    private boolean isBrokenState = false;

    public SluiceboxToken() {
        super(Label.SLUICEBOX_TOKEN_LABEL);
        this.currentGainFactor = 1.2;
    }

    public double gainFactor() {
        return currentGainFactor;
    }

    public double durability() {
        return currentGainFactor;
    }

    public void use() {
        if (currentGainFactor > 0) {
            currentGainFactor = Math.max(0, currentGainFactor - DECAY_RATE);
        }
    }

    @Override
    public boolean isBroken() {
        return currentGainFactor <= 0;
    }

    @Override
    public Tool useWith(Token token) {
        isWorkingOnTarget = false;
        isBrokenState = false;

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