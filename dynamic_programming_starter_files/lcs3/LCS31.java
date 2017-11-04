import java.util.*;

public class LCS3 {

    private static int lcs3(int[] a, int[] b, int[] c) {
        int[][][] value = new int[a.length+1][b.length+1][2];
        for(int k = 1; k <= c.length; k++){
        	for(int i = 1; i <= a.length; i++){
        		for(int j = 1; j <= b.length; j++){
        			int lld=value[i-1][j-1][(k-1)%2];
        			int llu=value[i-1][j-1][k%2];
        			int lrd=value[i-1][j][(k-1)%2];
        			int lru=value[i-1][j][k%2];
        			int rlu=value[i][j-1][k%2];
        			int rld=value[i][j-1][(k-1)%2];
        			int rrd=value[i][j][(k-1)%2];
        			int lr = Math.max(lru,lrd);
        			int rl = Math.max(rlu,rld);
        			int an = Math.max(llu,rrd);
        			int temp=Math.max(Math.max(lr, rl),an);
        			if(a[i-1]==b[j-1] && b[j-1]==c[k-1]){
        				value[i][j][k%2]=Math.max(temp, lld+1);
        			}else{
        				value[i][j][k%2]=Math.max(temp, lld);
        			}
        		}
        	}
        }
        return value[a.length][b.length][c.length%2];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int an = scanner.nextInt();
        int[] a = new int[an];
        for (int i = 0; i < an; i++) {
            a[i] = scanner.nextInt();
        }
        int bn = scanner.nextInt();
        int[] b = new int[bn];
        for (int i = 0; i < bn; i++) {
            b[i] = scanner.nextInt();
        }
        int cn = scanner.nextInt();
        int[] c = new int[cn];
        for (int i = 0; i < cn; i++) {
            c[i] = scanner.nextInt();
        }
        System.out.println(lcs3(c, b, a));
    }
}

