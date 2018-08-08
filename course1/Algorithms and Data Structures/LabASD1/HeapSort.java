import java.io.*;

public class HeapSort {
	static int heapSize;
	static int indexb = 0;
	
	private static int[] sort(int[] a) {
        buildHeap(a);
		int[] b = new int[a.length];
        while (heapSize > 1) {
            swap(a, 0, heapSize - 1);
			b[indexb] = a[heapSize-1];
			indexb++;
            heapSize--;
            heapify(a, 0);
        }
		b[indexb] = a[heapSize-1];
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
		
	public static void main(String[] args) throws IOException {
		try (BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("sortin.txt")));
		BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("sortout.txt")))) {
			int amount; 
			amount = Integer.parseInt(br.readLine());
			int[] digits = new int[amount];
			String buf = br.readLine();
			if (amount > 1) {
				String[] separ = buf.split(" ");
				for (int j = 0; j < amount; j++)
					digits[j] = Integer.parseInt(separ[j]);
			}
				else digits[0] = Integer.parseInt(buf);
			if (amount == 1) wr.write(digits[0]);
				else {
					int[] c = sort(digits);
					for (int z = 0; z < c.length; z++)
						wr.write(c[z] + " ");
				}	
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}
	}
}