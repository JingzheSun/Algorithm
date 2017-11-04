import java.util.*;

public class FibonacciPartialSum {
    private static long getFibonacciSumNaive(long n) {
        
        long[][] e = {{0, 1}, {1, 1}};
        long[][] a = {{1, 0}, {0, 1}};
		long m =10;
        long oo, ot, to, tt;
        if (n <= 1) {
            return n;
        }
        while (n>0) {
            if (n % 2 == 1) {
                oo = e[0][0] * a[0][0] + e[0][1] * a[1][0];
                ot = e[0][0] * a[0][1] + e[0][1] * a[1][1];
                to = e[1][0] * a[0][0] + e[1][1] * a[1][0];
                tt = e[1][0] * a[0][1] + e[1][1] * a[1][1];
                a[0][0] = oo%m;
                a[0][1] = ot%m;
                a[1][0] = to%m;
                a[1][1] = tt%m;
            }
            n = n >>1;
            oo = e[0][0] * e[0][0] + e[0][1] * e[1][0];
            ot = e[0][0] * e[0][1] + e[0][1] * e[1][1];
            to = e[1][0] * e[0][0] + e[1][1] * e[1][0];
            tt = e[1][0] * e[0][1] + e[1][1] * e[1][1];
            e[0][0] = oo%m;
            e[0][1] = ot%m;
            e[1][0] = to%m;
            e[1][1] = tt%m;
        }
        return a[0][1]%m;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        long s = getFibonacciSumNaive(from);
		long s1 = getFibonacciSumNaive(from-1);
		long m = getFibonacciSumNaive(to);
		long m1 = getFibonacciSumNaive(to+1);
        System.out.println((m+m1-s-s1+20)%10);
    }
}

