import java.util.*;
import java.io.*;

public class tree_orders {

    class FastScanner {

        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements()) {
                tok = new StringTokenizer(in.readLine());
            }
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public class TreeOrders {

        int n;
        int[] key, left, right;
        List<Integer> inOrder = new ArrayList<Integer>();
        List<Integer> preOrder = new ArrayList<Integer>();
        List<Integer> postOrder = new ArrayList<Integer>();

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            key = new int[n];
            left = new int[n];
            right = new int[n];
            for (int i = 0; i < n; i++) {
                key[i] = in.nextInt();
                left[i] = in.nextInt();
                right[i] = in.nextInt();
            }
        }

        void traveral(int index) {
            if (index == -1) {
                return;
            }
            preOrder.add(key[index]);
            this.traveral(left[index]);
            inOrder.add(key[index]);
            this.traveral(right[index]);
            postOrder.add(key[index]);
        }

    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_orders().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void print(List<Integer> x) {
        for (Integer a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        TreeOrders tree = new TreeOrders();
        tree.read();
        tree.traveral(0);
        print(tree.inOrder);
        print(tree.preOrder);
        print(tree.postOrder);
    }
}
