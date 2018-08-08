import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class B {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        int[] seq = new int[n];
        int[] dp = new int[n + 1];
        int[] link = new int[n + 1]; //ссылки на индекс элемента в исходной последовательности, на который заканчивается возрастающая последовательность длины i
        int[] prev = new int[n];
        link[0] = -1; //нет возрастающей последовательности длины 0
        int answer = 0;
        String[] buf = in.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            seq[i] = Integer.parseInt(buf[i]);
        }
        dp[0] = Integer.MIN_VALUE / 2;
        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE / 2;
        }
        for (int i = 0; i < n; i++) {
//            int index = 0;
//            for (int j = 0; j <= n; j++) {
//                if (dp[j] >= seq[i]) {
//                    index = j;
//                    break;
//                }
//            }
            int temp = seq[i];
            int index = Arrays.binarySearch(dp, temp);
            if (index < 0) {
                index = -index - 1; //смотри формулу в бин.поиске
            }
            if (seq[i] > dp[index - 1] && seq[i] < dp[index]) {
                dp[index] = seq[i];
                answer = Math.max(index, answer);
                link[index] = i;
                prev[i] = link[index - 1];
            }
        }
        System.out.println(answer);
        ArrayList<Integer> inc = new ArrayList<>();
        int pos = link[answer];
        while (pos != -1) {
            inc.add(seq[pos]);
            pos = prev[pos];
        }
//        for (Integer temp: dp) {
//            System.out.print(temp + " ");
//        }
        for (int i = inc.size() - 1; i >= 0; i--) {
            System.out.print(inc.get(i) + " ");
        }
    }
}
