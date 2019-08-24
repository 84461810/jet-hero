package org.hdme.jethero.model;

import org.hdme.jethero.geometry.Polygon;

import java.util.LinkedList;
import java.util.List;

public class BoundingBoxGroup implements Cloneable {
    public static final BoundingBoxGroup PLAYER_JET = new BoundingBoxGroup();
    //todo: add player bounding box
    public static final BoundingBoxGroup ENEMY_JET = new BoundingBoxGroup()
            .addBoundingBox(new Polygon().addPoint(19, 0).addPoint(61, 0).addPoint(61, 50).addPoint(19, 50))
            .addBoundingBox(new Polygon().addPoint(19, 15).addPoint(0, 19).addPoint(0, 31).addPoint(19, 50))
            .addBoundingBox(new Polygon().addPoint(61, 15).addPoint(80, 19).addPoint(80, 31).addPoint(61, 50))
            .addBoundingBox(new Polygon().addPoint(19, 50).addPoint(39, 100).addPoint(41, 100).addPoint(61, 50));
    public static final BoundingBoxGroup BULLET = new BoundingBoxGroup()
            .addBoundingBox(new Polygon().addPoint(0, 0).addPoint(6, 0).addPoint(6, 6).addPoint(0, 6));

    private List<Polygon> boundingBoxes;

    public BoundingBoxGroup() {
        boundingBoxes = new LinkedList<>();
    }

    public void setOffset(double offsetX, double offsetY) {
        for (Polygon p : boundingBoxes) {
            p.setOffset(offsetX, offsetY);
        }
    }

    private BoundingBoxGroup addBoundingBox(Polygon poly) {
        if (poly != null) {
            boundingBoxes.add(poly);
        }
        return this;
    }

    public boolean intersects(BoundingBoxGroup b) {
        if (b == null) {
            return false;
        }
        for (Polygon p1 : boundingBoxes) {
            if (p1 == null) {
                continue;
            }
            for (Polygon p2 : b.boundingBoxes) {
                if (p2 == null) {
                    continue;
                }
                if (p1.intersects(p2)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Object clone() {
        try {
            BoundingBoxGroup b = (BoundingBoxGroup) (super.clone());
            b.boundingBoxes = new LinkedList<>();
            for (Polygon p : boundingBoxes) {
                b.boundingBoxes.add((Polygon) p.clone());
            }
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
