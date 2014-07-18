// test file: http://coursera.cs.princeton.edu/algs4/testing/collinear-testing.zip

import java.util.Arrays;

public class Fast {
    private final static int DELAY = 200;
    // print Point p and Points[start] to Points[end-1]
    private static void printAndDraw(Point p, Point[] points, int start, int end) {
        StdOut.print(p + " -> ");
        for (int i = start; i < end - 1; i++) {
            StdOut.print(points[i] + " -> ");
        }

        StdOut.println(points[end - 1]);
        p.drawTo(points[end - 1]);
    }

    // check if a point is the least element in a set of
    // fragments of a given slope
    private static boolean isLeast(Point p, Point[] points, double slope) {
        final int n = points.length;

        // copy the points array
        Point[] temp = new Point[n];
        for (int i = 0; i < n; i++) {
            temp[i] = points[i];
        }

        // 1- For any virtual origin, consider all points.
        // 2- For any segment, check if the origin is the least element.
        Arrays.sort(temp, 0, n, p.SLOPE_ORDER);

        for (int i = 1; i < n; i++) {
            if (p.slopeTo(temp[i]) == slope) {
                if (p.compareTo(temp[i]) > 0)
                    return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        //StdDraw.setXscale(0, 15);
        //StdDraw.setYscale(-10, 10);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();

        Point[] points = new Point[N];

        // read the points
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        // sort the points
        Arrays.sort(points, 0, N);

        Point[] aux = points.clone();

        for (int i = 0; i < N - 3; i++) {
            // sort in slope
            Arrays.sort(points, i + 1, N, points[i].SLOPE_ORDER);
            
            // find a list of colinear points and print
            int count = 1, start = i + 1;
            for (int j = 1; j < N; j++) {
                if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[j - 1])){
                    count++;
                } else {
                    if (count >= 3 && isLeast(points[i], points, points[i].slopeTo(points[j - 1]))) {
                        printAndDraw(points[i], points, start, start + count);
                    }
                    start = j;
                    count = 1;
                }

                if (j == N - 1 && count >= 3 && isLeast(points[i], points, points[i].slopeTo(points[j - 1])))
                    printAndDraw(points[i], points, start, start + count);
            }
            

            // copy aux back to points
            /*
            for (int q = 0; q < points.length; q++)
                points[q] = aux[q];
            */

            points = aux.clone();



            /*
            // print the points
            StdOut.println(points[i] + " : ");
            for (int j = i + 1; j < N; j++) {
                StdOut.print(points[j] + " -> ");

            }
            StdOut.println();

            for (int j = i + 1; j < N; j++) {
                StdOut.printf("%.2f -> ", points[i].slopeTo(points[j]));

            }
            StdOut.println();
            StdOut.println();
            */

        }
    }
}
