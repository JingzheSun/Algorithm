import java.util.*;

public class FibonacciSumLastDigit {
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
        long n = scanner.nextLong();
        long s = getFibonacciSumNaive(n);
		long s1 = getFibonacciSumNaive(n+1);
        System.out.println((s1+s+9)%10);
    }
}

