import java.io.*;

public class Huffman {

    private static int heapSize;
    private static int indexb = 0;

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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("huffman.in")));
             BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("huffman.out")))) {
            int n = Integer.parseInt(br.readLine());
            int[] digits = new int[n];
            long[] sum = new long[n];
            for (int z = 0; z < n; z++)
                sum[z] = Long.MAX_VALUE / 100;
            int ch = 0;
            String[] buf = br.readLine().split(" ");
            for (int z = 0; z < n; z++)
                digits[z] = Integer.parseInt(buf[z]);
            int i = 0;
            int j = 0;
            long ans = 0;
            int[] rate = sort(digits);
            for (int k = 0; k < n - 1; k++) {
                if ((i < n - 1) && (j < n - 1)) {
                    if ((rate[i] + rate[i + 1] <= rate[i] + sum[j]) && (rate[i] + rate[i + 1] <= sum[j] + sum[j + 1])) {
                        sum[k] = rate[i] + rate[i + 1];
                        ans += sum[k];
                        i += 2;
                        continue;
                    }
                    if ((rate[i] + sum[j] <= rate[i] + rate[i + 1]) && (rate[i] + sum[j] <= sum[j] + sum[j + 1])) {
                        sum[k] = rate[i] + sum[j];
                        ans += sum[k];
                        i++;
                        j++;
                        continue;
                    }
                    if ((sum[j] + sum[j + 1] <= rate[i] + rate[i + 1]) && (sum[j] + sum[j + 1] <= rate[i] + sum[j])) {
                        sum[k] = sum[j] + sum[j + 1];
                        ans += sum[k];
                        j += 2;
                        continue;
                    }
                }
                if (i < n && j < n - 1 && rate[i] <= sum[j + 1]) {
                    sum[k] = rate[i] + sum[j];
                    ans += sum[k];
                    i++;
                    j++;
                    continue;
                }
                if (i < n - 1 && j < n && sum[j] <= rate[i + 1]) {
                    sum[k] = sum[j] + rate[i];
                    ans += sum[k];
                    i++;
                    j++;
                    continue;
                }
                if (i < n - 1) {
                    sum[k] = rate[i] + rate[i + 1];
                    ans += sum[k];
                    i += 2;
                    continue;
                }
                if (j < n - 1) {
                    sum[k] = sum[j] + sum[j + 1];
                    ans += sum[k];
                    j += 2;
                }
            }
            wr.write(String.valueOf(ans));
            //for (int z = 0; z < n; z++)
            //    wr.write(sum[z] + " ");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERROR" + e.getMessage());
        }
    }
}
