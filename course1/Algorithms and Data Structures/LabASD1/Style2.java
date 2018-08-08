import java.io.*;

public class Style2 {
	
	static int heapSize;
	
	private static int[] sort(int[] a) {
        int indexb = 0;
		buildHeap(a);
		int[] b  =new int[a.length];
        while (heapSize > 1) {
            swap(a, 0, heapSize - 1);
			b[indexb] = a[heapSize - 1];
			indexb++;
            heapSize--;
            heapify(a, 0);
        }
		b[indexb] = a[heapSize - 1];
		return b;
    }
	
	private static void buildHeap(int[] a) {
		heapSize = a.length;
		for (int i = a.length / 2; i >= 0; i--) {
			heapify(a, i);
		}
    }
	
	private static void heapify(int[] a, int i) {
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int min = i;
        if (l < heapSize && a[i] > a[l]) {
            min = l;
        } 
        if (r < heapSize && a[min] > a[r]) {
            min = r;
        }
        if (i != min) {
            swap(a, i, min);
            heapify(a, min);
        }
    }
	
	private static void swap(int[] a, int i, int j) {
		int buf = a[i];
		a[i] = a[j];
		a[j] = buf;
	}
	
	public static void main (String[] args) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("style.in")));
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("style.out")))) {
			int n = Integer.parseInt(br.readLine());
			int[] old_a = new int[n];
			String buf = br.readLine();
			if (n > 1) {
				String[] separ = buf.split(" ");
				for (int i = 0; i < n; i++)
					old_a[i] = Integer.parseInt(separ[i]);
			}	
			else old_a[0] = Integer.parseInt(buf);
			int[] a = sort(old_a);
			
			int m = Integer.parseInt(br.readLine());
			int[] old_b = new int[m];
			buf = br.readLine();
			if (m > 1) {
				String[] separ = buf.split(" ");
				for (int i = 0; i < m; i++)
					old_b[i] = Integer.parseInt(separ[i]);
			}	
			else old_b[0] = Integer.parseInt(buf);
			int[] b = sort(old_b);
			
			int p = Integer.parseInt(br.readLine());
			int[] old_c = new int[p];
			buf = br.readLine();
			if (p > 1) {
				String[] separ = buf.split(" ");
				for (int i = 0; i < p; i++)
					old_c[i] = Integer.parseInt(separ[i]);
			}	
			else old_c[0] = Integer.parseInt(buf);
			int[] c = sort(old_c);
			
			int k = Integer.parseInt(br.readLine());
			int[] old_d = new int[k];
			buf = br.readLine();
			if (k > 1) {
				String[] separ = buf.split(" ");
				for (int i = 0; i < k; i++)
					old_d[i] = Integer.parseInt(separ[i]);
			}	
			else old_d[0] = Integer.parseInt(buf);
			int[] d = sort(old_d);
			
			int min = Math.min(Math.min(a[0], b[0]), Math.min(c[0], d[0]));
			int max = Math.max(Math.max(a[0], b[0]), Math.max(c[0], d[0]));
			int diff = max - min;
			int ans_1 = a[0];
			int ans_2 = b[0];
			int ans_3 = c[0];
			int ans_4 = d[0];
			if (diff != 0) {
				int q = 0;
				int w = 0;
				int e = 0;
				int r = 0;
				while (q < n - 1 || w < m - 1 || e < p - 1 || r < k - 1) {
					min = Math.min(Math.min(a[q], b[w]), Math.min(c[e], d[r]));
					max = Math.max(Math.max(a[q], b[w]), Math.max(c[e], d[r]));
					if (max - min < diff) {
						diff = max - min;
						ans_1 = a[q];
						ans_2 = b[w];
						ans_3 = c[e];
						ans_4 = d[r];
					}	
					if (diff == 0) break;

					if (a[q] == min) {
						q++;
						if (q == n) break;
					}
					else if (b[w] == min) {
						w++;
						if (w == m) break;
					}
					else if (c[e] == min) {
						e++;
						if (e == p) break;
					}
					else {
						r++;
						if (r == k) break;
					}
					
					if (q == n - 1 && w == m - 1 && e == p - 1 && r == k - 1) break;
				}
			}	
			wr.write(ans_1 + " " + ans_2 + " " + ans_3 + " " + ans_4);	
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}
	}
}	