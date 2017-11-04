import java.util.*;

public class PointsAndSegments {

    private static ArrayList optimalPoints(Segment[] segments, int[] points) {

        ArrayList p = new ArrayList();
        Arrays.sort(segments);
        int[] pp = points;
        Arrays.sort(pp);
        int i = 0;
        int x = 0;
        int temp = 0;
        while (pp[0] + x < segments[0].getValue()) {
            p.add(temp);
            x++;
        }
        while (segments[0].getValue() == segments[i].getValue()) {
            i++;
        }
        p.add(i);
        temp = i;
        int nextemp = 0;
        for (int j = segments[0].getValue() + 1; j <= pp[pp.length - 1]; j++) {

            temp += nextemp;
            nextemp = 0;
            while ((i <= segments.length - 1) && j == segments[i].getValue()) {

                if (segments[i].getPosition().equals("Start")) {
                    temp++;
                }
                if (segments[i].getPosition().equals("End")) {
                    nextemp--;
                }
                i++;
            }
            p.add(temp);
        }
        return p;
    }

    private static class Segment implements Comparable<Segment> {

        int value;
        String position;

        Segment(int value, String position) {
            this.value = value;
            this.position = position;
        }

        public int getValue() {
            return value;
        }

        public String getPosition() {
            return position;
        }

        @Override
        public int compareTo(Segment o) {
            return this.getValue() - o.getValue();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        Segment[] segments = new Segment[n * 2];
        for (int i = 0; i < n * 2; i = i + 2) {
            int start, end;
            start = scanner.nextInt();
            segments[i] = new Segment(start, "Start");
            end = scanner.nextInt();
            segments[i + 1] = new Segment(end, "End");
        }

        int[] points = new int[m];
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }
        int[] point = Arrays.copyOf(points, points.length);
        ArrayList p = optimalPoints(segments, points);

        for (int i = 0; i < points.length; i++) {
            System.out.print(p.get(point[i] - Math.min(point[0], segments[0].getValue())) + " ");
        }
    }
}

