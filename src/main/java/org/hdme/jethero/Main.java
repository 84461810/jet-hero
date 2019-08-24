package org.hdme.jethero;

import org.hdme.jethero.game.SoloGame;
import org.hdme.jethero.model.Entity;
import org.hdme.jethero.view.SoloGamePanel;

import javax.swing.*;

public class Main {
    private static JFrame frame;

    public static void main(String[] args) {
        // local variables
        SoloGame soloGame;
        // UI settings
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame("Jet Hero");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocation(40, 30);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // load the game
        soloGame = new SoloGame();
        SoloGamePanel panel = soloGame.getGamePanel();
        frame.add(panel);
        frame.setContentPane(panel);
        frame.pack();
        frame.addKeyListener(soloGame.getGameListener()); // only add as key listener (temp)
        // show the window
        frame.setVisible(true);
        // main loop
        while (true) {
            soloGame.update();
            frame.repaint();
            try {
                Thread.sleep(Entity.TICK_DURATION);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
