import java.util.*;

public class LCM {
  private static long gcd(int a, int b) {
    
	if (b ==0){
		return a;
	}else{
		return gcd( b, a%b);
	}
  }

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println((long)a*b/gcd(a, b));
  }
}
