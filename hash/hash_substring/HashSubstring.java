import java.io.*;
import java.util.*;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
//    private static int bucketCount = 1000;
    private static int prime = 1003;
    private static int multiplier = 263;
    //  private static ArrayList<String>[] elems = new ArrayList[bucketCount];

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
//        for (int j = 0; j < elems.length; j++) {
//            elems[j] = new ArrayList();
//        }
        List<Integer> a = getOccurrences(readInput());
        printOccurrences(a);
        out.close();
    }

    private static int hashFunc(String s) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash + (long) s.charAt(i)) % prime;
        }
        return (int) (hash);
    }

    private static long power(int j) {
        double a = 1;
        for (int i = 0; i < j; i++) {
            a = (a * multiplier) % prime;
        }
        return (long) a;
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrences(Data input) {
		String s = input.pattern, t = input.text;
        List<Integer> occurrences = new ArrayList<Integer>();
        int n = s.length(), m = t.length();
        int phash = hashFunc(input.pattern);
        int[] hashes = new int[m - n + 1];
        hashes[0] = hashFunc(t.substring(0, 0 + n));
        if (s.equals(t.substring(0, n))) {
            occurrences.add(0);
        }
        for (int j = 1; j < m - n + 1; j++) {
            hashes[j] = (hashes[j - 1] - (int) t.charAt(j - 1) + (int) t.charAt(j + n - 1) + prime) % prime;
//           hashes[j] = hashFunc(input.text.substring(j, j + n));

            if (hashes[j] != phash) {
                continue;
            }
            if (s.regionMatches(0,t,j,n)) {
				//s.equals(t.substring(j, j + n))
                occurrences.add(j);
            }

        }
        return occurrences;
    }

    static class Data {

        String pattern;
        String text;

        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
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
