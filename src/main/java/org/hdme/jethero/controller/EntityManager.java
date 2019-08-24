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
    private List<Entity> entities;
    private Entity player;
    private MovingDirection playerDirection = MovingDirection.NONE;

    private int enemyGeneratorTimer;

    public EntityManager(List<Entity> entities, GameRule rule) {
        this.entities = entities;
        gamerule = rule;
        if (rule != null) {
            // setup player jet
            player = new PlayerJet(rule.getPlayerSpawnPosX(), rule.getPlayerSpawnPosY());
            ((PlayerJet) player).setAmmo(gamerule.getPlayerMaxAmmo());
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
        // TODO: non-rectangle/multi-rectangle collision box
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
                        break;
                    }
                }
            }
        }
        entities.removeAll(list);
    }

    private void generateBullets() {
        List<Bullet> list = new LinkedList<>();
        for (Entity e : entities) {
            if (e == null) {
                continue;
            }
            if (e instanceof Jet) {
                Jet j = (Jet) e;
                if (j.isShooterOn()) {
                    Shooter[] shooters = j.getJetPrototype().getShooters();
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
        entities.addAll(list);
    }

    private void removeOutRangeEntities() {
        List<Entity> list = new LinkedList<>();
        for (Entity e : entities) {
            double x1 = e.getX(), y1 = e.getY();
            double x2 = x1 + e.getSizeX(), y2 = y1 + e.getSizeY();
            if (e instanceof Bullet) {
                if (x1 < 0 || x2 > gamerule.getStageSizeX() || y1 < 0 || y2 > gamerule.getStageSizeY()) {
                    list.add(e);
                    if (((Bullet) e).getOwner() == player && player instanceof PlayerJet) {
                        PlayerJet p = (PlayerJet) player;
                        p.increaseAmmo(1);
                    }
                }
            }
        }
        entities.removeAll(list);
        // TODO: remove jets
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
                //double x = random.nextDouble() * (gamerule.getStageSizeX() - EntityPrototype.JET_MEDIUM.getSizeX());
                double x = random.nextInt(8) * 100;
                //double y = -EntityPrototype.JET_MEDIUM.getSizeY();
                double y = 0;
                entities.add(new EnemyJet(x, y));
            }
            enemyGeneratorTimer = gamerule.getIntervalGeneratingEnemies();
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
        // TODO: update entities
        for (Entity e : entities) {
            e.updateSpatialStatus();
        }
        fixPlayerPosition();
        updatePlayerVelocity();
        removeOutRangeEntities();
        checkCollisions();
        generateBullets();
        tryGeneratingEnemies();
        //doEnemiesAction();
    }

    public void setPlayerMovingDirection(MovingDirection direction) {
        playerDirection = direction;
    }

    public void setPlayerShooterOn(boolean b) {
        if (player == null) {
            return;
        }
        if (b && player instanceof PlayerJet) {
            PlayerJet p = (PlayerJet) player;
            if (!p.isAbleToConsumeAmmo()) {
                // no enough ammo
                return;
            }
        }
        if (player instanceof Jet) {
            ((Jet) player).setShooterOn(b);
        }
    }
}
