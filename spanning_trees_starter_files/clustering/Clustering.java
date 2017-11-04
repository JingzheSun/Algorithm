import java.util.*;

public class Clustering {
    private static double clustering(int[] x, int[] y, ArrayList<ArrayList> list, ArrayList<Edges> edges, int k) {

        if (k == list.size()) {
            return edges.get(0).dist;
        }
        for (int i = 0; i < edges.size(); i++) {
            int u = edges.get(i).x;
            int v = edges.get(i).y;
            double weight = edges.get(i).dist;

            u = find(list, u);
            v = find(list, v);
            if (u != v) {
                if (list.size() <= k) {
                    return weight;
                }
                union(list, u, v);
            }
        }
        return 0;
    }

    private static void union(ArrayList<ArrayList> list, int u, int v) {
     //   int a = find(list,u);
       // int b = find(list,v);
        list.get(u).addAll(list.get(v));
        list.remove(v);
    }

    private static int find(ArrayList<ArrayList> list, int v) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(v)) {
                return i;
            }
        }
        return -1;
    }

    private static double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private static class Edges implements Comparable<Edges> {

        int x;
        int y;
        double dist;

        Edges(int x, int y, double dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        public double getDist() {
            return dist;
        }

        @Override
        public int compareTo(Edges o) {
            return (this.getDist() - o.getDist() > 0) ? 1 : -1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
            list.add(new ArrayList());
            list.get(i).add(i);
        }
        ArrayList<Edges> edges = new ArrayList<Edges>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                edges.add(new Edges(i, j, dist(x[i], y[i], x[j], y[j])));
            }
        }
        int k = scanner.nextInt();
        Collections.sort(edges);
        double temp = clustering(x, y, list, edges,k);
        System.out.printf("%.10f\n", temp);
    }
}

