package org.hdme.jethero.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Maintains prototypes of jets.
 */
public final class JetPrototype {
    public static final JetPrototype PLAYER_JET = new JetPrototype()
            .setMaxVelocity(0.75)
            .addShooter((EntityPrototype.JET_MEDIUM.getSizeX() - EntityPrototype.BULLET.getSizeX()) / 2,
                    -EntityPrototype.BULLET.getSizeY(),
                    -Math.PI / 2, 2);
    public static final JetPrototype ENEMY_JET = new JetPrototype()
            .setMaxVelocity(0.5)
            .addShooter((EntityPrototype.JET_MEDIUM.getSizeX() - EntityPrototype.BULLET.getSizeX()) / 2,
                    -EntityPrototype.JET_MEDIUM.getSizeY(),
                    Math.PI / 2, 2);

    // pixels per tick
    private double maxVelocity;
    // top-left corner offset (to jet's top-left corner) of a newly generated bullet
    private List<Shooter> shooters;

    private JetPrototype() {
        shooters = new LinkedList<>();
    }

    private JetPrototype setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
        return this;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    private JetPrototype addShooter(int offsetX, int offsetY, double direction, double bulletVelo) {
        shooters.add(new Shooter(offsetX, offsetY, direction, bulletVelo));
        return this;
    }

    public Shooter[] getShooters() {
        return shooters.toArray(new Shooter[0]);
    }

    public int getShooterAmount() {
        return shooters.size();
    }
}
