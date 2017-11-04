import java.io.*;
import java.util.*;

public class Acyclicity {
     private static ArrayList visited = new ArrayList();

    private static int acyclic(ArrayList<Integer>[] adj) {

        for (int j = 0; j < adj.length; j++) {
            if (visited.contains(j)) {
                continue;
            }
            ArrayList meet = new ArrayList();
            visited.add(j);
            meet.add(j);
            if (explore(adj, j, meet) == 1) {
                return 1;
            }
        }
        return 0;
    }

    private static int explore(ArrayList<Integer>[] adj, int j, ArrayList meet) {
        for (int i = 0; i < adj[j].size(); i++) {
            int friend = adj[j].get(i);
            if (!meet.contains(friend)) {
                meet.add(friend);
                visited.add(friend);
                if (explore(adj, friend, meet) == 1) {
                    return 1;
                }
            } else {
                return 1;
            }
        }
		meet.remove((Integer)j);
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
        }
        System.out.println(acyclic(adj));
    }
}

