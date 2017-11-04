import java.util.*;

public class DifferentSummands {
    private static List<Integer> optimalSummands(int n) {
        List<Integer> summands = new ArrayList<Integer>();
        int i = 0;
        while (i < 65535) {
            if (i * (i + 1) <= n * 2 && (i + 2) * (i + 1) > n * 2) {

                for (int j = 0; j < i; j++) {
                    summands.add(j+1);
                }
                summands.set(i - 1, n + i - i * (i + 1) / 2);
                break;
            }
            i++;
        }
        return summands;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
    }
}

