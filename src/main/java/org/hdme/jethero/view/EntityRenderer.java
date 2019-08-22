package org.hdme.jethero.view;

import org.hdme.jethero.model.Entity;

import java.awt.*;
import java.util.List;


public class EntityRenderer extends Renderer {
    private EntityRenderMediator mediator;

    public EntityRenderer() {
        mediator = new EntityRenderMediator();
    }

    public EntityRenderer(List<Entity> entities) {
        mediator = new EntityRenderMediator(entities);
    }

    public void bindEntityMap(List<Entity> entities) {
        mediator.bindEntityMap(entities);
    }

    @Override
    protected void render(Graphics g) {
        if (mediator == null) {
            return;
        }
        List<RenderItem> list = mediator.getRenderList();
        for (RenderItem item : list) {
            Image img = ResourceManager.getImage(item.getResourceName());
            if (img != null) {
                g.drawImage(img, item.getX(), item.getY(), null);
            }
        }
    }
}
