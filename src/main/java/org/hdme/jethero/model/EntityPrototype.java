package org.hdme.jethero.model;

/**
 * Maintains prototypes of entities.
 */
public final class EntityPrototype {
    public static final EntityPrototype BULLET = new EntityPrototype()
            .setSize(6, 6);
    public static final EntityPrototype JET = new EntityPrototype()
            .setSize(80, 100);

    private int sizeX, sizeY;

    private EntityPrototype() {
        // empty
    }

    private EntityPrototype setSize(int x, int y) {
        sizeX = x;
        sizeY = y;
        return this;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
