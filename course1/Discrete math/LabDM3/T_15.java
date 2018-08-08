import java.io.*;
import java.util.ArrayList;

public class T_15 {

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

    static ArrayList<Integer> num2choose(int n, int k, long m) {
        ArrayList<Integer> choose = new ArrayList<>();
        int nextNumber = 1;
        while (k > 0) {
            long temp = choose(n - k + 1, n - 1, k - 1);
            if (m < temp) {
                choose.add(nextNumber);
                k--;
            } else {
                m -= temp;
            }
            n--;
            nextNumber++;
        }
        return choose;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("num2choose.in")));
             PrintWriter pr = new PrintWriter("num2choose.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            int k = Integer.parseInt(buf[1]);
            long m = Long.parseLong(buf[2]);
            for (Integer number: num2choose(n, k ,m)) {
                pr.print(number + " ");
            }
        }
    }
}
