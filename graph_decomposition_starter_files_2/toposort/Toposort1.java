import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Toposort {
    private static ArrayList inversed = new ArrayList();
    private static ArrayList startPoint = new ArrayList();

    private static void toposort(ArrayList<Integer>[] adj) {
        boolean[] visited = new boolean[adj.length];
        for (int j = 0; j < startPoint.size(); j++) {
            visited[(int) startPoint.get(j) - 1] = true;
            explore(adj, (int) startPoint.get(j) - 1, visited);
        }
    }

    private static void explore(ArrayList<Integer>[] adj, int j, boolean[] visited) {
        for (int i = 0; i < adj[j].size(); i++) {
            int friend = (int) adj[j].get(i);
            if (!visited[friend]) {
                visited[friend] = true;
                explore(adj, friend, visited);
            }
        }
        inversed.add(j);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            startPoint.add(i + 1);
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            startPoint.remove((Integer) y);
            adj[x - 1].add(y - 1);
        }
        toposort(adj);
        ArrayList<Integer> order = inversed;
        Collections.reverse(order);
        for (int x : order) {
            System.out.print((x + 1) + " ");
        }
    }
}

