import java.io.*;
import java.util.*;

public class RadixSort {
	
	public static String[] Sort(String[] lines, int n, int m, int k) {
		int[] keys = new int[26];
		int[] pos = new int[25];
		for (int j = 0; j < 25; j++) {
			keys[j] = 0;
			pos[j] = 0;
		}
		keys[25] = 0;
		String[] s_lines = new String[n];
			
				for (int j = 0; j < n; j++) 
					keys[(int) lines[j].charAt(m - 1) - 97]++;
				for (int j = 1; j < 25; j++)
					pos[j] = pos[j - 1] + keys[j - 1];
				for (int j = 0; j < n; j++)
					s_lines[pos[(int) lines[j].charAt(m - 1) - 97]++] = lines[j];
				if (k ==1) return s_lines;
				else {
					k--;
					m--;
					return Sort(s_lines, n, m, k);
				}	
	}	
	
	public static void main (String[] args) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("radixsortin.txt")));
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("radixsortout.txt")))) {
			int[] keys = new int[26];
			int[] pos = new int[25];
			for (int j = 0; j < 25; j++) {
				keys[j] = 0;
				pos[j] = 0;
			}
			keys[25] = 0;
			String[] buf = br.readLine().split(" ");
			int n = Integer.parseInt(buf[0]);
			int m = Integer.parseInt(buf[1]);
			int k = Integer.parseInt(buf[2]);
			String[] lines = new String[n];
			for (int i = 0; i < n; i++)
				lines[i] = br.readLine();
			String[] s_lines = Sort(lines, n, m, k);
			for (int i = 0; i < n; i++) {
				wr.write(s_lines[i]);
				wr.newLine();
			}	
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}	
	}	
}	