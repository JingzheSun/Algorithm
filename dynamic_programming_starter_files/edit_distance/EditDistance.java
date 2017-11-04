import java.util.*;

class EditDistance {
  public static int EditDistance(String s, String t) {
        int[][] dist = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i < s.length() + 1; i++) {
            dist[i][0] = i;
        }
        for (int j = 0; j < t.length() + 1; j++) {
            dist[0][j] = j;
        }
        for (int i = 1; i < s.length() + 1; i++) {
            for (int j = 1; j < t.length() + 1; j++) {

                int d = Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1);
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dist[i][j] = Math.min(dist[i - 1][j - 1], d);
                } else {
                    dist[i][j] = Math.min(dist[i - 1][j - 1] + 1, d);
                }
            }
        }
        return dist[s.length()][t.length()];
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        String s = scan.next();
        String t = scan.next();

        System.out.println(EditDistance(s, t));
    }

}
