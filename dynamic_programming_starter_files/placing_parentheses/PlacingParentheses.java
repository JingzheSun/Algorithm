import java.util.Scanner;

public class PlacingParentheses {
    private static long getMaximValue(String exp) {
        int n = exp.length() / 2 + 1;
        
        long[] num = new long[n];
        char[] ops = new char[exp.length() / 2];
        long[][] m = new long[n][n];
        long[][] M = new long[n][n];

        for (int i = 0; i < exp.length(); i++) {
            if (i % 2 == 0) {
                num[i / 2] = (long) exp.charAt(i) - 48;
            } else {
                ops[i / 2] = exp.charAt(i);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j - i == 0) {
                    M[i][j] = num[i];
                    m[i][j] = num[i];
                }
            }
        }
        for (int s = 1; s < n; s++) {
            for (int i = 0; i < n - s; i++) {
                int j = i + s;
                long min = Long.MAX_VALUE;
                long max = Long.MIN_VALUE;
                for (int k = i; k < j; k++) {
                    long a = eval(M[i][k], M[k + 1][j], ops[k]);
                    long b = eval(M[i][k], m[k + 1][j], ops[k]);
                    long c = eval(m[i][k], M[k + 1][j], ops[k]);
                    long d = eval(m[i][k], m[k + 1][j], ops[k]);
                    min = Math.min(Math.min(a, b), Math.min(min, Math.min(c, d)));
                    max = Math.max(Math.max(a, b), Math.max(Math.max(c, d), max));
                }
                M[i][j] = max;
                m[i][j] = min;
            }
        }

        return M[0][n-1];
    }

    private static long eval(long a, long b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
    }
}

