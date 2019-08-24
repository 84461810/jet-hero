package org.hdme.jethero.model;

public class Bullet extends Entity {
    private Entity owner;

    public Bullet(Entity owner, double x, double y, int team) {
        super(x, y, team);
        setEntityPrototype(EntityPrototype.BULLET);
        setBoundingBoxAsPrototype(BoundingBoxGroup.BULLET);
        setTypeName("Bullet");
        this.owner = owner;
    }

    public Entity getOwner() {
        return owner;
    }
}
