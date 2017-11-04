import java.util.*;

public class PrimitiveCalculator {
    private static ArrayList optimal_sequence(int n) {
        ArrayList<Integer>[] sequence = new ArrayList[n];

        sequence[0] = new ArrayList();
        sequence[0].add(1);
        for (int i = 1; i < n; i++) {
            sequence[i] = new ArrayList();
            int a = n, b = n, c = sequence[i - 1].size() - 1;
            if ((i + 1) % 3 == 0) {
                a = sequence[(i + 1) / 3 - 1].size() - 1;
            }
            if ((i + 1) % 2 == 0) {
                b = sequence[(i + 1) / 2 - 1].size() - 1;
            }

            if (a <= b && a <= c) {
                sequence[i] = (ArrayList)sequence[(i + 1) / 3 - 1].clone();
                sequence[i].add(i + 1);
            } else if (b <= a && b <= c) {
                sequence[i] = (ArrayList)sequence[(i + 1) / 2 - 1].clone();
                sequence[i].add(i + 1);
            } else if (c <= a && c <= b) {
                sequence[i] = (ArrayList)sequence[i - 1].clone();
                sequence[i].add(i + 1);
            }
        }
        return sequence[n - 1];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimal_sequence(n);
        System.out.println(sequence.size() - 1);
        for (Integer x : sequence) {
            System.out.print(x + " ");
        }
    }
}

