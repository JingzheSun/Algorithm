import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;


public class ShortestPaths {

    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, long[] distance, int[] reachable, int[] shortest) {

        PriorityBlockingQueue<Vertex> queue = new PriorityBlockingQueue<Vertex>();
        distance[s] = 0;
        reachable[s] = 1;
        shortest[s] = 1;

        for (int j = 0; j < distance.length; j++) {
            queue.add(new Vertex(s, distance[s]));
            boolean[] used = new boolean[distance.length];
            while (!queue.isEmpty()) {
                int processing = (int) queue.poll().index;
                for (int i = 0; i < adj[processing].size(); i++) {
                    int next = (int) adj[processing].get(i);
                    long weight = cost[processing].get(i);
                    reachable[next] = 1;
                    if (!used[processing]) {
                        queue.add(new Vertex(next, distance[next]));
                        if (distance[next] > distance[processing] + weight) {
                            distance[next] = distance[processing] + weight;
                            if (j != 0) {
                                shortest[next] = 0;
                            }
                        }
                    }
                }
                used[processing] = true;
            }
        }
        distance[s] = 0;
        reachable[s] = 1;
        shortest[s] = 1;
    }

    private static class Vertex implements Comparable<Vertex> {

        int index;
        long dist;

        Vertex(int index, long dist) {
            this.index = index;
            this.dist = dist;
        }

        public int getIndex() {
            return index;
        }

        public long getDist() {
            return dist;
        }

        @Override
        public int compareTo(Vertex o) {
            return (int) (this.getDist() - o.getDist()) % 1000000;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
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
        int s = scanner.nextInt() - 1;
        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            reachable[i] = 0;
            shortest[i] = 1;
        }
        shortestPaths(adj, cost, s, distance, reachable, shortest);
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

