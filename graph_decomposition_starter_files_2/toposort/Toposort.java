import java.util.*;
import java.io.*;

public class Toposort {
    private static ArrayList inversed = new ArrayList();
    private static ArrayList startPoint = new ArrayList();

    private static void toposort(ArrayList<Integer>[] adj, boolean[] starts) {
        boolean[] visited = new boolean[adj.length];
        boolean[] popped = new boolean[adj.length];
        for (int j = 0; j < adj.length; j++) {
            if (starts[j]) {
                continue;
            }
            Stack using = new Stack();
            using.push(j);
            int temp;
            while (!using.empty()) {
                temp = (int) using.peek();
                if (!visited[temp]) {
                    visited[temp] = true;
                    if (adj[temp].isEmpty()) {
                        int b = (int) using.pop();
                        inversed.add(b);
                        popped[b] = true;
                        continue;
                    }
                    int i;
                    for (i = 0; i < adj[temp].size(); i++) {
                        using.push(adj[temp].get(i));
                    }
                } else if (!popped[temp]) {
                    int a = (int) using.pop();
                    inversed.add(a);
                    popped[a] = true;
                } else {
                    using.pop();
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        boolean[] starts = new boolean[n];
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            starts[(int) y - 1] = true;
            adj[x - 1].add(y - 1);
        }
        toposort(adj, starts);
        ArrayList<Integer> order = inversed;
        Collections.reverse(order);
        for (int x : order) {
            System.out.print((x + 1) + " ");
        }
    }
}

