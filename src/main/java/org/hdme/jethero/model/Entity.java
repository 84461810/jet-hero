package org.hdme.jethero.model;

/**
 * Entity data structure. The super class of all entity-type classes.
 * <br/>A non-abstract inheriting class should call <code>setEntityPrototype</code>
 * and <code>setBoundingBoxAsPrototype</code> in its constructor.
 */
public abstract class Entity {
    /**
     * Defines the interval between updates of entity state.
     * It is currently 5 milliseconds.
     * It is also used as time unit.
     */
    public static final int TICK_DURATION = 5;

    public static final int TEAM_ALLY = 0;
    public static final int TEAM_ENEMY = 1;
    public static final int TEAM_NEUTRAL = 2;

    private EntityPrototype entityPrototype;
    private BoundingBoxGroup boundingBox;
    private int team;
    private String typeName;
    // position of top-left corner
    private double posX, posY;
    private double veloX, veloY;
    private double aclrX, aclrY;

    public Entity(double x, double y, int team) {
        this.team = team;
        posX = x;
        posY = y;
        veloX = 0;
        veloY = 0;
        aclrX = 0;
        aclrY = 0;
    }

    public EntityPrototype getEntityPrototype() {
        return entityPrototype;
    }

    protected void setEntityPrototype(EntityPrototype prototype) {
        entityPrototype = prototype;
    }

    protected void setBoundingBoxAsPrototype(BoundingBoxGroup prototype) {
        boundingBox = (BoundingBoxGroup) prototype.clone();
    }

    protected void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Update position, and then velocity
     */
    public void updateSpatialStatus() {
        posX += veloX;
        posY += veloY;
        veloX += aclrX;
        veloY += aclrY;
        if (boundingBox != null) {
            boundingBox.setOffset(posX, posY);
        }
    }

    public void setPosition(double x, double y) {
        posX = x;
        posY = y;
    }

    public void setVelocity(double x, double y) {
        veloX = x;
        veloY = y;
    }

    public void setVelocityRadius(double r, double a) {
        veloX = r * Math.cos(a);
        veloY = r * Math.sin(a);
    }

    public void setAcceleration(double x, double y) {
        aclrX = x;
        aclrY = y;
    }

    public double getX() {
        return posX;
    }

    public double getY() {
        return posY;
    }

    public int getSizeX() {
        return entityPrototype.getSizeX();
    }

    public int getSizeY() {
        return entityPrototype.getSizeY();
    }

    public int getTeam() {
        return team;
    }

    public boolean intersects(Entity e) {
        if (e == null) {
            return false;
        }
        if (boundingBox == null || e.boundingBox == null) {
            return false;
        }
        return boundingBox.intersects(e.boundingBox);
    }

    @Override
    public String toString() {
        return typeName + "@(" + posX + ", " + posY + ")";
    }
}
