import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        final int N = StdIn.readInt();
        Point[] points = new Point[N];

        // read the points
        for (int i = 0; i < N; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }

        // sort the points
        Arrays.sort(points, 0, N);

        for (int i = 0; i < N - 3; i++)
            for (int j = i + 1; j < N - 2; j++)
                for (int k = j + 1; k < N - 1; k++)
                    for (int p = k + 1; p < N; p++) {
                        // don't have the same slope
                        if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) continue;
                        if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[p])) continue;

                        // have the same slope
                        StdOut.print(points[i] + " -> ");
                        StdOut.print(points[j] + " -> ");
                        StdOut.print(points[k] + " -> ");
                        StdOut.print(points[p] + "\n");
                    }
    }
}