package edu.io.player;

import java.util.Objects;

public class Vitals {
    private int hydration;
    private Runnable onDeathCallback;

    public Vitals() {
        this.hydration = 100;
        this.onDeathCallback = () -> {};
    }

    public int hydration() {
        return hydration;
    }

    public void hydrate(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        this.hydration = Math.min(100, this.hydration + amount);
    }

    public void dehydrate(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        this.hydration = Math.max(0, this.hydration - amount);

        if (!isAlive()) {
            onDeathCallback.run();
        }
    }

    public boolean isAlive() {
        return hydration > 0;
    }

    public void setOnDeathHandler(Runnable callback) {
        this.onDeathCallback = Objects.requireNonNull(callback, "callback cannot be null");
    }
}