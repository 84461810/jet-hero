package org.hdme.jethero.game;

public class GameRule {
    double stageSizeX = 800, stageSizeY = 600;
    double playerSpawnPosX = 360, playerSpawnPosY = 480;
    int playerMaxAmmo = 8;
    int timeFirstTryGeneratingEnemies = 600;
    int intervalGeneratingEnemies = 1600;
    int enemyGroupMinSize = 1, enemyGroupMaxSize = 4;
    int maxEnemyCount = 5;

    public double getStageSizeX() {
        return stageSizeX;
    }

    public double getStageSizeY() {
        return stageSizeY;
    }

    public double getPlayerSpawnPosX() {
        return playerSpawnPosX;
    }

    public double getPlayerSpawnPosY() {
        return playerSpawnPosY;
    }

    public int getPlayerMaxAmmo() {
        return playerMaxAmmo;
    }

    public int getTimeFirstTryGeneratingEnemies() {
        return timeFirstTryGeneratingEnemies;
    }

    public int getIntervalGeneratingEnemies() {
        return intervalGeneratingEnemies;
    }

    public int getEnemyGroupMinSize() {
        return enemyGroupMinSize;
    }

    public int getEnemyGroupMaxSize() {
        return enemyGroupMaxSize;
    }

    public int getMaxEnemyCount() {
        return maxEnemyCount;
    }
}
