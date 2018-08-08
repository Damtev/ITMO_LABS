import java.util.Scanner;

public class F {

    static int min(int x, int y, int z) {
        return Math.min(Math.min(x, y), z);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String brackets = in.nextLine();
        int n = brackets.length();
        int[][] dp = new int[n][n];
        for (int j = 0; j < n; j++) {
            for (int i = j; i >= 0; i--) {
                if (i == j) {
                    dp[i][j] = 1;
                }
                else {
                    int min = 9999999;
                    if (brackets.charAt(i) == '(' && brackets.charAt(j) == ')' || brackets.charAt(i) == '[' && brackets.charAt(j) == ']'
                    || brackets.charAt(i) == '{' && brackets.charAt(j) == '}') {
                        min = dp[i + 1][j - 1];
                    }
//                    if (i < n - 1) {
//                        min = Math.min(min, dp[i + 1][j] + 1);
//                    }
//                    if (j > 0) {
//                        min = Math.min(min, dp[i][j - 1] + 1);
//                    }
                    for (int k = i; k < j; k++) {
                        min = Math.min(min, dp[i][k] + dp[k + 1][j]);
//                        min = dp[i][k] + dp[k + 1][j];
                    }
                    dp[i][j] = min;
                }
            }
        }
        System.out.println(n - dp[0][n - 1]);
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(dp[i][j] + " ");
//            }
//            System.out.println();
//        }
    }
}
