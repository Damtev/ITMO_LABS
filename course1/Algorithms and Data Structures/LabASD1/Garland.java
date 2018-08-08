import java.io.*;
import java.text.DecimalFormat;

public class Garland {
	
	private static DecimalFormat df2 = new DecimalFormat("#0.00");
	
	public static double Search(int n, double l, double r, double A) {
		double[] a = new double[n];
		a[0] = A;
		while (true) {
			a[1] = (l + r) / 2;
			if (a[1] == r || a[1] == l) break;
			if (Above(n, a[1], A)) r = a[1];
			else l = a[1];
		}
		for (int i = 1; i < n - 1; i++) {
			a[i + 1] = 2 * a[i] + 2 - a[i - 1];
		}
		return a[n - 1];	
	}	
	
	public static boolean Above(int n, double mid, double A) {
		boolean flag = true;
		double[] a = new double[n];
		a[0] = A;
		a[1] = mid;
		for (int i = 1; i < n - 1; i++) {
			a[i + 1] = 2 * a[i] + 2 - a[i - 1];
			if (a[i + 1] < 0) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	public static void main (String[] args) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("garland.in")));
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("garland.out")))) {
			String[] buf = br.readLine().split(" ");
			int n = Integer.parseInt(buf[0]);
			double A = Double.parseDouble(buf[1]);
			wr.write(df2.format(Math.abs(Search(n, 0, A, A))));
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}	
	}
}	