import java.io.*;

public class T_17 {

    static String num2brackets(int n, long k, long[][] d) {
        int depth = 0;
        StringBuilder brackets = new StringBuilder("");
        if (k == 0) {
            for (int i = 0; i < n; i++) {
                brackets.append('(');
            }
            for (int i = 0; i < n; i++) {
                brackets.append(')');
            }
        }
        else {
            for (int i = 0; i < 2 * n; i++) {
                if (depth + 1 <= n && d[2 * n - i - 1][depth + 1] >= k) {
                    brackets.append('(');
                    depth++;
                }
                else if (depth + 1 <= n && d[2 * n - i - 1][depth + 1] < k) {
                    brackets.append(')');
                    k -= d[2 * n - i - 1][depth + 1];
                    depth--;
                }
            }
        }
        return brackets.toString();
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("num2brackets.in")));
             PrintWriter pr = new PrintWriter("num2brackets.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            long k = Long.parseLong(buf[1]);
            long[][] d = new long[2 * n][n + 1];
            d[0][0] = 1;
            for (int i = 1; i < 2 * n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j != 0) {
                        d[i][j] = d[i - 1][j - 1] + d[i - 1][j + 1];
                    }
                    else {
                        d[i][j] = d[i - 1][j + 1];
                    }
                }
            }
            pr.println(num2brackets(n, k, d));
        }
    }
}
