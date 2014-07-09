/*************************************************************************
 * Name: Hao Tan
 * Email: ihntee@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution: 
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>()
    {
        @Override
        public int compare(Point p0, Point p1)
        {
            double s0 = slopeTo(p0);
            double s1 = slopeTo(p1);
            return Double.compare(s0, s1);
        }
    }; 

    // x coordinate
    private final int x;  
    // y coordinate                            
    private final int y;                             

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        // same x coordinate
        if (this.x == that.x) {
            // a degenerate vertical line (same point)
            if (this.y == that.y) 
                return Double.NEGATIVE_INFINITY; 
            // vertical line
            else
                return Double.POSITIVE_INFINITY;
        } else {
            // to prevent the 0.0 and -0.0 case
            // since 0.0 / 1 = 0.0, 0.0 / -1 = -0.0
            if (this.y == that.y)
                return 0.0;
            else
                return (double) (this.y - that.y) / (this.x - that.x);
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        // equal
        if (this.x == that.x && this.y == that.y)
            return 0;

        // compare by y
        if (this.y != that.y)
            return (this.y < that.y) ? -1 : 1;
        // compare by x
        else {
            return (this.x < that.x) ? -1 : 1;
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {

    }
}