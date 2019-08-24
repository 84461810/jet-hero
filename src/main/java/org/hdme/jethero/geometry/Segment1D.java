package org.hdme.jethero.geometry;

/**
 * Line segment with 1-dimension coordinates.
 */
public class Segment1D {
    private double x1, x2;

    public Segment1D(double x1, double x2) {
        if (x1 > x2) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        this.x1 = x1;
        this.x2 = x2;
    }

    private boolean segmentIntersects(double ax1, double ax2, double bx1, double bx2) {
        if ((bx1 > ax1 && bx1 < ax2) || (bx2 > ax1 && bx2 < ax2)) {
            return true;
        }
        return bx1 <= ax1 && bx2 >= ax2;
    }

    public boolean intersects(Segment1D s) {
        return segmentIntersects(x1, x2, s.x1, s.x2);
    }
}
