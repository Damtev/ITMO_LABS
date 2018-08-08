import java.io.*;
import java.util.Arrays;

public class T_24 {

    static int[] prevperm (int n, int[] permutation) {
        int i = n - 2;
        while (i >= 0 && permutation[i] <= permutation[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = i + 1;
            while (j < n - 1 && permutation[i] > permutation[j + 1]) {
                j++;
            }
            int temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }
        for (int j = i + 1, k = n - 1; j < k; j = j + 1, k = k - 1) {
            int temp = permutation[j];
            permutation[j] = permutation[k];
            permutation[k] = temp;
        }
        return permutation;
    }

    static int[] nextperm (int n, int[] permutation) {
        int i = n - 2;
        while (i >= 0 && permutation[i] >= permutation[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = i + 1;
            while (j < n - 1 && permutation[i] < permutation[j + 1]) {
                j++;
            }
            int temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }
        for (int j = i + 1, k = n - 1; j < k; j = j + 1, k = k - 1) {
            int temp = permutation[j];
            permutation[j] = permutation[k];
            permutation[k] = temp;
        }
        return permutation;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("nextperm.in")));
             PrintWriter pr = new PrintWriter("nextperm.out")) {
            int n = Integer.parseInt(br.readLine());
            String[] buf = br.readLine().split(" ");
            int[] permutation = new int[n];
            int[] origin = new int[n];
            int[] last = new int[n];
            for (int i = 0; i < n; i++) {
                permutation[i] = Integer.parseInt(buf[i]);
                origin[i] = i + 1;
                last[i] = n - i;
            }
            int[] prev = new int[n];
            int[] next = new int[n];
            for (int i = 0; i < n; i++) {
                prev[i] = permutation[i];
                next[i] = permutation[i];
            }
            prev = prevperm(n, prev);
            if (Arrays.equals(last, prev)) {
                for (int i = 0; i < n; i++) {
                    pr.print("0 ");
                }
            }
            else {
                for (int i = 0; i < n; i++) {
                    pr.print(prev[i] + " ");
                }
            }
            pr.println();
            next = nextperm(n, next);
            if (Arrays.equals(origin, next)) {
                for (int i = 0; i < n; i++) {
                    pr.print("0 ");
                }
            }
            else {
                for (int i = 0; i < n; i++) {
                    pr.print(next[i] + " ");
                }
            }
        }
    }
}
