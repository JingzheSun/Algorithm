import java.io.*;
import java.util.*;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(in.next(),in.next()));
        out.close();
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

	private static List<Integer> getOccurrences(String pattern, String text) {
        int hash = pattern.hashCode();
        int n = pattern.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        for (int i = 0; i <= text.length() - n; i++) {
            boolean e = true;
            for (int k = 0; k < n; k = k + n/100 + 1) {
                if (pattern.charAt(k) == text.substring(i, i + n).charAt(k)) {
                } else {
                    e = false;
                    break;
                }
            }
            if (!e) {
                continue;
            }
			int thash = (text.substring(i, i + n)).hashCode();
            if (hash == thash) {
            //    if (pattern.substring(n*3/4,n).equals(text.substring(i+n*3/4, i + n))) {
                    occurrences.add(i);
            //    }
            }
        }
        return occurrences;
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
