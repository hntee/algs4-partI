public class PointTest {
    public Point p0 = new Point(0, 0);
    public Point p1 = new Point(1, 1);
    public Point p2 = new Point(2, 2);
    public Point p3 = new Point(1, 3);
    public Point p4 = new Point(0, 1);
    public Point p5 = new Point(0, -1);

    public void testSlopeOrder() {
        assert p0.SLOPE_ORDER.compare(p0, p1) == -1;
        assert p0.SLOPE_ORDER.compare(p0, p0) == 0;
        assert p0.SLOPE_ORDER.compare(p1, p1) == 0;
        assert p0.SLOPE_ORDER.compare(p1, p2) == 0;
        assert p0.SLOPE_ORDER.compare(p4, p5) == 0;
        assert p0.SLOPE_ORDER.compare(p1, p3) == -1;
        assert p0.SLOPE_ORDER.compare(p3, p1) == 1;
        assert p0.SLOPE_ORDER.compare(p3, p4) == -1;
        assert p0.SLOPE_ORDER.compare(p3, p5) == -1;
    }

    public void testSlopeTo() {
        assert p4.slopeTo(p1) == 0;
        assert p0.slopeTo(p4) == Double.POSITIVE_INFINITY;
        assert p0.slopeTo(p5) == Double.POSITIVE_INFINITY;
        assert p4.slopeTo(p4) == Double.NEGATIVE_INFINITY;
        assert p0.slopeTo(p1) == 1;
        assert p0.slopeTo(p3) == 3;
        assert p3.slopeTo(p2) == -1;
    }

    public void testCompareTo() {
        assert p0.compareTo(p1) < 0;
        assert p0.compareTo(p4) < 0;
        assert p0.compareTo(p5) > 0;
        assert p4.compareTo(p1) < 0;
    }

    public static void main(String[] args) {
        PointTest pt = new PointTest();
        pt.testSlopeOrder();
        pt.testSlopeTo();
        pt.testCompareTo();
    }
}