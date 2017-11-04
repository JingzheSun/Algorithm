import java.util.ArrayList;
import java.util.Scanner;

public class Reachability {
    
    private static ArrayList old = new ArrayList();

    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
        old.add(x);
        if (!adj[x].contains(y)) {
            int reachable = 0;
            for (int i = 0; i < adj[x].size(); i++) {
                int friend = adj[x].get(i);
                //               adj[temp].remove((Integer) x);
                if (!old.contains(friend)) {
                    reachable = reach(adj, friend, y);
                    if (reachable == 1) {
                        return 1;
                    }
                }
            }
            return 0;
        }
        return 1;
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
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        int a = reach(adj, x, y);
        System.out.println(a);
    }

}

