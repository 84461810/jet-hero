package org.hdme.jethero.controller;

import org.hdme.jethero.game.GameRule;
import org.hdme.jethero.model.*;

import java.util.List;

public class EntityManager {
    private GameRule gamerule;
    private List<Entity> entities;
    private Entity player;
    private MovingDirection playerDirection = MovingDirection.NONE;

    public EntityManager(List<Entity> entities, GameRule rule) {
        this.entities = entities;
        gamerule = rule;
        if (rule != null) {
            player = new PlayerJet(rule.getPlayerSpawnPosX(), rule.getPlayerSpawnPosY());
            addEntity(player);
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

    public void update() {
        // TODO: update entities
        for (Entity e : entities) {
            e.updateSpatialStatus();
        }
        fixPlayerPosition();
        updatePlayerVelocity();
    }

    public void setPlayerMovingDirection(MovingDirection direction) {
        playerDirection = direction;
    }
}
