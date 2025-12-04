package edu.io.player;

public class Gold {

    private double amount;

    public Gold() {
        this(0.0);
    }

    public Gold(double initialAmount) {
        if (initialAmount < 0) {
            throw new IllegalArgumentException("Initial gold amount cannot be negative");
        }
        this.amount = initialAmount;
    }

    public double amount() {
        return amount;
    }

    public void gain(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Cannot gain negative amount");
        }
        this.amount += value;
    }

    public void lose(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Cannot lose negative amount");
        }
        if (this.amount - value < 0) {
            throw new IllegalArgumentException("Not enough gold to lose this amount");
        }
        this.amount -= value;
    }
}