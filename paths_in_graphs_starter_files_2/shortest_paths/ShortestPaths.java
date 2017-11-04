import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ShortestPaths {

    private static void shortestPaths(ArrayList<Integer>[] adj, int[] xs, int[] ys, int[] ws, int s, long[] distance, int[] reachable, int[] shortest) {

        LinkedBlockingDeque queue = new LinkedBlockingDeque();
        boolean[] reach = new boolean[distance.length];
        boolean[] reaches = new boolean[distance.length];
        distance[s] = 0;
        reachable[s] = 1;
        shortest[s] = 1;
        reach[s] = true;
        queue.add(s);

        while (!queue.isEmpty()) {
            int processing = (int) queue.poll();
            for (int i = 0; i < adj[processing].size(); i++) {
                int next = (int) adj[processing].get(i);
                reach[next] = true;
                if (!reaches[next]) {
                    queue.add(next);
                    reaches[next] = true;
                }
            }
        }

        for (int j = 0; j < distance.length; j++) {
            for (int i = 0; i < xs.length; i++) {
                int processing = xs[i];
                int next = ys[i];
                long weight = ws[i];
                if (distance[next] > distance[processing] + weight) {
                    distance[next] = distance[processing] + weight;
                    if (distance[next] < Long.MAX_VALUE / 2) {
                        reachable[next] = 1;
                    }
                    if (j == distance.length - 1 && reach[next]) {
                        queue.add(next);
                    }
                }
            }
        }
        reaches = new boolean[distance.length];
        while (!queue.isEmpty()) {
            int processing = (int) queue.poll();
            for (int i = 0; i < adj[processing].size(); i++) {
                int next = (int) adj[processing].get(i);
                shortest[next] = 0;
                if (!reaches[next]) {
                    queue.add(next);
                    reaches[next] = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] xs = new int[m];
        int[] ys = new int[m];
        int[] ws = new int[m];
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            xs[i] = x - 1;
            ys[i] = y - 1;
            ws[i] = w;
            adj[x - 1].add(y - 1);
        }
        int s = scanner.nextInt() - 1;
        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE - Integer.MAX_VALUE - 1;
            reachable[i] = 0;
            shortest[i] = 1;
        }
        shortestPaths(adj, xs, ys, ws, s, distance, reachable, shortest);
        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }
}

