import java.io.*;

public class T_19 {

    static String num2brackets2(int n, long k, long[][] d) {
        int depth = 0;
        StringBuilder brackets = new StringBuilder("");
        char[] hooks = new char[2 * n];
        int count = 0;
        if (k == 0) {
            for (int i = 0; i < n; i++) {
                brackets.append('(');
            }
            for (int i = 0; i < n; i++) {
                brackets.append(')');
            }
        }
        else {
            for (int i = 2 * n - 1; i >= 0; i--) {
                long temp = 0;
                if (depth + 1 <= n) {
                    temp = d[i][depth + 1] * (long) Math.pow(2, (i - depth - 1) / 2 );
                }
                if (temp >= k) {
                    brackets.append('(');
                    hooks[count++] = '(';
                    depth++;
                    continue;
                }
                k -= temp;
                if (count > 0 && hooks[count - 1] == '(' && depth >= 1) {
                    temp = d[i][depth - 1] * (long) Math.pow(2, (i - depth + 1) / 2 );
                }
                else {
                    temp = 0;
                }
                if (temp >= k) {
                    brackets.append(')');
                    count--;
                    depth--;
                    continue;
                }
                k -= temp;
                if (depth + 1 <= n) {
                    temp = d[i][depth + 1] * (long) Math.pow(2, (i - depth - 1) / 2 );
                }
                else {
                    temp = 0;
                }
                if (temp >= k) {
                    brackets.append('[');
                    hooks[count++] = '[';
                    depth++;
                    continue;
                }
                k -= temp;
                brackets.append(']');
                count--;
                depth--;
            }
        }
        return brackets.toString();
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("num2brackets2.in")));
             PrintWriter pr = new PrintWriter("num2brackets2.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            long k = Long.parseLong(buf[1]);
            long[][] d = new long[2 * n + 1][n + 1];
            d[0][0] = 1;
            for (int i = 0; i < 2 * n; i++) {
                for (int j = 0; j <= n; j++) {
                    if (j + 1 <= n) {
                        d[i + 1][j + 1] += d[i][j];
                    }
                    if (j > 0) {
                        d[i + 1][j - 1] += d[i][j];
                    }
                }
            }
            pr.println(num2brackets2(n, k + 1, d));
        }
    }
}
