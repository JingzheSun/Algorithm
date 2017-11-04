import java.util.*;

public class LargestNumber {
    
    public static void sortinput(String[] input) {
        for (int i = 0; i < input.length - 1; i++) {
            for (int j = 0; j < input.length - i - 1; j++) {
                if ((input[j]+ input[j + 1]).compareTo(input[j + 1]+ input[j]) < 0) {
                    String temp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] a = new String[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.next();
        }
        sortinput(a);
        for(int i = 0; i<a.length;i++){
            System.out.print(a[i]);
        }
    }
}

