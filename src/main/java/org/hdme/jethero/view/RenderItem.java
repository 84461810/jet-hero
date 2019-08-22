package org.hdme.jethero.view;

public class RenderItem {
    private int x, y;
    private String resourceName;

    public RenderItem(int x, int y, String resourceName) {
        this.x = x;
        this.y = y;
        this.resourceName = resourceName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getResourceName() {
        return resourceName;
    }
}
