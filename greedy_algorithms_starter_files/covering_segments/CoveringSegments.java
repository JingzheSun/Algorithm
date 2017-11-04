import java.util.*;

public class CoveringSegments {

    private static ArrayList optimalPoints(Segment[] segments) {

        ArrayList points = new ArrayList();
        Arrays.sort(segments);
        ArrayList index = new ArrayList();
        for (int j = 0; j < segments.length; j++) {
            if (index.contains(j)) {
                continue;
            }
            points.add(segments[j].getEnd());
            for (int i = 0; i < segments.length; i++) {
                if (segments[i].getStart() <= segments[j].getEnd()) {
                    index.add(i);
                }
            }
        }
        return points;
    }

    private static class Segment implements Comparable<Segment> {

        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        @Override
        public int compareTo(Segment o) {
            return this.getEnd() - o.getEnd();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }
        ArrayList points = optimalPoints(segments);
        System.out.println(points.size());
        for (int i = 0; i < points.size(); i++) {
            System.out.print(points.get(i) + " ");
        }
    }
}
 
