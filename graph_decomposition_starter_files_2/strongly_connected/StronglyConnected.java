import java.io.*;
import java.util.*;

public class StronglyConnected {
    private static Stack pre = new Stack();
    private static Stack post = new Stack();
    private static int num = 0;

    private static void numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj, ArrayList<Integer>[] rev) {
        boolean[] visited = new boolean[adj.length];
        for (int j = 0; j < adj.length; j++) {
            if (visited[j]) {
                continue;
            }
            visited[j] = true;
            explore(rev, j, visited, false);
            post.push(j);
        }
        visited = new boolean[adj.length];
        while (!post.empty()) {
            if (visited[(int) post.peek()]) {
                post.pop();
                continue;
            }
            int temp = (int) post.pop();
            visited[temp] = true;
            explore(adj, temp, visited, true);
            num++;
        }
    }

    private static void explore(ArrayList<Integer>[] adj, int j, boolean[] visited, boolean r) {
        for (int i = 0; i < adj[j].size(); i++) {
            //pre
            int friend = adj[j].get(i);
            if (!visited[friend]) {
                visited[friend] = true;
                explore(adj, friend, visited, r);
                //post
                if (!r) {
                    post.push(friend);
                }
            }

        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        ArrayList<Integer>[] rev = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            rev[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            rev[y - 1].add(x - 1);
        }
        numberOfStronglyConnectedComponents(adj, rev);
        System.out.println(num);
    }
}

