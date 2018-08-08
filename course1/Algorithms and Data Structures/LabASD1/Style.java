import java.io.*;
//import java.util.*;

public class Style {
	
	public static void main (String[] args) throws IOException {
		try (BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("stylein.txt")));
		BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("styleout.txt")))) {
			//Scanner in = new Scanner(System.in);
			int n = Integer.parseInt(br.readLine());
			//int n = Integer.parseInt(in.nextLine());
			//String buf = in.nextLine();
			int[] a = new int[n];
			String buf = br.readLine();
			if (n > 1) {
				String[] separ = buf.split(" ");
				for (int l = 0; l < n; l++)
					a[l] = Integer.parseInt(separ[l]);
			}	
				else a[0] = Integer.parseInt(buf);
			int m = Integer.parseInt(br.readLine());
			//int m = Integer.parseInt(in.nextLine());
			//buf = in.nextLine();
			int[] b = new int[m];
			buf = br.readLine();
			if (m > 1) {
				String[] separ = buf.split(" ");
				for (int k = 0; k < m; k++)
					b[k] = Integer.parseInt(separ[k]);
			}	
				else b[0] = Integer.parseInt(buf);
			int min = Math.abs(a[0] - b[0]);
			if (min == 0) wr.write(a[0] + " " + b[0]);
			//if (min == 0) System.out.println(a[0] + " " + b[0]);
				else {
					int i = 0;
					int j = 0;
					int a_exit = a[0];
					int b_exit = b[0];
					while (i < n && j < m) {
						if (a[i] == b[j]) {
							a_exit = a[i];
							b_exit = b[j];
							break;
						}
							else if (a[i] < b[j]) {
								if (min > Math.abs(a[i] - b[j])) { 
									min = Math.abs(a[i] - b[j]);
									a_exit = a[i];
									b_exit = b[j];
								}	
								i++;
							}
								else {
									if (min > Math.abs(a[i] - b[j])) {
										min = Math.abs(a[i] - b[j]);
										a_exit = a[i];
										b_exit = b[j];
									}	
									j++;
								}	
					}
					wr.write(a_exit + " " + b_exit);
					//System.out.println(a_exit + " " + b_exit);
				}
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}
	}	
}	