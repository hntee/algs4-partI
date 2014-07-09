import java.util.Arrays;

public class Fast {
    // print Point p and Points[start] to Points[end-1]
    private static void print(Point p, Point[] points, int start, int end) {
        StdOut.print(p + " -> ");
        for (int i = start; i < end - 1; i++)
            StdOut.print(points[i] + " -> ");
        StdOut.println(points[end - 1]);
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
        final int N = StdIn.readInt();
        Point[] points = new Point[N];

        // an auxiliary array
        Point[] aux = new Point[N];

        // read the points
        for (int i = 0; i < N; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
            aux[i] = new Point(x, y);
        }

        // sort the points
        Arrays.sort(points, 0, N);
        Arrays.sort(aux, 0, N);

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
                        print(points[i], points, start, start + count);
                    }
                    start = j;
                    count = 1;
                }

                if (j == N - 1 && count >= 3 && isLeast(points[i], points, points[i].slopeTo(points[j - 1])))
                    print(points[i], points, start, start + count);
            }
            

            // copy aux back to points
            for (int q = 0; q < points.length; q++)
                points[q] = aux[q];

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
