package org.hdme.jethero.model;

/**
 * Maintains prototypes of jets.
 */
public final class JetPrototype {
    public static final JetPrototype PLAYER_JET = new JetPrototype()
            .setMaxVelocity(0.75);
    public static final JetPrototype ENEMY_JET = new JetPrototype()
            .setMaxVelocity(0.5);

    // pixels per tick
    private double maxVelocity;

    private JetPrototype() {
        // empty
    }

    private JetPrototype setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
        return this;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }
}
