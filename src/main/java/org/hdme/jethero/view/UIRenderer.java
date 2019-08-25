package org.hdme.jethero.view;

import org.hdme.jethero.model.ScoreBoard;

import java.awt.*;

/**
 * Renders UI parts, including scoreboard.
 */
public class UIRenderer extends Renderer {
    private ScoreBoard scoreBoard;

    @Override
    public void render(Graphics g) {
        if (scoreBoard != null) {
            Font oldFont = g.getFont();
            Color oldColor = g.getColor();
            g.setFont(new Font("Courier New", Font.PLAIN, 16));
            g.setColor(Color.WHITE);
            g.fillRect(0, 600, 800, 20);
            g.setColor(Color.BLACK);
            g.drawString("Score: " + scoreBoard.getScore(), 2, 610);
            g.setFont(oldFont);
            g.setColor(oldColor);
        }
    }

    public void bindScoreBoard(ScoreBoard board) {
        scoreBoard = board;
    }
}
