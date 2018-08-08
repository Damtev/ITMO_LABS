import java.io.*;
import java.util.ArrayList;

public class T_14 {

    static long factorial(long n) {
        long ans = 1;
        for (int i = 2; i <= n; i++) {
            ans *= i;
        }
        return ans;
    }

    static long perm2num(ArrayList<Integer> permutation, int n) {
        long num = 0;
        boolean[] was = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= permutation.get(i - 1) - 1; j++) {
                if (was[j] == false) {
                    num += factorial(n - i);
                }
            }
            was[permutation.get(i - 1)] = true;
        }
        return num;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("perm2num.in")));
             PrintWriter pr = new PrintWriter("perm2num.out")) {
            int n = Integer.parseInt(br.readLine());
            String[] buf = br.readLine().split(" ");
            ArrayList<Integer> permutation = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                permutation.add(Integer.parseInt(buf[i]));
            }
            pr.println(perm2num(permutation, n));
        }
    }
}
