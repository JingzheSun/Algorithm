import java.util.*;

public class LCS32 {

    public static int lcs3(int[] a, int[] b, int[] c) {
        int[][][] dist = new int[a.length + 1][b.length + 1][c.length + 1];
//        for (int i = 0; i < a.length + 1; i++) {
//            dist[i][0][0] = i;
//        }
//        for (int j = 0; j < b.length + 1; j++) {
//            dist[0][j][0] = j;
//        }
//        for (int k = 0; k < c.length + 1; k++) {
//            dist[0][0][k] = k;
//        }
//        for (int i = 1; i < a.length + 1; i++) {
//            for (int j = 1; j < b.length + 1; j++) {
//                int d = Math.min(dist[i - 1][j][0] + 1, dist[i][j - 1][0] + 1);
//                if (a[i - 1] == b[j - 1]) {
//                    dist[i][j][0] = Math.min(dist[i - 1][j - 1][0], d);
//                } else {
//                    dist[i][j][0] = Math.min(dist[i - 1][j - 1][0] + 1, d);
//                }
//            }
//        }
//        for (int i = 1; i < a.length + 1; i++) {
//            for (int k = 1; k < c.length + 1; k++) {
//                int d = Math.min(dist[i - 1][0][k] + 1, dist[i][0][k - 1] + 1);
//                if (a[i - 1] == c[k - 1]) {
//                    dist[i][0][k] = Math.min(dist[i - 1][0][k], d);
//                } else {
//                    dist[i][0][k] = Math.min(dist[i - 1][0][k] + 1, d);
//                }
//            }
//        }
//        for (int k = 1; k < c.length + 1; k++) {
//            for (int j = 1; j < b.length + 1; j++) {
//                int d = Math.min(dist[0][j][k - 1] + 1, dist[0][j - 1][k] + 1);
//                if (c[k - 1] == b[j - 1]) {
//                    dist[0][j][k] = Math.min(dist[0][j - 1][k - 1], d);
//                } else {
//                    dist[0][j][k] = Math.min(dist[0][j - 1][k - 1] + 1, d);
//                }
//            }
//        }
        for (int i = 1; i < a.length + 1; i++) {
            for (int j = 1; j < b.length + 1; j++) {
                for (int k = 1; k < c.length + 1; k++) {
                    int d = Math.max(dist[i - 1][j][k] , dist[i][j - 1][k] );
                    int d1 = Math.max(dist[i][j][k - 1], d);
                    if (a[i - 1] == b[j - 1] && a[i - 1] == c[k - 1] && b[j - 1] == c[k - 1]) {
                        dist[i][j][k] = Math.max(dist[i - 1][j - 1][k - 1]+1, d1);
                    } 
//                    else if (a[i - 1] == b[j - 1]) {
//                        dist[i][j][k] = Math.min(dist[i - 1][j - 1][k] + 1, d1);
//                    } else if (a[i - 1] == c[k - 1]) {
//                        dist[i][j][k] = Math.min(dist[i - 1][j][k - 1] + 1, d1);
//                    } else if (b[j - 1] == c[k - 1]) {
//                        dist[i][j][k] = Math.min(dist[i][j - 1][k - 1] + 1, d1);
//                    } 
                    else {
                        dist[i][j][k] = Math.max(dist[i - 1][j - 1][k - 1], d1);
                    }
                }
            }
        }
//        System.out.println(dist[2][1][2]);
//        System.out.println(dist[3][0][2]);
//        System.out.println(dist[3][1][1]);
        return  dist[a.length][b.length][c.length];
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
        System.out.println(lcs3(a, b, c));
    }
}

