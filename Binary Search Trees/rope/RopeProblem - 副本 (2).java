import java.io.*;
import java.util.*;

class RopeProblem {

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

    class Rope {

        String s;

        void process(int i, int j, int k) {
            
            Vertex left = null;
            Vertex right = null;
            Vertex mid = null;
            Vertex newSmall = null;
            VertexPair leftMid = split(root, i + 1);
            left = leftMid.left;
            right = leftMid.right;
            VertexPair midRight = split(right, j + 2);
            mid = midRight.left;
            root = merge(left, midRight.right);
            
            
//            String t = s.substring(0, i) + s.substring(j + 1);
//            s = t.substring(0, k) + s.substring(i, j + 1) + t.substring(k);
//            
//            
            if (k > i) {
                k = k + 1 + j - i;
            }
            VertexPair newPosition = split(root, k + 1);
            root = merge(merge(newPosition.left, mid), newPosition.right);
            newSmall = findSmall(root);
            Vertex p = newSmall;
            int n = 0;
            while (n < s.length()) {
                update(p);
                p = findNext(p);
                n++;
            }
        }
        
        String result() {
            return s;
        }

        Rope(String s) {
            this.s = s;
            for (int i = 0; i < s.length(); i++) {
                insert(i, s.charAt(i));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new RopeProblem().run();
    }

    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        Rope rope = new Rope(in.next());
        for (int q = in.nextInt(); q > 0; q--) {
            int i = in.nextInt();
            int j = in.nextInt();
            int k = in.nextInt();
            rope.process(i, j, k);
        }
  //      out.println(rope.result());
        out.println(root.str);
        out.close();
    }

    class Vertex {

        char c;
        int sum;
        String str;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(char c, int sum, String str, Vertex left, Vertex right, Vertex parent) {
            this.c = c;
            this.sum = sum;
            this.str = str;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    void updateString(Vertex v) {
        if (v == null) {
            return;
        }
        v.str = String.valueOf(v.c);
        if (v.left != null) {
            v.str = v.left.str + v.str;
            v.left.parent = v;
        }
        if (v.right != null) {
            v.str = v.str + v.right.str;
            v.right.parent = v;
        }
    }

    Vertex findSmall(Vertex v) {
        if (v == null) {
            return null;
        }
        if (v.left != null) {
            Vertex p = v;
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }
        return v;
    }

    Vertex findNext(Vertex v) {
        if (v == null) {
            return null;
        }
        if (v.right != null) {
            Vertex p = v.right;
            while (p.left != null) {
                p = p.left;
            }
            return p;
        } else if (v.parent != null) {
            Vertex p = v;
            while (p.parent != null && p.parent.right == p) {
                p = p.parent;
            }
            if (p.parent != null) {
                return p.parent;
            }
        }
        return null;
    }

    void update(Vertex v) {
        if (v == null) {
            return;
        }
        v.sum = 1;
        if (v.left != null) {
            Vertex p = v.left;
            while (p.right != null) {
                p = p.right;
            }
            v.sum = p.sum + 1;
        } else if (v.parent != null) {
            Vertex p = v;
            while (p.parent != null && p.parent.left == p) {
                p = p.parent;
            }
            if (p.parent != null) {
                v.sum = p.parent.sum + 1;
            }
        }
        v.str = String.valueOf(v.c);
        if (v.left != null) {
            v.str = v.left.str + v.str;
            v.left.parent = v;
        }
        if (v.right != null) {
            v.str = v.str + v.right.str;
            v.right.parent = v;
        }
    }

    void smallRotation(Vertex v) {
        Vertex parent = v.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = v.parent.parent;
        if (parent.left == v) {
            Vertex m = v.right;
            v.right = parent;
            parent.left = m;
            parent.parent = v;
            if (m != null) {
                m.parent = parent;
            }
        } else {
            Vertex m = v.left;
            v.left = parent;
            parent.parent = v;
            parent.right = m;
            if (m != null) {
                m.parent = parent;
            }
        }
        v.parent = grandparent;
        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = v;
            } else {
                grandparent.right = v;
            }
        }
        updateString(parent);
        updateString(v);
    }

    void bigRotation(Vertex v) {
        if (v.parent.left == v && v.parent.parent.left == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }

    // Makes splay of the given vertex and returns the new root.
    Vertex splay(Vertex v) {
        if (v == null) {
            return null;
        }
        while (v.parent != null) {
            if (v.parent.parent == null) {
                smallRotation(v);
                break;
            }
            bigRotation(v);
        }
        return v;
    }

    class VertexPair {

        Vertex left;
        Vertex right;

        VertexPair() {
        }

        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }
    }

    // Searches for the given key in the tree with the given root
    // and calls splay for the deepest visited node after that.
    // Returns pair of the result and the new root.
    // If found, result is a pointer to the node with the given key.
    // Otherwise, result is a pointer to the node with the smallest
    // bigger key (next value in the order).
    // If the key is bigger than all keys in the tree,
    // then result is null.
    VertexPair find(Vertex root, int order) {
        Vertex v = root;
        Vertex last = root;
        Vertex next = null;
        while (v != null) {
            if (v.sum >= order && (next == null || v.sum < next.sum)) {
                next = v;
            }
            last = v;
            if (v.sum == order) {
                break;
            }
            if (v.sum < order) {
                v = v.right;
            } else {
                v = v.left;
            }
        }
        root = splay(last);
        return new VertexPair(next, root);
    }

    VertexPair split(Vertex root, int order) {
        VertexPair result = new VertexPair();
        VertexPair findAndRoot = find(root, order);
        root = findAndRoot.right;
        result.right = findAndRoot.left;
        if (result.right == null) {
            result.left = root;
            return result;
        }
        result.right = splay(result.right);
        result.left = result.right.left;
        result.right.left = null;
        if (result.left != null) {
            result.left.parent = null;
        }
        updateString(result.left);
        updateString(result.right);
        return result;
    }

    Vertex merge(Vertex left, Vertex right) {
        if (left == null) {
            updateString(right);
            return right;
        }
        if (right == null) {
            updateString(left);
            return left;
        }
        while (right.left != null) {
            right = right.left;
        }
        right = splay(right);
        right.left = left;
        updateString(right);
        return right;
    }

    // Code that uses splay tree to solve the problem
    Vertex root = null;

    void insert(int order, char c) {
        Vertex left = null;
        Vertex right = null;
        Vertex new_vertex = null;
        VertexPair leftRight = split(root, order);
        left = leftRight.left;
        right = leftRight.right;

        new_vertex = new Vertex(c, 1, null, null, null, null);
        root = merge(merge(left, right), new_vertex);
        update(root);
    }
}
