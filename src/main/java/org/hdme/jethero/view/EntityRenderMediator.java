package org.hdme.jethero.view;

import org.hdme.jethero.model.Entity;
import org.hdme.jethero.model.EntityType;
import org.hdme.jethero.model.EntityUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntityRenderMediator {
    private static Map<EntityType, String> resourceNameList = new HashMap<>();

    static {
        resourceNameList.put(EntityType.PLAYER_JET, "player_jet");
        resourceNameList.put(EntityType.ENEMY_JET, "enemy_jet");
        resourceNameList.put(EntityType.BULLET, "bullet");
    }

    private List<Entity> entities;

    public EntityRenderMediator() {
        // empty
    }

    public EntityRenderMediator(List<Entity> entities) {
        bindEntityMap(entities);
    }

    public void bindEntityMap(List<Entity> entities) {
        this.entities = entities;
    }

    public List<RenderItem> getRenderList() {
        List<RenderItem> list = new LinkedList<>();
        if (entities == null) {
            return list;
        }
        for (Entity e : entities) {
            EntityType type = EntityUtil.getEntityType(e);
            if (type != null) {
                list.add(new RenderItem(new Double(e.getX()).intValue(),
                        new Double(e.getY()).intValue(), resourceNameList.get(type)));
            }
        }
        return list;
    }
}
