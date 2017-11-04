import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class NegativeCycle {
    private static int negativeCycle(int s, int[] distance, int[] xs, int[] ys, int[] ws) {

        distance[s] = 0;
        for (int j = 0; j < distance.length; j++) {
            boolean change = false;
            for (int i = 0; i < xs.length; i++) {
                int processing = xs[i];
                int next = ys[i];
                int weight = ws[i];
                if (distance[next] > distance[processing] + weight) {
                    distance[next] = distance[processing] + weight;
                    change = true;
                }
            }

            if (!change) {
                return 0;
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] distance = new int[n];
        int[] xs = new int[m];
        int[] ys = new int[m];
        int[] ws = new int[m];
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE / 2;
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            xs[i] = x - 1;
            ys[i] = y - 1;
            ws[i] = w;
        }
        System.out.println(negativeCycle(0, distance, xs, ys, ws));
    }
}

