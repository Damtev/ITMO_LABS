import java.util.Scanner;

public class Choose {

    static long factorial(long n) {
        long ans = 1;
        for (int i = 2; i <= n; i++) {
            ans *= i;
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] buf = in.nextLine().split(" ");
        int k = Integer.parseInt(buf[0]);
        int n = Integer.parseInt(buf[1]);
        long ans = 1;
        for (int i = n - k + 1; i <= n; i++) {
            ans *= i;
        }
        System.out.println(ans / factorial(k));
    }
}
