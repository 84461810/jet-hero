package org.hdme.jethero.controller;

import org.hdme.jethero.game.GameRule;
import org.hdme.jethero.model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Manages status of all the entities (including player) in a specific map.
 */
public class EntityManager {
    private GameRule gamerule;
    private ScoreBoard scoreBoard;
    private List<Entity> entities;
    private PlayerJet player;
    private MovingDirection playerDirection = MovingDirection.NONE;

    private int enemyGeneratorTimer;
    private boolean playerCrashed = false;

    public EntityManager(List<Entity> entities, GameRule rule, ScoreBoard board) {
        this.entities = entities;
        gamerule = rule;
        scoreBoard = board;
        if (rule != null) {
            // setup player jet
            player = new PlayerJet(rule.getPlayerSpawnPosX(), rule.getPlayerSpawnPosY());
            player.setAmmo(gamerule.getPlayerMaxAmmo());
            addEntity(player);
            // setup enemy generator timer
            enemyGeneratorTimer = gamerule.getTimeFirstTryGeneratingEnemies();
        }
        // empty map upon null value of game rule
    }

    private void addEntity(Entity e) {
        if (e == null) {
            return;
        }
        if (!entities.add(e)) {
            System.out.println("Fail to add entity " + e);
        }
    }
    
    private void addEntities(List<Entity> list) {
        for (Entity e : list) {
            addEntity(e);
        }
    }
    
    private void removeEntity(Entity e) {
        if (e == null) {
            return;
        }
        if (!entities.remove(e)) {
            System.out.println("Fail to remove entity " + e);
        }
        if (e instanceof Bullet) {
            Bullet b = (Bullet) e;
            if (b.getOwner() == player && player != null) {
                // recycle player's bullet
                player.increaseAmmo(1);
            }
        } else if (e == player) {
            // player crashes => game over
            playerCrashed = true;
        }
    }

    private void removeEntities(List<Entity> list) {
        for (Entity e : list) {
            removeEntity(e);
        }
    }

    private void updatePlayerVelocity() {
        if (player == null) {
            return;
        }
        double maxV = JetPrototype.PLAYER_JET.getMaxVelocity();
        double sqrt2 = Math.sqrt(2);
        switch (playerDirection) {
            case NONE:
                player.setVelocity(0, 0);
                break;
            case UP:
                player.setVelocity(0, -maxV);
                break;
            case UP_RIGHT:
                player.setVelocity(maxV / sqrt2, -maxV / sqrt2);
                break;
            case RIGHT:
                player.setVelocity(maxV, 0);
                break;
            case DOWN_RIGHT:
                player.setVelocity(maxV / sqrt2, maxV / sqrt2);
                break;
            case DOWN:
                player.setVelocity(0, maxV);
                break;
            case DOWN_LEFT:
                player.setVelocity(-maxV / sqrt2, maxV / sqrt2);
                break;
            case LEFT:
                player.setVelocity(-maxV, 0);
                break;
            case UP_LEFT:
                player.setVelocity(-maxV / sqrt2, -maxV / sqrt2);
                break;
        }
    }

    private void fixPlayerPosition() {
        if (player == null) {
            return;
        }
        // border detection
        double x = player.getX(), y = player.getY();
        double playerSizeX = player.getSizeX(), playerSizeY = player.getSizeY();
        double stageSizeX = gamerule.getStageSizeX(), stageSizeY = gamerule.getStageSizeY();
        if (x < 0) {
            x = 0;
        } else if (x + playerSizeX > stageSizeX) {
            x = stageSizeX - playerSizeX;
        }
        if (y < 0) {
            y = 0;
        } else if (y + playerSizeY > stageSizeY) {
            y = stageSizeY - playerSizeY;
        }
        player.setPosition(x, y);
    }

    private void checkCollisions() {
        List<Entity> list = new LinkedList<>();
        for (Entity e1 : entities) {
            if (e1 == null) {
                continue;
            }
            for (Entity e2 : entities) {
                if (e2 == null) {
                    continue;
                }
                if (e1.getTeam() != e2.getTeam()) {
                    if (e1.intersects(e2)) {
                        list.add(e1);
                        if (e1 instanceof EnemyJet) {
                            scoreBoard.addEnemyAmountKilled(1);
                        }
                        break;
                    }
                }
            }
        }
        removeEntities(list);
    }

    private void generateBullets() {
        List<Entity> list = new LinkedList<>();
        for (Entity e : entities) {
            if (e == null) {
                continue;
            }
            if (e instanceof Jet) {
                Jet j = (Jet) e;
                if (j.isShooterOn()) {
                    Shooter[] shooters = j.getJetPrototype().getShooters();
                    // check player's ammo
                    if (j instanceof PlayerJet) {
                        PlayerJet p = (PlayerJet) j;
                        if (!p.consumeAmmo()) {
                            // no enough ammo
                            continue;
                        }
                    }
                    for (int i = 0; i < shooters.length; i++) {
                        Shooter shooter = shooters[i];
                        Bullet b = new Bullet(e, j.getX() + shooter.getOffsetX(),
                                j.getY() + shooter.getOffsetY(), j.getTeam());
                        b.setVelocityRadius(shooter.getBulletVelo(), shooter.getDirection());
                        list.add(b);
                    }
                    j.setShooterOn(false);
                }
            }
        }
        addEntities(list);
    }

