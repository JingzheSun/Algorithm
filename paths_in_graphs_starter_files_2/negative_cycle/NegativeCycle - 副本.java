import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class NegativeCycle {
    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int[] distance, int[] xs, int[] ys) {

        LinkedBlockingDeque queue = new LinkedBlockingDeque();
        distance[s] = 0;

        for (int j = 0; j < distance.length; j++) {
            boolean change = false;
            queue.add(s);
            while (!queue.isEmpty()) {
                int processing = (int) queue.poll();
                for (int i = 0; i < adj[processing].size(); i++) {
                    int next = (int) adj[processing].get(i);
                    int weight = (int) cost[processing].get(i);
                    if (distance[next] == Integer.MAX_VALUE) {
                        distance[next] = distance[processing] + weight;
                        queue.add(next);
                        change = true;
                    } else if (distance[next] > distance[processing] + weight) {
                        distance[next] = distance[processing] + weight;
                        change = true;
                    }
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
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
            xs[i] = x;
            ys[i] = y;
        }
        System.out.println(negativeCycle(adj, cost, 0, distance, xs, ys));
    }
}

