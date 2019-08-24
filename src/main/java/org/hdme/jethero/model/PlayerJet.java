package org.hdme.jethero.model;

public class PlayerJet extends Jet {
    private int ammo;

    public PlayerJet(double x, double y) {
        super(x, y, Entity.TEAM_ALLY);
        setJetPrototype(JetPrototype.PLAYER_JET);
        setBoundingBoxAsPrototype(BoundingBoxGroup.PLAYER_JET);
        setTypeName("PlayerJet");
    }

    public int getAmmo() {
        return ammo;
    }

    public boolean setAmmo(int ammo) {
        if (ammo >= 0) {
            this.ammo = ammo;
            return true;
        }
        return false;
    }

    public boolean increaseAmmo(int inc) {
        return setAmmo(ammo + inc);
    }

    public boolean decreaseAmmo(int dec) {
        return setAmmo(ammo - dec);
    }

    public boolean consumeAmmo() {
        return decreaseAmmo(getShooterAmount());
    }

    public boolean isAbleToConsumeAmmo() {
        return ammo >= getShooterAmount();
    }
}
