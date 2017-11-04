import java.util.*;

public class DotProduct1 {
    private static double minD(Dot[] dot, int left, int right) {

        if (right == left + 2) {
            double a = Math.sqrt(Math.pow(dot[left].X - dot[right].X, 2) + Math.pow(dot[left].Y - dot[right].Y, 2));
            double b = Math.sqrt(Math.pow(dot[left + 1].X - dot[right].X, 2) + Math.pow(dot[left + 1].Y - dot[right].Y, 2));
            double c = Math.sqrt(Math.pow(dot[left].X - dot[right - 1].X, 2) + Math.pow(dot[left].Y - dot[right - 1].Y, 2));
            return Math.min(a, Math.min(b, c));

        } else if (right == left + 1) {
            return Math.sqrt(Math.pow(dot[left].X - dot[right].X, 2) + Math.pow(dot[left].Y - dot[right].Y, 2));
        }

        int ave = (left + right) / 2;
        double d1 = minD(dot, left, ave);
        double d2 = minD(dot, ave + 1, right);
        double d = Math.min(d2, d1);
        double d3 = minDacross(dot, Math.max((int) Math.ceil(ave - d), left), ave, Math.min((int) Math.floor(ave + d), right), d);

        return Math.min(d3, Math.min(d1, d2));
    }

    private static double minDacross(Dot[] dot, int left, int mid, int right, double d) {

        double result = Math.sqrt(Math.pow(dot[left].X - dot[right].X, 2) + Math.pow(dot[left].Y - dot[right].Y, 2));
        Doty[] doty = new Doty[right - left];
        for (int m = 0; m < right - left; m++) {
            doty[m] = new Doty(dot[m + left].X, dot[m + left].Y);
        }
        Arrays.sort(doty);
        for (int i = 0; i < right - left-1; i++) {

            for (int j = i + 1; j < right - left; j++) {
                if (doty[j].Y - doty[i].Y < d) {//&& Math.abs(doty[i].X - doty[j].X) < d
                    double dist = Math.sqrt(Math.pow(doty[i].X - doty[j].X, 2) + Math.pow(doty[i].Y - doty[j].Y, 2));
                    if (dist < result) {
                        result = dist;
                    }
                } else {
                    break;
                }
            }
        }
        return result;
    }

    private static class Dot implements Comparable<Dot> {

        int X;
        int Y;

        Dot(int x, int y) {
            this.X = x;
            this.Y = y;
        }

        public int getX() {
            return X;
        }

        public int getY() {
            return Y;
        }

        @Override
        public int compareTo(Dot o) {
            return this.getX() - o.getX();
        }
    }

    private static class Doty implements Comparable<Doty> {

        int X;
        int Y;

        Doty(int x, int y) {
            this.X = x;
            this.Y = y;
        }

        public int getX() {
            return X;
        }

        public int getY() {
            return Y;
        }

        @Override
        public int compareTo(Doty o) {
            return this.getY() - o.getY();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        Dot[] dot = new Dot[n];
        for (int i = 0; i < n; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            dot[i] = new Dot(x, y);
        }
        Arrays.sort(dot);
        System.out.println(minD(dot, 0, dot.length - 1));
    }
}

