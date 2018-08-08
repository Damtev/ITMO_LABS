import java.io.*;

public class T_5 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("telemetry.in")));
             PrintWriter pr = new PrintWriter("telemetry.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            int k = Integer.parseInt(buf[1]);
            int[] digit = new int[n];
            int[] multi = new int[n];
            int[] flag = new int[n];
            for (int j = 0; j < n; j++) {
                digit[j] = 0;
                multi[j] = 1;
                flag[j] = 1;
            }
            for (int i = 0; i < Math.pow(k, n); i++) {
                for (int j = 0; j < n; j++) {
                    pr.print(digit[j]);
                }
                pr.println();
                for (int j = 0; j < n; j++) {
                    if (flag[j] == (int) Math.pow(k, j)) {//Возможно, надо поменять на k
                        digit[j] += 1 * multi[j];
                        if (digit[j] == k) {
                            digit[j] = k - 1;
                            multi[j] = -1;
                        }
                        if (digit[j] == -1) {
                            digit[j] = 0;
                            multi[j] = 1;
                        }
                        flag[j] = 1;
                    }
                    else {
                        flag[j]++;
                    }
                }
            }
        }
    }
}
