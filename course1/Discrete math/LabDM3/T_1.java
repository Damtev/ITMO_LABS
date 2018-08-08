import java.io.*;

public class T_1 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("allvectorsin.txt")));
             PrintWriter pr = new PrintWriter("allvectors.out")) {
            int n = Integer.parseInt(br.readLine());
            StringBuilder[] ans = new StringBuilder[(int) Math.pow(2, n)];
            for (int i = 0; i < Math.pow(2, n); i++) {
                ans[i] = new StringBuilder("");
                int temp = i;
                if (temp == 0) {
                    ans[i].append(0);
                }
                while (temp != 0) {
                    ans[i].append(temp % 2);
                    temp /= 2;
                }
                for (int j = ans[i].length(); j < n; j++) {
                    ans[i].append(0);
                }
                ans[i].reverse();
                pr.println(ans[i]);
            }
        }
    }
}
