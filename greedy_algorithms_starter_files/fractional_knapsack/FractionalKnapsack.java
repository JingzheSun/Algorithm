import java.util.*;

public class FractionalKnapsack {
    
	private static double getOptimalValue(int capacity, int[] values, int[] weights, int n) {
        double value = 0;
        ArrayList index = new ArrayList();
        int numItem = n;
        while (capacity > 0 && n > 0) {

            double maxUnitValue = 0;
            int maxIndex = 0;
            for (int i = 0; i < numItem; i++) {
                if ((double)values[i] / weights[i] > maxUnitValue && !index.contains(i)) {
                    maxUnitValue = (double) values[i] / weights[i];
                    maxIndex = i;
                }
            }
            index.add(maxIndex);
            if (capacity >= weights[maxIndex]) {
                value = value + values[maxIndex];
                capacity = capacity - weights[maxIndex];
            } else {
                value = value + (double)values[maxIndex] * capacity / weights[maxIndex];
                capacity = 0;
            }
            n--;
        }
        return value;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
        System.out.printf("%.3f",getOptimalValue(capacity, values, weights, n));
    }
} 
