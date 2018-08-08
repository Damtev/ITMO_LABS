import java.io.*;
//import java.util.*;

public class Heap {
	static String IfHeap (int n, int[] a) {
		String test = "YES";
		//if (n == 2) 
		//	if (a[0]>a[1]) test = "NO";
		if (n >= 2) {
			for (int i=0; i < n/2; i++) { //или до n
				if (2 * i + 1 >= n) break;
					else if (a[i] > a[2 * i + 1]) {
						test = "NO";
						break;
					}	
						else if (2 * i + 2 >= n) break;
							else if (a[i] > a[2 * i + 2]) {
								test = "NO";
								break;
							}	
			}
		}	
		return test;	
	}
	public static void main(String[] args) throws IOException {
		try (BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("isheap.in")));
		BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("isheap.out")))) {
			//Scanner in = new Scanner(System.in);
			int amount; 
			amount = Integer.parseInt(br.readLine());
			//amount = in.nextInt();
			//String line = in.nextLine();
			int[] digits = new int[amount];
			String buf = br.readLine();
			//String buf = in.nextLine();
			if (amount > 1) {
				String[] separ = buf.split(" ");
				for (int j = 0; j < amount; j++)
					digits[j] = Integer.parseInt(separ[j]);
			}
				else digits[0] = Integer.parseInt(buf);
			wr.write(IfHeap(amount, digits));
			//System.out.println(IfHeap(amount, digits));
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
            System.err.println("ERROR" + e.getMessage());
		}	
	}
}	