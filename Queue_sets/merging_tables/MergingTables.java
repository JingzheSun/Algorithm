import java.io.*;
import java.util.*;

public class MergingTables {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Table[] table = new Table[n];
        int[] merge = new int[m];
        int[] merged = new int[m];

        for (int i = 0; i < n; i++) {
            table[i] = new Table(scanner.nextInt());
        }
        for (int j = 0; j < m; j++) {
            merge[j] = scanner.nextInt();
            merged[j] = scanner.nextInt();
        }

        mergeTables(merge, merged, table);
    }

    public static void mergeTables(int[] merge, int[] merged, Table[] table) {
        int max = 0;
        for (int j = 0; j < table.length; j++) {
            if (max < table[j].num) {
                max = table[j].num;
            }
        }
        for (int i = 0; i < merge.length; i++) {
            if (table[merge[i] - 1].equals(table[merged[i] - 1])) {
                System.out.println(max);
                continue;
            }
            
			table[merge[i] - 1].setNum(table[merged[i] - 1].num + table[merge[i] - 1].num);
            int hash =table[merged[i] - 1].hashCode();
            table[merged[i] - 1] = table[merge[i] - 1];
            for(int k = 0; k<i;k++){
                if(table[merged[k] - 1].hashCode() == hash){
                    table[merged[k] - 1] =table[merge[i] - 1];
                }
            }
            if (table[merged[i] - 1].num > max) {
                max = table[merged[i] - 1].num;
            }
            System.out.println(max);
        }

    }

    private static class Table implements Comparable<Table> {

        int num;

        Table(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public int compareTo(Table o) {
            return this.getNum() - o.getNum();
        }
    }
}
