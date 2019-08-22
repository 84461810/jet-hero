package org.hdme.jethero.controller;

public enum MovingDirection {
    NONE(0), UP(1), UP_RIGHT(2), RIGHT(3), DOWN_RIGHT(4),
    DOWN(5), DOWN_LEFT(6), LEFT(7), UP_LEFT(8);

    private static String[] names = {"NONE", "UP", "UP_RIGHT", "RIGHT", "DOWN_RIGHT",
            "DOWN", "DOWN_LEFT", "LEFT", "UP_LEFT"};

    private int id;

    MovingDirection(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return names[id];
    }
}
