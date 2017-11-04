import java.util.*;

public class PointsAndSegments {

   private static int[] fastCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        Arrays.sort(starts);
        Arrays.sort(ends);

        for (int i = 0; i < points.length; i++) {
            int j = 0;
            int start = 0, end = 0;
            if (starts[0] > points[i]) {
                start = starts.length;
            } else if (starts[starts.length - 1] < points[i]) {
                start = 0;
            } else {
                while (starts[j] <= points[i]) {
                    start = starts.length - j - 1;
                    if (j == starts.length-1) {
                        break;
                    }
                    j++;
                }
            }
            j = 0;
            if (ends[ends.length - 1] < points[i]) {
                end = ends.length;
            } else if (ends[0] > points[i]) {
                end = 0;
            } else {
                while (ends[j] < points[i]) {
                    end = j + 1;
                    if (j == ends.length-1) {
                        break;
                    }
                    j++;
                }
            }
            cnt[i] = starts.length - start - end;
        }

        return cnt;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        int[] starts = new int[n];
        int[] ends = new int[n];
        int[] points = new int[m];
        for (int i = 0; i < n; i++) {
            starts[i] = scanner.nextInt();
            ends[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }
        int[] cnt = fastCountSegments(starts, ends, points);
        for (int x : cnt) {
            System.out.print(x + " ");
        }
    }
}

