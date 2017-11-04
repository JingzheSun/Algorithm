import java.util.Scanner;

public class Fibonacci {
  private static long calc_fib(int n) {
    
	long a = 0;
	long b = 1;
	long c = 2;
	
	if (n==0){
		return 0; 
	}else if (n==1){
		return 1;
	}
	for (int i = 2; i < n+1 ;i++){
		c=a+b;
		a=b;
		b=c;
		
	}
    return c;
  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();

    System.out.println(calc_fib(n));
  }
}
