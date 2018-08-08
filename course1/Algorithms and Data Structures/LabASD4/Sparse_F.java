import java.io.*;

public class Sparse_F {

    private static int n;
    private static int[] log;
    private static int st[][];

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("sparse.in")));
             PrintWriter pr = new PrintWriter("sparse.out")) {
            String[] vars = br.readLine().split(" ");
            n = Integer.parseInt(vars[0]);
            int m = Integer.parseInt(vars[1]);
            int[] a = new int[n];
            int[] u = new int[m + 1];
            int[] v = new int[m + 1];
            int[] ans = new int[m + 1];
            a[0] = Integer.parseInt(vars[2]);
            for (int i = 0; i < n - 1; i++) {
                a[i + 1] = (23 * a[i] + 21563) % 16714589;
            }
            computeLog();
            computeSparseTable(a);
            vars = br.readLine().split(" ");
            u[1] = Integer.parseInt(vars[0]);
            v[1] = Integer.parseInt(vars[1]);
            for (int i = 1; i < m; i++) {
                ans[i] = computeMin(u[i] - 1, v[i] - 1);
                u[i + 1] = ((17 * u[i] + 751 + ans[i] + 2 * i) % n) + 1;
                v[i + 1] = ((13 * v[i] + 593 + ans[i] + 5 * i) % n) + 1;
            }
            ans[m] = computeMin(u[m] - 1, v[m] - 1);
            pr.println(u[m] + " " + v[m]+ " " + ans[m]);
            /*pr.print("U:\t");
            for (int i = 0; i < m; i++) {
                pr.print(u[i] + "\t");
            }
            pr.println();
            pr.print("V: \t");
            for (int i = 0; i < m; i++) {
                pr.print(v[i] + " \t");
            }
            pr.println();
            pr.print("ANS:\t");
            for (int i = 0; i < m; i++) {
                pr.print(ans[i] + "\t");
            }*/
        }
    }

    private static void computeLog() {
        log = new int[n + 1];
        log[1] = 0;
        for (int i = 2; i <= n; i++) {
            log[i] = log[i >> 1] + 1;
        }
    }

    private static void computeSparseTable(int[] array) {
        int k = log[n] + 1;
        st = new int[n][k];
        for (int i = 0; i < n; i++) {
            st[i][0] = array[i];
        }
        for (int j = 1; j <= k; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                st[i][j] = Math.min(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    private static int computeMin(int l, int r) {
        if (l > r) {
            int buf = r;
            r = l;
            l = buf;
        }
        int j = log[r - l + 1];
        return  Math.min(st[l][j], st[r - (1 << j) + 1][j]);
    }
}
