package org.hdme.jethero.model;

public enum EntityType {
    PLAYER_JET(0), ENEMY_JET(1), BULLET(2);

    private int id;

    EntityType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
