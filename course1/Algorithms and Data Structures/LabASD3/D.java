import java.io.*;

public class D {

    static int min(int x, int y, int z) {
        return Math.min(Math.min(x, y), z);
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
             PrintWriter pr = new PrintWriter("output.txt")) {
            StringBuilder start = new StringBuilder(" ");
            String s1 = new String(start.append(br.readLine()));
            int m = s1.length();
            start = new StringBuilder(" ");
            String s2 = new String(start.append(br.readLine()));
            int n = s2.length();
            int[][] dp = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 && j == 0) {
                        dp[i][j] = 0;
                        continue;
                    }
                    if (i > 0 && j == 0) {
                        dp[i][j] = i;
                        continue;
                    }
                    if (i == 0 && j > 0) {
                        dp[i][j] = j;
                        continue;
                    }
                    if (s1.charAt(i) == s2.charAt(j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                        continue;
                    }
                    dp[i][j] = min(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1]) + 1;
                }
            }
            pr.println(dp[m - 1][n - 1]);
        }
    }
}
