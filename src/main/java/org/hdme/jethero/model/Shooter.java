package org.hdme.jethero.model;

public class Shooter {
    private int offsetX, offsetY;
    /*
     * O - - - > (x)
     * |
     * |
     * |
     * V
     * (y)
     */
    // the angel(rad) between ejecting direction and positive-X axis
    private double direction;
    private double bulletVelo;

    Shooter(int offsetX, int offsetY, double direction, double bulletVelo) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.direction = direction;
        this.bulletVelo = bulletVelo;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public double getDirection() {
        return direction;
    }

    public double getBulletVelo() {
        return bulletVelo;
    }
}
