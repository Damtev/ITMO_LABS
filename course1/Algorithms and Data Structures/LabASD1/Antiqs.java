import java.io.*;
import java.util.*;

public class Antiqs {
	
	public static void main (String[] args) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("antiqs.in")));
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("antiqs.out")))) {
			int n = Integer.parseInt(br.readLine());
			wr.write("1 ");
			int even = 2;
			while (2 * even <=n) {
				wr.write(2 * even + " ");
				even++;
			}
			if (n > 1 && (n % 2 == 1)) {
				wr.write(n + " ");
				even++;
			}	
			while (even <= n) {
				int odd = even;
				while (odd % 2 == 1) odd = (odd + 1) / 2;
				if (odd > 2) wr.write(odd - 1 + " ");
				else wr.write(odd + " ");
				even++;
			}	
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}	
	}	
}	