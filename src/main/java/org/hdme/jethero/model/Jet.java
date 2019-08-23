package org.hdme.jethero.model;

public abstract class Jet extends Entity {
    private JetPrototype jetPrototype;
    // whether a bullet will be generated next tick
    private boolean shooterOn = false;

    public Jet(double x, double y, int team) {
        super(x, y, team);
        setEntityPrototype(EntityPrototype.JET_MEDIUM);
    }

    public boolean isShooterOn() {
        return shooterOn;
    }

    public void setShooterOn(boolean shooterOn) {
        this.shooterOn = shooterOn;
    }

    public JetPrototype getJetPrototype() {
        return jetPrototype;
    }

    protected void setJetPrototype(JetPrototype prototype) {
        jetPrototype = prototype;
    }
}
