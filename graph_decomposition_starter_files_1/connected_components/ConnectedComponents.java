import java.util.*;
import java.util.*;

public class ConnectedComponents {
	
    private static ArrayList old = new ArrayList();
    private static int numberOfComponents(ArrayList<Integer>[] adj) {
        int result = 0;
        for (int j = 0; j < adj.length; j++) {
            if (old.contains(j)) {
                continue;
            }
            old.add(j);
            addFriend(adj, j);
            result++;
        }
        return result;
    }

    private static void addFriend(ArrayList<Integer>[] adj, int j) {
        for (int i = 0; i < adj[j].size(); i++) {
            int friend = adj[j].get(i);
            if (!old.contains(friend)) {
                old.add(friend);
                addFriend(adj, friend);
            }
        }
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
        System.out.println(numberOfComponents(adj));
    }
}

