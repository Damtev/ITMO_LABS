import java.io.*;
import java.util.ArrayList;

public class T_13 {

    static long factorial(long n) {
        long ans = 1;
        for (int i = 2; i <= n; i++) {
            ans *= i;
        }
        return ans;
    }

    static ArrayList<Integer> num2perm(int n, long k) {
        ArrayList<Integer> permutation = new ArrayList<>();
        boolean[] was = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (was[j]) {
                    continue;
                }
                else {
                    if (k >= factorial(n - i)) {
                        k -= factorial(n - i);
                        continue;
                    }
                    else {
                        permutation.add(j);
                        was[j] = true;
                        break;
                    }
                }
            }
        }
        return permutation;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("num2perm.in")));
             PrintWriter pr = new PrintWriter("num2perm.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            long k = Long.parseLong(buf[1]);
            ArrayList<Integer> ans = num2perm(n, k);
            for (int i = 0; i < ans.size(); i++) {
                pr.print(ans.get(i));
                if (i != ans.size() - 1) {
                    pr.print(" ");
                }
            }
        }
    }
}
