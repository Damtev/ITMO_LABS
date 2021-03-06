import java.util.Scanner;

public class C {

    static long mod(long x) {
        return x % 1000000000;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        long[][] dp = new long[n + 1][10];
        long ans = 0;
        for (int j = 1; j <= 9; j++) {
            if (j != 8) {
                dp[1][j] = 1;
            }
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= 9; j++) {
                switch (j) {
                    case 0:
                        dp[i][j] = mod(dp[i - 1][4] + dp[i - 1][6]);
                        break;
                    case 1:
                        dp[i][j] = mod(dp[i - 1][6] + dp[i - 1][8]);
                        break;
                    case 2:
                        dp[i][j] = mod(dp[i - 1][7] + dp[i - 1][9]);
                        break;
                    case 3:
                        dp[i][j] = mod(dp[i - 1][4] + dp[i - 1][8]);
                        break;
                    case 4:
                        dp[i][j] = mod(dp[i - 1][0] + dp[i - 1][3] + dp[i - 1][9]);
                        break;
                    case 5:
                        break;
                    case 6:
                        dp[i][j] = mod(dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][7]);
                        break;
                    case 7:
                        dp[i][j] = mod(dp[i - 1][2] + dp[i - 1][6]);
                        break;
                    case 8:
                        dp[i][j] = mod(dp[i - 1][1] + dp[i - 1][3]);
                        break;
                    case 9:
                        dp[i][j] = mod(dp[i - 1][2] + dp[i - 1][4]);
                        break;
                }
            }
        }
        for (int j = 0; j <= 9; j++) {
            ans = (ans + dp[n][j]) % 1000000000;
        }
        System.out.println(ans);
    }
}
