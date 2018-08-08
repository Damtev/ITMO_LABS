import java.util.*;
import java.io.*;

public class BinSearch {
	
	public static int leftSearch(int[] a, int l, int r, int k) {
		/*while (r - l > 1) {
			int m = l + (r - l) / 2;
			if (k > a[m])
				return leftSearch(a, m, r, k); //В правую половину
				else 
					return leftSearch(a, l, m, k); //В левую половину
		}*/
		
		while (l != r) {
			int m = l + (r - l) / 2;
			if (a[m] < k)
				l = m + 1;
				else
					r = m;
		}
		if (a[l] == k)    
			return l + 1;
		else                     
			return -1;
		
		/*while (l < r)
			if (a[l] == k) break;
				else l++;
		return l; //Возвращает самое левое вхождение*/
	}

	public static int rightSearch(int[] a, int l, int r, int k) {
		/*while (r - l > 1) {
			int m = l + (r - l) / 2;
			if (k >= a[m])
				return rightSearch(a, m, r, k); //В правую половину
				else 
					return rightSearch(a, l, m, k); //В левую половину
		}
		while (l < r)
			if (a[r] == k) break;
				else r--;*/
				
		while (l < r - 1) {
			int m = l + (r - l) / 2;
			if (a[m] <= k)
				l = m;
				else
					r = m - 1;
		}
		if (a[r] == k)    
			return r + 1;
		else if (a[l] == k)
			return l + 1;
		else
			return -1;   		
				
				
		//return r; //Возвращает самое правое вхождение
	}
	
	
	
	public static void main(String[] args) throws IOException {
		try (BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("binsearch.in")));
		BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("binsearch.out")))) {
			//Scanner in = new Scanner(System.in);
			//int n = in.nextInt();
			//String buf = in.nextLine();
			int n = Integer.parseInt(br.readLine());
			int[] a = new int[n];
			//String[] buf_1 = in.nextLine().split(" ");
			String[] buf_1 = br.readLine().split(" ");
			for (int i = 0; i < n; i++)
				a[i] = Integer.parseInt(buf_1[i]);
			int m = Integer.parseInt(br.readLine());
			//int m = in.nextInt();
			int[] b = new int[m];
			//buf = in.nextLine();
			String[] buf_2 = br.readLine().split(" ");
			//String[] buf_2 = in.nextLine().split(" ");
			for (int j = 0; j < m; j++)
				b[j] = Integer.parseInt(buf_2[j]);
			for (int i = 0; i < m; i++) {
				//if (a[leftSearch(a, 0, n -1, b[i])] == a[rightSearch(a, 0, n - 1, b[i])]) {
					//int left = leftSearch(a, 0, n -1, b[i]) + 1;
					//int right = rightSearch(a, 0, n - 1, b[i]) + 1;
					wr.write(leftSearch(a, 0, n - 1, b[i]) + " " + rightSearch(a, 0, n - 1, b[i]));
					wr.newLine();
					//System.out.println(left + " " + right);
					//System.out.println(leftSearch(a, 0, n - 1, b[i]) + " " + rightSearch(a, 0, n - 1, b[i]));
				//}	
					//else wr.write("-1" + " " + "-1");
					//else System.out.println("-1" + " " + "-1");
			}	
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}	
	}	
}	