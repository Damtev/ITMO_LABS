import java.io.*;

public class Lottery_3 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("lottery.in")));
             PrintWriter pr = new PrintWriter("lottery.out")) {
            String[] vars = br.readLine().split(" ");
            int n = Integer.parseInt(vars[0]);
            int m = Integer.parseInt(vars[1]);
            int[] a = new int[m];
            int[] b = new int[m];
            double e = 0;
            double luckyMultiplier = 1;
            double unluckyMultiplier = 1;
            for (int i = 0; i < m; i++) {
                vars = br.readLine().split(" ");
                a[i] = Integer.parseInt(vars[0]);
                b[i] = Integer.parseInt(vars[1]);
            }
            for (int i = 0; i < m; i++) {
                luckyMultiplier /= (double) a[i];
                if (i < m - 1) {
                    unluckyMultiplier = (double) luckyMultiplier * (a[i + 1] - 1) / a[i + 1];
                    e += unluckyMultiplier * b[i];
                } else {
                    e += luckyMultiplier * b[i];
                }
            }
            pr.println(n - e);
        }
    }
}
