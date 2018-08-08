import java.io.*;
import java.util.ArrayList;

public class T_16 {

    static long factorial(long n) {
        long ans = 1;
        for (int i = 2; i <= n; i++) {
            ans *= i;
        }
        return ans;
    }

    static long choose(int begin, int end, int divider) {
        long ans = 1;
        for (int i = begin; i <= end; i++) {
            ans *= i;
        }
        ans /= factorial(divider);
        return ans;
    }

    static long choose2num(ArrayList<Integer> choose, int n, int k) {
        long num = 0;
        for (int i = 1; i <= k; i++) {
            for (int j = choose.get(i - 1) + 1; j <= choose.get(i) - 1; j++) {
                num += choose(n - j - k + i + 1, n - j, k - i);
            }
        }
        return num;
    }


    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("choose2num.in")));
             PrintWriter pr = new PrintWriter("choose2num.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            int k = Integer.parseInt(buf[1]);
            ArrayList<Integer> choose = new ArrayList<>();
            choose.add(0);
            buf = br.readLine().split(" ");
            for (int i = 0; i < k; i++) {
                choose.add(Integer.parseInt(buf[i]));
            }
            pr.print(choose2num(choose, n, k));
        }
    }
}
