import java.util.*;

public class Inversions {

    static long numberOfInversions = 0;

    private static void getNumberOfInversions(int[] a, int left, int right) {

        if (right <= left + 1) {
            return;
        }
        int ave = (left + right) / 2;
        getNumberOfInversions(a, left, ave);
        getNumberOfInversions(a, ave, right);
        merge(a, left, ave, right);
    }

    public static void merge(int[] nums, int low, int j, int high) {
        int[] temp = new int[high - low];
        int i = low;
        int mid = j - 1;
        int k = 0;

        while (i <= mid && j <= high - 1) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
                numberOfInversions = numberOfInversions + mid - i + 1;
            }
        }
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        while (j <= high - 1) {
            temp[k++] = nums[j++];
        }
        for (int k2 = 0; k2 < temp.length; k2++) {
            nums[k2 + low] = temp[k2];
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        getNumberOfInversions(a, 0, a.length);
        System.out.println(numberOfInversions);
    }
}

