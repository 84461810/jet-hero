package org.hdme.jethero.view;

import org.hdme.jethero.model.Entity;
import org.hdme.jethero.model.ScoreBoard;

import javax.swing.JPanel;
import java.awt.*;
import java.util.List;

public class SoloGamePanel extends JPanel {
    private ScrollRenderer scrollRenderer;
    private EntityRenderer entityRenderer;
    private UIRenderer uiRenderer;

    public SoloGamePanel() {
        scrollRenderer = new ScrollRenderer();
        entityRenderer = new EntityRenderer();
        uiRenderer = new UIRenderer();
        setLayout(null);
        setPreferredSize(new Dimension(800, 620));
        setDoubleBuffered(true);
    }

    public void bindEntityMap(List<Entity> entities) {
        entityRenderer.bindEntityMap(entities);
    }

    public void bindScoreBoard(ScoreBoard board) {
        uiRenderer.bindScoreBoard(board);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (scrollRenderer != null) {
            scrollRenderer.render(g);
        }
        if (entityRenderer != null) {
            entityRenderer.render(g);
        }
        if (uiRenderer != null) {
            uiRenderer.render(g);
        }
    }
}
