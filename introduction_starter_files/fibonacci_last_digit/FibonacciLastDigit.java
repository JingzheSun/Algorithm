import java.util.*;

public class FibonacciLastDigit {
    private static int getFibonacciLastDigitNaive(int n) {
        
	int a = 0;
	int b = 1;
	int c = 2;
	
	if (n==0){
		return 0; 
	}else if (n==1){
		return 1;
	}
	for (int i = 2; i < n+1 ;i++){
		c=(a+b)%10;
		a=b;
		b=c;
		
	}
    return c;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = getFibonacciLastDigitNaive(n);
        System.out.println(c);
    }
}

