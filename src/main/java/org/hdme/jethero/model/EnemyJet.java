package org.hdme.jethero.model;

public class EnemyJet extends Jet {
    private int moveTimer;
    private int shootTimer;

    public EnemyJet(double x, double y) {
        super(x, y, Entity.TEAM_ENEMY);
        setJetPrototype(JetPrototype.ENEMY_JET);
        setBoundingBoxAsPrototype(BoundingBoxGroup.ENEMY_JET);
        setTypeName("EnemyJet");
    }

    public int getMoveTimer() {
        return moveTimer;
    }

    public void setMoveTimer(int moveTimer) {
        this.moveTimer = moveTimer;
    }

    public int getShootTimer() {
        return shootTimer;
    }

    public void setShootTimer(int shootTimer) {
        this.shootTimer = shootTimer;
    }
}
