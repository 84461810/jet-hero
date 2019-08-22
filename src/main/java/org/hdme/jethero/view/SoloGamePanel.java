package org.hdme.jethero.view;

import org.hdme.jethero.model.Entity;

import javax.swing.JPanel;
import java.awt.*;
import java.util.List;

public class SoloGamePanel extends JPanel {
    private EntityRenderer entityRenderer;

    public SoloGamePanel() {
        entityRenderer = new EntityRenderer();
        setLayout(null);
        setPreferredSize(new Dimension(800, 600));
        setDoubleBuffered(true);
    }

    public void bindEntityMap(List<Entity> entities) {
        entityRenderer.bindEntityMap(entities);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (entityRenderer != null) {
            entityRenderer.render(g);
        }
    }
}
