package org.hdme.jethero.game;

import org.hdme.jethero.controller.EntityManager;
import org.hdme.jethero.model.Entity;
import org.hdme.jethero.model.ScoreBoard;
import org.hdme.jethero.view.PlayerActionListener;
import org.hdme.jethero.view.SoloGamePanel;

import java.util.LinkedList;
import java.util.List;

public class SoloGame {
    private GameRule gamerule;
    private ScoreBoard scoreBoard;
    private List<Entity> entities;
    private EntityManager entityManager;
    private SoloGamePanel panel;
    private PlayerActionListener listener;

    public SoloGame() {
        gamerule = new GameRule();
        scoreBoard = new ScoreBoard();
        entities = new LinkedList<>();
        entityManager = new EntityManager(entities, gamerule, scoreBoard);
        panel = new SoloGamePanel();
        panel.bindEntityMap(entities);
        panel.bindScoreBoard(scoreBoard);
        listener = new PlayerActionListener(entityManager);
    }

    public void update() {
        entityManager.update();
    }

    public SoloGamePanel getGamePanel() {
        return panel;
    }

    public PlayerActionListener getGameListener() {
        return listener;
    }

    public boolean isGameOver() {
        return entityManager.isPlayerCrashed();
    }

    public int getScore() {
        return scoreBoard.getScore();
    }
}
