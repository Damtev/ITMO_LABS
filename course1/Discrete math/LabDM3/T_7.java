import java.io.*;

public class T_7 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("permutations.in")));
             PrintWriter pr = new PrintWriter("permutations.out")) {
            int n = Integer.parseInt(br.readLine());
            int[] permutation = new int[n];
            int i;
            for (i = 0; i < n; i++) {
                permutation[i] = i + 1;
            }
            do {
                for (int z = 0; z < n; z++) {
                    pr.print(permutation[z] + " ");
                }
                pr.println();
                i = n - 2;
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
            } while (i >= 0);
        }
    }
}
