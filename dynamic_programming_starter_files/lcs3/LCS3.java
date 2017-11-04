import java.util.*;

public class LCS3 {

    public static int lcs3(int[] a, int[] b, int[] c) {
        int[][][] dist = new int[a.length + 1][b.length + 1][c.length + 1];
        for (int i = 0; i < a.length + 1; i++) {
            dist[i][0][0] = i;
        }
        for (int j = 0; j < b.length + 1; j++) {
            dist[0][j][0] = j;
        }
        for (int k = 0; k < c.length + 1; k++) {
            dist[0][0][k] = k;
        }
        for (int i = 1; i < a.length + 1; i++) {
            for (int j = 1; j < b.length + 1; j++) {
                int d = Math.min(dist[i - 1][j][0] + 1, dist[i][j - 1][0] + 1);
                if (a[i - 1] == b[j - 1]) {
                    dist[i][j][0] = Math.min(dist[i - 1][j - 1][0], d);
                } else {
                    dist[i][j][0] = Math.min(dist[i - 1][j - 1][0] + 1, d);
                }
            }
        }
        for (int i = 1; i < a.length + 1; i++) {
            for (int k = 1; k < c.length + 1; k++) {
                int d = Math.min(dist[i - 1][0][k] + 1, dist[i][0][k - 1] + 1);
                if (a[i - 1] == c[k - 1]) {
                    dist[i][0][k] = Math.min(dist[i - 1][0][k], d);
                } else {
                    dist[i][0][k] = Math.min(dist[i - 1][0][k] + 1, d);
                }
            }
        }
        for (int k = 1; k < c.length + 1; k++) {
            for (int j = 1; j < b.length + 1; j++) {
                int d = Math.min(dist[0][j][k - 1] + 1, dist[0][j - 1][k] + 1);
                if (c[k - 1] == b[j - 1]) {
                    dist[0][j][k] = Math.min(dist[0][j - 1][k - 1], d);
                } else {
                    dist[0][j][k] = Math.min(dist[0][j - 1][k - 1] + 1, d);
                }
            }
        }
        for (int i = 1; i < a.length + 1; i++) {
            for (int j = 1; j < b.length + 1; j++) {
                for (int k = 1; k < c.length + 1; k++) {
                    int d = Math.min(dist[i - 1][j][k] + 1, dist[i][j - 1][k] + 1);
                    int d1 = Math.min(dist[i][j][k - 1] + 1, d);
                    if (a[i - 1] == b[j - 1] && a[i - 1] == c[k - 1] && b[j - 1] == c[k - 1]) {
                        dist[i][j][k] = Math.min(dist[i - 1][j - 1][k - 1], d1);
                    } else if (a[i - 1] == b[j - 1]) {
                        dist[i][j][k] = Math.min(dist[i - 1][j - 1][k] + 1, d1);
                    } else if (a[i - 1] == c[k - 1]) {
                        dist[i][j][k] = Math.min(dist[i - 1][j][k - 1] + 1, d1);
                    } else if (b[j - 1] == c[k - 1]) {
                        dist[i][j][k] = Math.min(dist[i][j - 1][k - 1] + 1, d1);
                    } else {
                        dist[i][j][k] = Math.min(dist[i - 1][j - 1][k - 1] + 1, d1);
                    }
                }
            }
        }
        int i = a.length, j = b.length, k = c.length, total = 0;
        while (i != 0 || j != 0 || k != 0) {
            if (i == 0 && j == 0) {
                total++;
                k--;
            } else if (i == 0 && k == 0) {
                total++;
                j--;
            } else if (k == 0 && j == 0) {
                total++;
                i--;
            } else if (i == 0) {
                total++;
                if (b[j - 1] == c[k - 1]) {
                    j--;
                    k--;
                } else if (dist[0][j][k] - dist[0][j - 1][k - 1] == 1) {
                    j--;
                    k--;
                } else if (dist[0][j][k] - dist[0][j][k - 1] == 1) {
                    k--;
                } else if (dist[0][j][k] - dist[0][j - 1][k] == 1) {
                    j--;
                }
            } else if (j == 0) {
                total++;
                if (a[i - 1] == c[k - 1]) {
                    i--;
                    k--;
                } else if (dist[i][0][k] - dist[i - 1][0][k - 1] == 1) {
                    i--;
                    k--;
                } else if (dist[i][0][k] - dist[i - 1][0][k] == 1) {
                    i--;
                } else if (dist[i][0][k] - dist[i][0][k - 1] == 1) {
                    k--;
                }
            } else if (k == 0) {
                total++;
                if (b[j - 1] == a[i - 1]) {
                    j--;
                    i--;
                } else if (dist[i][j][0] - dist[i - 1][j - 1][0] == 1) {
                    j--;
                    i--;
                } else if (dist[i][j][0] - dist[i - 1][j][0] == 1) {
                    i--;
                } else if (dist[i][j][0] - dist[i][j - 1][0] == 1) {
                    j--;
                }
            } else if (a[i - 1] == b[j - 1] && a[i - 1] == c[k - 1]) {
                total++;
                i--;
                j--;
                k--;
            } else if (a[i - 1] == b[j - 1]) {
                total++;
                k--;
            } else if (b[j - 1] == c[k - 1]) {
                total++;
                i--;
            } else if (a[i - 1] == c[k - 1]) {
                total++;
                j--;
            } else {
                total++;
                i--;
                j--;
                k--;
            }
        }
//        System.out.println(dist[2][1][2]);
//        System.out.println(dist[3][0][2]);
//        System.out.println(dist[3][1][1]);
//       System.out.println(total);
        return total - dist[a.length][b.length][c.length];
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

