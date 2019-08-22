package org.hdme.jethero.model;

public class EntityUtil {
    public static EntityType getEntityType(Entity e) {
        if (e == null) {
            return null;
        }
        if (e instanceof Jet) {
            if (e instanceof PlayerJet) {
                return EntityType.PLAYER_JET;
            } else if (e instanceof EnemyJet) {
                return EntityType.ENEMY_JET;
            } else {
                return null;
            }
        } else if (e instanceof Bullet) {
            return EntityType.BULLET;
        } else {
            return null;
        }
    }
}
