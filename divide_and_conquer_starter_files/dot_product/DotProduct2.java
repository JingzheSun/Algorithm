import java.util.*;
 
public class DotProduct {
    private static long minDotProduct(int[] a, int[] b, int[] c, int[] d, int left, int right) {
        if(right<=left+1){
        	return Long.MAX_VALUE;
        }
        int mid = (left+right)/2;        
        long sl=minDotProduct(a, b, c, d, left, mid);
        long sr=minDotProduct(a, b, c, d, mid, right);
        long delta = (sl>sr)? sr: sl;
        long result = delta;
        int i=mid-1;
        int count = 0;
        while(i>=left && Math.pow(a[mid-1]-a[i],2)<delta){
        	c[count] = a[i];
        	d[count++] = b[i--];
        }
        int j =mid;
        while(j < right && Math.pow(a[j]-a[mid],2)<delta){
			c[count] = a[j];
			d[count++] = b[j++];
        }
        Merg(d,c,0,count);
        for(i=0;i<count;i++){
        	for(j=i+1;j<i+7 && j<count;j++){
        		long temp = (long)Math.pow(c[i]-c[j],2) + (long)Math.pow(d[i]-d[j],2);
        		result = (result > temp) ? temp: result; 
        	}
        }
        return result;
    }
    
    private static int Merg(int[] a, int[] b, int left, int right) {
        if (right <= left + 1) {
            return 0;
        }
        int ave = (left + right) / 2;
        Merg(a, b, left, ave);
        Merg(a, b, ave, right);
        int k =0, i = left, j = ave;
        int[] c = new int[right - left];
        int[] d = new int[right - left];
        while(k < right -left){
        	if(i==ave){
        		while(j < right){
        			d[k] = b[j];
        			c[k++] = a[j++];
        		}
        	}
        	else if(j==right){
           		while(i < ave){
           			d[k]=b[i];
           			c[k++] = a[i++];
           		}
        	}
        	else if(a[i]>a[j]){
        		d[k]=b[j];
        		c[k++]=a[j++];
        	}
        	else{
        		d[k]=b[i];
        		c[k++]=a[i++];
        	}
        }
        for(k= 0; k < right - left; k++){
        	a[left+k]=c[k];
        	b[left+k]=d[k];
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] b = new int[n];
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
            a[i] = scanner.nextInt();
        }
        int[] c= new int[n];
        int[] d= new int[n];
        Merg(a, b, 0, n);
        System.out.println(Math.sqrt(minDotProduct(a, b, c, d, 0, n)));
    }
}

