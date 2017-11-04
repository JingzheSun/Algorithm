import java.util.Scanner;

public class ConnectingPoints {
    private static double minimumDistance(int[] x, int[] y, double[] cost, int s) {
        PriorityBlockingQueue<Vertex> queue = new PriorityBlockingQueue<Vertex>();
        boolean[] visited = new boolean[cost.length];
        Vertex [] visit  = new Vertex[cost.length];
        cost[s] = 0;
        visited[s] = true;
        Vertex temp =new Vertex(s, cost[s]);
        visit[s]=temp;
        queue.add(temp);
        while (!queue.isEmpty()) {
            int processing = (int) queue.poll().index;
            double min = Double.POSITIVE_INFINITY;
            int index = 0;
            for (int i = 0; i < x.length; i++) {
                if (visited[i]||processing == i) {
                    continue;
                }
                int next = i;
                double weight = dist(x[processing], y[processing], x[next], y[next]);
                if (!visited[next] && cost[next] > weight) {
                    cost[next] = weight;
                    queue.add(new Vertex(next, cost[next]));
                }
                if (min > weight) {
                    min = weight;
                    index = next;
                }
            }
            visited[index] = true;
        }

        double sum = 0;
        for (int i = 0; i < cost.length; i++) {
            sum += cost[i];
        }
        return sum;
    }

    private static double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private static class Vertex implements Comparable<Vertex> {

        int index;
        double dist;

        Vertex(int index, double dist) {
            this.index = index;
            this.dist = dist;
        }

        public int getIndex() {
            return index;
        }

        public double getDist() {
            return dist;
        }

        @Override
        public int compareTo(Vertex o) {
            return (int) this.getDist() - (int) o.getDist();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        double[] cost = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
            cost[i] = Double.POSITIVE_INFINITY;
        }
        double temp = minimumDistance(x, y, cost, 0);
        System.out.printf("%.7f\n", temp);

        for (int i = 0; i < cost.length; i++) {
            System.out.println(cost[i]);
        }
    }
}

