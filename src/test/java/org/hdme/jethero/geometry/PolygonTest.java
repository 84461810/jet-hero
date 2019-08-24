package org.hdme.jethero.geometry;

import org.junit.Assert;
import org.junit.Test;

public class PolygonTest {
    @Test
    public void testIntersects() throws Exception {
        Polygon p1 = new Polygon().addPoint(0, 0).addPoint(0, 2).addPoint(2, 2).addPoint(2, 0);
        Polygon p2 = new Polygon().addPoint(1, 1).addPoint(1, 3).addPoint(3, 3).addPoint(3, 1);
        Assert.assertTrue(p1.intersects(p2));
        p2 = new Polygon().addPoint(1, 1).addPoint(1, 3).addPoint(3, 3);
        Assert.assertTrue(p1.intersects(p2));
        p2 = new Polygon().addPoint(1, 1).addPoint(1, 3).addPoint(1.5, 3);
        Assert.assertTrue(p1.intersects(p2));
        p2 = new Polygon().addPoint(0, 2).addPoint(0, 4).addPoint(1, 3);
        Assert.assertFalse(p1.intersects(p2));
        p1 = new Polygon().addPoint(0, 2).addPoint(2, 2).addPoint(1, 3);
        Assert.assertFalse(p1.intersects(p2));
        p1 = new Polygon().addPoint(0, 2).addPoint(2, 2).addPoint(1, 3.1);
        Assert.assertTrue(p1.intersects(p2));
        p1.setOffset(0, 100);
        Assert.assertFalse(p1.intersects(p2));
        p1.setOffset(0, 1);
        Assert.assertTrue(p1.intersects(p2));
        p1.setOffset(1, 1);
        Assert.assertFalse(p1.intersects(p2));
    }
}
