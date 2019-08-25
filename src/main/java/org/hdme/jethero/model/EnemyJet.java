package org.hdme.jethero.model;

public class EnemyJet extends Jet {
    private int chargeTimer;
    private int shootTimer;
    private double centerX;
    private boolean charging;

    public EnemyJet(double x, double y) {
        super(x, y, Entity.TEAM_ENEMY);
        setJetPrototype(JetPrototype.ENEMY_JET);
        setBoundingBoxAsPrototype(BoundingBoxGroup.ENEMY_JET);
        setTypeName("EnemyJet");
        centerX = x;
    }

    public int getChargeTimer() {
        return chargeTimer;
    }

    public void setChargeTimer(int chargeTimer) {
        if (chargeTimer >= 0) {
            this.chargeTimer = chargeTimer;
        }
    }

    public boolean decreaseChargeTimer() {
        if (chargeTimer <= 0) {
            return false;
        }
        chargeTimer--;
        return true;
    }

    public int getShootTimer() {
        return shootTimer;
    }

    public void setShootTimer(int shootTimer) {
        if (shootTimer >= 0) {
            this.shootTimer = shootTimer;
        }
    }

    public boolean decreaseShootTimer() {
        if (shootTimer <= 0) {
            return false;
        }
        shootTimer--;
        return true;
    }

    public double getCenterX() {
        return centerX;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    @Override
    public void updateSpatialStatus() {
        super.updateSpatialStatus();
    }
}
