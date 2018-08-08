import java.io.*;

public class Sum0_A {

    private static final int aMod = (1 << 16) - 1;
    private static final int bMod = (1 << 30) - 1;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("sum0.in")));
             PrintWriter pr = new PrintWriter("sum0.out")) {
            String[] vars = br.readLine().split(" ");
            int n = Integer.parseInt(vars[0]);
            int x = Integer.parseInt(vars[1]);
            int y = Integer.parseInt(vars[2]);
            long[] a = new long[n];
            long[] pref = new long[n];
            a[0] = Long.parseLong(vars[3]);
            pref[0] = a[0];
            for (int i = 1; i < n; i++) {
                a[i] = (x * a[i - 1] + y) & aMod;
                pref[i] = pref[i - 1] + a[i];
            }
            vars = br.readLine().split(" ");
            int m = Integer.parseInt(vars[0]);
            int z = Integer.parseInt(vars[1]);
            int t = Integer.parseInt(vars[2]);
            long ans = 0;
            if (m > 0) {
                int[] b = new int[m << 1];
                int[] c = new int[m << 1];
                b[0] = Integer.parseInt(vars[3]);
                c[0] = b[0] % n;
                for (int i = 1; i < (m << 1); i++) {
                    b[i] = (z * b[i - 1] + t) & bMod;
                    c[i] = b[i] % n;
                }
                for (int i = 0; i < m; i++) {
                    int i2 = (i << 1);
                    if (c[i2 + 1] < c[i2]) {
                        int buf = c[i2 + 1];
                        c[i2 + 1] = c[i2];
                        c[i2] = buf;
                    }
                    ans += (c[i2] > 0) ? pref[c[i2 + 1]] - pref[c[i2] - 1] : pref[c[i2 + 1]];
                }
            }
            pr.println(ans);
        }
    }
}
