package org.hdme.jethero.view;

import org.hdme.jethero.controller.EntityManager;
import org.hdme.jethero.controller.MovingDirection;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class PlayerActionListener implements KeyListener {
    private Map<Integer, Boolean> keyStatusList;
    private PlayerActionHandler handler;

    private MovingDirection getDirection() {
        int down = 1, right = 1;
        if (getKeyStatus(KeyEvent.VK_RIGHT)) {
            right++;
        }
        if (getKeyStatus(KeyEvent.VK_LEFT)) {
            right--;
        }
        if (getKeyStatus(KeyEvent.VK_DOWN)) {
            down++;
        }
        if (getKeyStatus(KeyEvent.VK_UP)) {
            down--;
        }
        MovingDirection[][] dir = {
                {MovingDirection.UP_LEFT, MovingDirection.UP, MovingDirection.UP_RIGHT},
                {MovingDirection.LEFT, MovingDirection.NONE, MovingDirection.RIGHT},
                {MovingDirection.DOWN_LEFT, MovingDirection.DOWN, MovingDirection.DOWN_RIGHT}
        };
        return dir[down][right];
    }

    private boolean getKeyStatus(int key) {
        Boolean b = keyStatusList.get(key);
        if (b == null) {
            return false;
        }
        return b;
    }

    public PlayerActionListener() {
        keyStatusList = new HashMap<>();
        handler = new PlayerActionHandler();
    }

    public PlayerActionListener(EntityManager entityManager) {
        keyStatusList = new HashMap<>();
        handler = new PlayerActionHandler(entityManager);
    }

    public void bindEntityManager(EntityManager entityManager) {
        handler.bindEntityManager(entityManager);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        Boolean b = keyStatusList.get(key);
        if (b == null || !b) {
            // set key status
            keyStatusList.put(key, true);
            // call handler
            handler.onMove(getDirection());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        Boolean b = keyStatusList.get(key);
        // set key status
        keyStatusList.put(key, false);
        if (b != null && b) {
            // call handler
            handler.onMove(getDirection());
        }
    }
}
