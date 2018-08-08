import java.util.Scanner;

public class G {

    static int min(int x, int y, int z) {
        return Math.min(Math.min(x, y), z);
    }

    static StringBuilder rightSeq(String brackets, int l, int r, int[][] dp, int[][] bestSplit, StringBuilder sb) {
        if (dp[l][r] == r - l + 1) {
            return sb;
        }
        if (dp[l][r] == 0) {
            return sb.append(brackets.substring(l, r + 1));
        }
        if (bestSplit[l][r] == -1) {
            sb.append(brackets.charAt(l));
            rightSeq(brackets, l + 1, r - 1, dp, bestSplit, sb);
            sb.append(brackets.charAt(r));
            return sb;
        }
        rightSeq(brackets, l, bestSplit[l][r], dp, bestSplit, sb);
        rightSeq(brackets, bestSplit[l][r] + 1, r, dp, bestSplit, sb);
        return sb;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String brackets = in.nextLine();
        int n = brackets.length();
        int[][] dp = new int[n][n];
        int[][] bestSplit = new int[n][n];
        for (int j = 0; j < n; j++) {
            for (int i = j; i >= 0; i--) {
                if (i == j) {
                    dp[i][j] = 1;
                } else {
                    int min = 9999999;
                    int split = -1;
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
                        if (min > dp[i][k] + dp[k + 1][j]) {
                            min = dp[i][k] + dp[k + 1][j];
                            split = k;
                        }
                    }
                    dp[i][j] = min;
                    bestSplit[i][j] = split;
                }
            }
        }
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(bestSplit[i][j] + " ");
//            }
//            System.out.println();
//        }
        System.out.println(rightSeq(brackets, 0, n - 1, dp, bestSplit, new StringBuilder()).toString());
    }
}
