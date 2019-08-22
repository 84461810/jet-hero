package org.hdme.jethero.model;

public class PlayerJet extends Jet {
    private int ammo;

    public PlayerJet(double x, double y) {
        super(x, y, Entity.TEAM_ALLY);
        setJetPrototype(JetPrototype.PLAYER_JET);
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        if (ammo >= 0) {
            this.ammo = ammo;
        }
    }
}
