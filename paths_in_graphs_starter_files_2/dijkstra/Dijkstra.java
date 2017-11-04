import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class Dijkstra {
    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t, int[] distance) {

        PriorityBlockingQueue<Vertex> queue = new PriorityBlockingQueue<Vertex>();
        distance[s] = 0;
        queue.add(new Vertex(s, distance[s]));

        while (!queue.isEmpty()) {
            int processing = (int) queue.poll().index;
            for (int i = 0; i < adj[processing].size(); i++) {
                int next = (int) adj[processing].get(i);
                int weight = (int) cost[processing].get(i);
                if (distance[next] == -1) {
                    distance[next] = distance[processing] + weight;
                    queue.add(new Vertex(next, distance[next]));
                } else if (distance[next] > distance[processing] + weight) {
                    distance[next] = distance[processing] + weight;
                    queue.add(new Vertex(next, distance[next]));
                }
            }
        }
        return distance[t];
    }

    private static class Vertex implements Comparable<Vertex> {

        int index;
        int dist;

        Vertex(int index, int dist) {
            this.index = index;
            this.dist = dist;
        }

        public int getIndex() {
            return index;
        }

        public int getDist() {
            return dist;
        }

        @Override
        public int compareTo(Vertex o) {
            return this.getDist() - o.getDist();
        }
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
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y, distance));
    }
}

