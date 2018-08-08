import java.io.*;
import java.util.*;

public class Kth {

	public static int find(int[] a, int l, int r, int k) {
		while (l < r) {
			int i = l; 
			int j = r;
			int x = a[(r - l) / 2 + l];
			while (i <= j) {
				while (a[i] < x) i++;
				while (a[j] > x) j--;
				if (i <= j) {
					int t = a[i];
					a[i] = a[j];
					a[j] = t;
					i++;
					j--;
				}
			}
			if (k <= j)
				r = j;
			else if (k >= i)
				l = i;
			else
				break;
		}
		return a[k];
	}
	
	public static void main(String[] args) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("kth.in")));
		/*BufferedWriter wr = new BufferedWriter(new FileWriter(new File("kthout.txt")))*/
		PrintWriter pw = new PrintWriter(new File("kth.out"))) {
			String[] buf_1 = br.readLine().split(" ");
			int n = Integer.parseInt(buf_1[0]);
			int k = Integer.parseInt(buf_1[1]);
			int[] a = new int[n];
			String[] buf_2 = br.readLine().split(" ");
			int a_const = Integer.parseInt(buf_2[0]);
			int b_const = Integer.parseInt(buf_2[1]);
			int c_const = Integer.parseInt(buf_2[2]);
			a[0] = Integer.parseInt(buf_2[3]);
			a[1] = Integer.parseInt(buf_2[4]);
			for (int i = 2; i < n; i++)
				a[i] = a_const * a[i-2] + b_const * a[i-1] + c_const;
			//wr.write(find(a, 0, n, k));
			pw.print(find(a, 0, n - 1, k - 1));
			//pw.print(a[find(a, n, k)]);
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}	
	}	
}	