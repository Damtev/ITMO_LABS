import java.io.*;
import java.util.Arrays;

public class T_28 {

    static int[] nextmultiperm (int n, int[] permutation) {
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
            for (int z = i + 1, k = n - 1; z < k; z = z + 1, k = k - 1) {
                temp = permutation[z];
                permutation[z] = permutation[k];
                permutation[k] = temp;
            }
            return permutation;
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("nextmultiperm.in")));
             PrintWriter pr = new PrintWriter("nextmultiperm.out")) {
            int n = Integer.parseInt(br.readLine());
            String[] buf = br.readLine().split(" ");
            int[] multipermutation = new int[n];
            for (int i = 0; i < n; i++) {
                multipermutation[i] = Integer.parseInt(buf[i]);
            }
            multipermutation = nextmultiperm(n, multipermutation);
            if (multipermutation == null) {
                for (int i = 0; i < n; i++) {
                    pr.print("0 ");
                }
            }
            else {
                for (int i = 0; i < n; i++) {
                    pr.print(multipermutation[i]);
                    if (i != n - 1) {
                        pr.print(" ");
                    }
                }
            }
        }
    }
}
