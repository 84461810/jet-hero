package org.hdme.jethero.game;

import org.hdme.jethero.controller.EntityManager;
import org.hdme.jethero.model.Entity;
import org.hdme.jethero.view.PlayerActionListener;
import org.hdme.jethero.view.SoloGamePanel;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class SoloGame {
    private GameRule gamerule;
    private List<Entity> entities;
    private EntityManager entityManager;
    private SoloGamePanel panel;
    private PlayerActionListener listener;

    public SoloGame() {
        gamerule = new GameRule();
        entities = new LinkedList<>();
        entityManager = new EntityManager(entities, gamerule);
        panel = new SoloGamePanel();
        panel.bindEntityMap(entities);
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
}
