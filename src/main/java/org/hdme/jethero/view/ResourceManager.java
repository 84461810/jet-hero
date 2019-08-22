package org.hdme.jethero.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static Map<String, Image> imageMap = new HashMap<>();

    static {
        try {
            imageMap.put("player_jet", ImageIO.read(ResourceManager.class.getResourceAsStream("/jet.png")));
            imageMap.put("enemy_jet", ImageIO.read(ResourceManager.class.getResourceAsStream("/enemy_jet.png")));
            imageMap.put("bullet", ImageIO.read(ResourceManager.class.getResourceAsStream("/bullet.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Image getImage(String name) {
        return imageMap.get(name);
    }
}
