package org.hdme.jethero.geometry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Polygon implements Cloneable {
    private List<Point> points, pointsWithOffset;
    private double offsetX = 0, offsetY = 0;
    private boolean offsetChanged = true;

    public Polygon() {
        points = new LinkedList<>();
    }

    public Polygon addPoint(Point p) {
        if (p == null) {
            return this;
        }
        points.add(p);
        return this;
    }

    public Polygon addPoint(double x, double y) {
        addPoint(new Point(x, y));
        return this;
    }

    public void setOffset(double offsetX, double offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        offsetChanged = true;
    }

    private List<Point> getPointsWithOffset() {
        if (offsetChanged) {
            // recalculate on offset change
            pointsWithOffset = new ArrayList<>();
            for (Point p : points) {
                pointsWithOffset.add(p.getWithOffset(offsetX, offsetY));
            }
            offsetChanged = false;
        }
        return pointsWithOffset;
    }

    private double dot(double x1, double y1, double x2, double y2) {
        return x1 * x2 + y1 * y2;
    }

    private double det(double a11, double a12, double a21, double a22) {
        return a11 * a22 - a12 * a21;
    }

    // Get the solution (x, y) of equation group:
    // a1 * x + b1 * y = c1
    // a2 * x + b2 * y = c2
    private double[] solve(double a1, double b1, double c1, double a2, double b2, double c2) {
        double d = det(a1, b1, a2, b2), dx = det(c1, b1, c2, b2), dy = det(a1, c1, a2, c2);
        return new double[]{dx / d, dy / d};
    }

    // project along the vector (p1 -> p2) and return p3's coordinates
    private double projectAlong(Point p1, Point p2, Point p3) {
        if (p3.equals(p1) || p3.equals(p2)) {
            return 0;
        }
        double x1 = p1.x, y1 = p1.y, x3 = p3.x, y3 = p3.y;
        double a = p2.x - x1, b = p2.y - y1;
        double[] cross = solve(a, b, a * x3 + b * y3, -b, a, -b * x1 + a * y1);
        double c = x3 - cross[0], d = y3 - cross[1];
        double len = Math.sqrt(c * c + d * d);
        // rotate p1->p2 (a, b) by 90 degrees to get a new axis (-b, a)
        if (dot(-b, a, c, d) < 0) {
            len = -len;
        }
        return len;
    }

    // project along the line (determined by p1 and p2) and return the polygon's projection
    private Segment1D projectAlong(Point p1, Point p2) {
        double min = Double.MAX_VALUE, max = -Double.MAX_VALUE;
        List<Point> list = getPointsWithOffset();
        for (Point p : list) {
            double c = projectAlong(p1, p2, p);
            if (min > c) {
                min = c;
            }
            if (max < c) {
                max = c;
            }
        }
        return new Segment1D(min, max);
    }

    public boolean intersects(Polygon poly) {
        List<Point[]> edges = new LinkedList<>();
        List<Point> list = getPointsWithOffset();
        int maxi = list.size();
        for (int i = 0; i < maxi; i++) {
            int j = (i + 1) % maxi;
            edges.add(new Point[]{list.get(i), list.get(j)});
        }
        list = poly.getPointsWithOffset();
        maxi = list.size();
        for (int i = 0; i < maxi; i++) {
            int j = (i + 1) % maxi;
            edges.add(new Point[]{list.get(i), list.get(j)});
        }
        for (Point[] edge : edges) {
            Segment1D s1 = projectAlong(edge[0], edge[1]);
            Segment1D s2 = poly.projectAlong(edge[0], edge[1]);
            if (!s1.intersects(s2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Polygon [");
        List<Point> list = getPointsWithOffset();
        for (Point p : list) {
            buffer.append("(").append(p.x).append(", ").append(p.y).append("), ");
        }
        buffer.append("]");
        return buffer.toString();
    }

    @Override
    public Object clone() {
        try {
            Polygon p = (Polygon) (super.clone());
            p.points = new LinkedList<>();
            for (Point pt : points) {
                p.points.add((Point) pt.clone());
            }
            p.offsetChanged = true;
            return p;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
