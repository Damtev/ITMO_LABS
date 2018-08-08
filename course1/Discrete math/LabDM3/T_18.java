import java.io.*;

public class T_18 {

    static long brackets2num(int n, String brackets, long[][] d) {
        long number = 0;
        int depth = 1;
        if (n != 1) {
            for (int i = 0; i < 2 * n; i++) {
                if (brackets.charAt(i) == '(') {
                    depth++;
                }
                else {
                    number += d[2 * n - i - 1][depth];
                    depth--;
                }
            }
        }
        return number;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("brackets2num.in")));
             PrintWriter pr = new PrintWriter("brackets2num.out")) {
            String brackets = br.readLine().trim();
            int n = brackets.length() / 2;
            long[][] d = new long[2 * n][2 * n];
            d[0][0] = 1;
            for (int j = 1; j < 2 * n; j++) {
                d[0][j] = 0;
            }
            for (int i = 1; i < 2 * n; i++) {
                for (int j = 0; j < 2 * n - 1; j++) {
                    if (j != 0) {
                        d[i][j] = d[i - 1][j - 1] + d[i - 1][j + 1];
                    }
                    else {
                        d[i][j] = d[i - 1][j + 1];
                    }
                }
            }
            pr.println(brackets2num(n, brackets, d));
        }
    }
}
