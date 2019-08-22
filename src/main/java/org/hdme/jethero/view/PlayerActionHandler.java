package org.hdme.jethero.view;

import org.hdme.jethero.controller.EntityManager;
import org.hdme.jethero.controller.MovingDirection;

public class PlayerActionHandler {
    private EntityManager entityManager;

    public PlayerActionHandler() {
        // empty
    }

    public PlayerActionHandler(EntityManager entityManager) {
        bindEntityManager(entityManager);
    }

    public void bindEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void onMove(MovingDirection direction) {
        if (direction != null && entityManager != null) {
            entityManager.setPlayerMovingDirection(direction);
        }
    }
}
