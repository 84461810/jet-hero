package org.hdme.jethero.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ScrollRenderer extends Renderer {
    private double y = 900;
    private double rate = 0.6;

    private void updateY() {
        y -= rate;
        if (y < 0) {
            y += 1500;
        }
    }

    @Override
    public void render(Graphics g) {
        int y = new Double(this.y).intValue();
        if (y > 900) {
            int height1 = 1500 - y;
            BufferedImage img = ResourceManager.getImage("bg_ocean");
            // up
            BufferedImage img1 = img.getSubimage(0, y, 800, height1);
            // down
            BufferedImage img2 = img.getSubimage(0, 0, 800, 600 - height1);
            g.drawImage(img1, 0, 0, null);
            g.drawImage(img2, 0, height1, null);
        } else {
            BufferedImage img = ResourceManager.getImage("bg_ocean")
                    .getSubimage(0, y, 800, 600);
            g.drawImage(img, 0, 0, null);
        }
        updateY();
    }
}
