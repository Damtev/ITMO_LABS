import java.io.*;

public class K {

    static boolean existTrans(int n, String p1, String p2) {
        StringBuilder sb = new StringBuilder("");
        for (int i = p1.length(); i < n; i++) {
            sb.append(0);
        }
        p1 = sb.append(p1).toString();
        char[] pr1 = p1.toCharArray();
        sb = new StringBuilder("");
        for (int i = p2.length(); i < n; i++) {
            sb.append(0);
        }
        p2 = sb.append(p2).toString();
        char[] pr2 = p2.toCharArray();
        for (int i = 0; i < n - 1; i++) {
            if (pr1[i] == pr2[i] && pr1[i + 1] == pr2[i + 1] && pr1[i] == pr2[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("nice.in")));
             PrintWriter pr = new PrintWriter("nice.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Math.min(Integer.parseInt(buf[0]), Integer.parseInt(buf[1]));
            int m = Math.max(Integer.parseInt(buf[0]), Integer.parseInt(buf[1]));
            int[] old = new int[1 << n];
            int[] cur = new int[1 << n];
            long answer = 0;
//            boolean[][] dp = new boolean[1 << n][1 << n];
//            int[][] count = new int[m][1 << n];
            for (int j = 0; j < 1 << n; j++) {
//                count[0][j] = 1;
                old[j] = 1;
            }
//            count[0][0] = 1;
//            for (int i = 0; i < 1 << n; i++) {
//                for (int j = 0; j < 1 << n; j++) {
//                    if (existTrans(n, Integer.toString(i, 2), Integer.toString(j, 2))) {
//                        dp[i][j] = true;
//                    }
//                    else {
//                        dp[i][j] = false;
//                    }
//                }
//            }
            for (int k = 1; k < m; k++) {
                for (int i = 0; i < 1 << n; i++) {
                    for (int j = 0; j < 1 << n; j++) {
                        if (existTrans(n, Integer.toString(i, 2), Integer.toString(j, 2))) {
//                            count[k][i] += count[k - 1][j];
                            cur[i] += old[j];
                        }
//                        count[k][i] += count[k - 1][j] * dp[j][i];
                    }
                }
                old = cur;
                cur = new int[1 << n];
            }
            for (int j = 0; j < 1 << n; j++) {
//                answer += count[m - 1][j];
                answer += old[j];
            }
            pr.println(answer);
        }
    }
}
