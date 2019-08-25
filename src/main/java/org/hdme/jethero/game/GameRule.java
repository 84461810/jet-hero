package org.hdme.jethero.game;

import java.util.Random;

public class GameRule {
    double stageSizeX = 800, stageSizeY = 600;
    double playerSpawnPosX = 360, playerSpawnPosY = 480;
    int playerMaxAmmo = 8;
    int timeFirstTryGeneratingEnemies = 600;
    int intervalGeneratingEnemies = 1600;
    int enemyGroupMinSize = 1, enemyGroupMaxSize = 4;
    int maxEnemyCount = 5;
    int intervalEnemyTryChargingMin = 300, intervalEnemyTryChargingMax = 500;
    int intervalEnemyTryShootingOnSuccessMin = 400, intervalEnemyTryShootingOnSuccessMax = 600;
    int intervalEnemyTryShootingOnFailMin = 200, intervalEnemyTryShootingOnFailMax = 300;
    double enemyVibrationCoefficient = 0.0001;
    double enemyVibrationRadius = 40;

    private Random random;

    public GameRule() {
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    // [min, max)
    private int nextInt(int min, int max) {
        if (min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        return random.nextInt(max - min) + min;
    }

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

    public int getIntervalEnemyTryCharging() {
        return nextInt(intervalEnemyTryChargingMin, intervalEnemyTryChargingMax);
    }

    public int getIntervalEnemyTryShootingOnSuccess() {
        return nextInt(intervalEnemyTryShootingOnSuccessMin, intervalEnemyTryShootingOnSuccessMax);
    }

    public int getIntervalEnemyTryShootingOnFail() {
        return nextInt(intervalEnemyTryShootingOnFailMin, intervalEnemyTryShootingOnFailMax);
    }

    public double getEnemyVibrationCoefficient() {
        return enemyVibrationCoefficient;
    }

    public double getEnemyVibrationRadius() {
        return enemyVibrationRadius;
    }
}
