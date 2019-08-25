package org.hdme.jethero;

import org.hdme.jethero.game.SoloGame;
import org.hdme.jethero.model.Entity;
import org.hdme.jethero.view.PlayerActionListener;
import org.hdme.jethero.view.SoloGamePanel;

import javax.swing.*;

public class Main {
    private static JFrame frame;

    public static void main(String[] args) {
        // local variables
        SoloGame soloGame;
        SoloGamePanel panel = null;
        PlayerActionListener listener = null;
        boolean retry = true;
        // UI settings
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame("Jet Hero v0.1");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocation(40, 30);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // main loop
        while (retry) {
            // load the game
            soloGame = new SoloGame();
            if (panel != null) {
                frame.remove(panel);
            }
            if (listener != null) {
                frame.removeKeyListener(listener);
            }
            panel = soloGame.getGamePanel();
            listener = soloGame.getGameListener();
            frame.add(panel);
            frame.setContentPane(panel);
            frame.pack();
            frame.addKeyListener(listener); // only add as key listener (temp)
            // show the window
            frame.setVisible(true);
            while (!soloGame.isGameOver()) {
                soloGame.update();
                frame.repaint();
                try {
                    Thread.sleep(Entity.TICK_DURATION);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            retry = JOptionPane.showConfirmDialog(frame,
                    "Score: " + soloGame.getScore() + ". Try again?",
                    "You failed.", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION;
        }
        System.exit(0);
    }
}
