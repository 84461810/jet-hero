package org.hdme.jethero.model;

public class ScoreBoard {
    private int enemyAmountKilled = 0;

    public int getEnemyAmountKilled() {
        return enemyAmountKilled;
    }

    public void addEnemyAmountKilled(int inc) {
        if (inc > 0) {
            enemyAmountKilled += inc;
        }
    }

    public int getScore() {
        return enemyAmountKilled;
    }
}
