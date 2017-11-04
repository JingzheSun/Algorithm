import java.io.*;
import java.util.*;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;
    private ArrayList<String>[] elems;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash + power(i) * (long) s.charAt(i)) % prime;
        }
        return (int) (hash % bucketCount);
    }

    private long power(int j) {
        double a = 1;
        for (int i = 0; i < j; i++) {
            a = (a * multiplier) % prime;
        }
        return (long) a;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

    private void processQuery(Query query) {
        int hash = 0;
        switch (query.type) {
            case "add":
                hash = hashFunc(query.s);
                if (!elems[hash].contains(query.s)) {
                    elems[hash].add(0, query.s);
                }
                break;
            case "del":
                hash = hashFunc(query.s);
                if (elems[hash].contains(query.s)) {
                    elems[hash].remove(query.s);
                }
                break;
            case "find":
                hash = hashFunc(query.s);
                writeSearchResult(elems[hash].contains(query.s));
                break;
            case "check":
                for (String cur : elems[query.ind]) {
                    if (hashFunc(cur) == query.ind) {
                        out.print(cur + " ");
                    }
                }
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        int queryCount = in.nextInt();
        elems = new ArrayList[bucketCount];
        for (int j = 0; j < elems.length; j++) {
            elems[j] = new ArrayList();
        }
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {

        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class FastScanner {

        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
