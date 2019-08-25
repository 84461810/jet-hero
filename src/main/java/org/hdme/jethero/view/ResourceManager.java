package org.hdme.jethero.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static Map<String, BufferedImage> imageMap = new HashMap<>();

    static {
        try {
            imageMap.put("player_jet", ImageIO.read(ResourceManager.class.getResourceAsStream("/jet.png")));
            imageMap.put("enemy_jet", ImageIO.read(ResourceManager.class.getResourceAsStream("/enemy_jet.png")));
            imageMap.put("bullet", ImageIO.read(ResourceManager.class.getResourceAsStream("/bullet.png")));
            imageMap.put("bg_ocean", ImageIO.read(ResourceManager.class.getResourceAsStream("/bg_ocean.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getImage(String name) {
        return imageMap.get(name);
    }
}