    private void removeOutRangeEntities() {
        List<Entity> list = new LinkedList<>();
        for (Entity e : entities) {
            if (e == null) {
                continue;
            }
            double x1 = e.getX(), y1 = e.getY();
            double x2 = x1 + e.getSizeX(), y2 = y1 + e.getSizeY();
            if (e instanceof Bullet) {
                if (x2 < 0 || x1 > gamerule.getStageSizeX() || y2 < 0 || y1 > gamerule.getStageSizeY()) {
                    list.add(e);
                }
            } else {
                if (y1 > gamerule.getStageSizeY()) {
                    list.add(e);
                }
            }
        }
        removeEntities(list);
    }

    private int getEnemyJetCount() {
        int count = 0;
        for (Entity e : entities) {
            if (e instanceof EnemyJet) {
                count++;
            }
        }
        return count;
    }

    private void tryGeneratingEnemies() {
        enemyGeneratorTimer--;
        if (enemyGeneratorTimer <= 0) {
            // generate random amount of enemy jets
            Random random = new Random();
            random.setSeed(System.currentTimeMillis());
            int maxAmount = gamerule.getEnemyGroupMaxSize(), minAmount = gamerule.getEnemyGroupMinSize();
            int amount = random.nextInt((maxAmount - minAmount + 1)) + minAmount;
            int exist = getEnemyJetCount(), maxEnemyCount = gamerule.getMaxEnemyCount();
            for (int i = 0; i < amount && exist + i < maxEnemyCount; i++) {
                // x is random, while y is fixed (the top border)
                double x = random.nextDouble() * (gamerule.getStageSizeX() - EntityPrototype.JET_MEDIUM.getSizeX());
                double y = -EntityPrototype.JET_MEDIUM.getSizeY();
                EnemyJet enemy = new EnemyJet(x, y);
                enemy.setCharging(true);
                enemy.setVelocityX(Math.sqrt(gamerule.getEnemyVibrationCoefficient()) * gamerule.getEnemyVibrationRadius());
                addEntity(enemy);
            }
            enemyGeneratorTimer = gamerule.getIntervalGeneratingEnemies();
        }
    }

    private void enemiesActs() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        for (Entity e : entities) {
            if (e == null) {
                continue;
            }
            if (e instanceof EnemyJet) {
                EnemyJet enemy = (EnemyJet) e;
                if (!enemy.decreaseChargeTimer()) {
                    // change charge status
                    enemy.setCharging(random.nextBoolean());
                    // reset charge timer
                    enemy.setChargeTimer(gamerule.getIntervalEnemyTryCharging());
                    enemy.decreaseChargeTimer();
                }
                // vibration
                enemy.setAcceleration((enemy.getCenterX() - enemy.getX()) * gamerule.getEnemyVibrationCoefficient(), 0);
                // charge
                if (enemy.isCharging()) {
                    double v = enemy.getJetPrototype().getMaxVelocity();
                    double vx = enemy.getVelocityX();
                    if (Math.abs(v) <= Math.abs(vx)) {
                        enemy.setVelocityY(0);
                    } else {
                        enemy.setVelocityY(v * v - vx * vx);
                    }
                } else {
                    enemy.setVelocityY(0);
                }
                if (!enemy.decreaseShootTimer()) {
                    // enemy tries shooting
                    if (random.nextBoolean()) {
                        // shoot
                        enemy.setShooterOn(true);
                        enemy.setShootTimer(gamerule.getIntervalEnemyTryShootingOnSuccess());
                    } else {
                        // try next time
                        enemy.setShootTimer(gamerule.getIntervalEnemyTryShootingOnFail());
                    }
                    enemy.decreaseShootTimer();
                }
            }
        }
    }

    /**
     * Updates status of each entity in the map,
     * and adds or removes entities according to the mechanism.
     *
     * <br/>Should be called continuously in a loop with an interval(ms) of
     * <code>org.hdme.jethero.model.Entity.TICK_DURATION</code>.
     */
    public void update() {
        for (Entity e : entities) {
            e.updateSpatialStatus();
        }
        fixPlayerPosition();
        updatePlayerVelocity();
        removeOutRangeEntities();
        checkCollisions();
        generateBullets();
        tryGeneratingEnemies();
        enemiesActs();
    }

    public void setPlayerMovingDirection(MovingDirection direction) {
        playerDirection = direction;
    }

    public void setPlayerShooterOn(boolean b) {
        if (player == null) {
            return;
        }
        if (b) {
            if (!player.isAbleToConsumeAmmo()) {
                // no enough ammo
                return;
            }
        }
        player.setShooterOn(b);
    }

    public boolean isPlayerCrashed() {
        return playerCrashed;
    }
}
