import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class BFS {
    private static int distance(ArrayList<Integer>[] adj, int s, int t, int[] distance) {

        LinkedBlockingDeque queue = new LinkedBlockingDeque();
        distance[s] = 0;
        queue.add(s);

        while (!queue.isEmpty()) {
            int processing = (int) queue.pop();
            for (int i = 0; i < adj[processing].size(); i++) {
                int next = (int) adj[processing].get(i);
                if (distance[next] == -1) {
                    distance[next] = distance[processing] + 1;
                    queue.add(next);
                }
            }
        }
        return distance[t];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] distance = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = -1;
        }
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, x, y, distance));
    }
}

