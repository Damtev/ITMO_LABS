import java.io.*;
import java.util.Arrays;

public class T_25 {

    static int[] nextchoose(int n, int k, int[] choose) {
        for (int j = k - 1; j >= 0; j--) {
            if (choose[j + 1] - choose[j] >= 2) {
                choose[j]++;
                for (int l = j + 1; l < k; l++) {
                    choose[l] = choose[l - 1] + 1;;
                }
                break;
            }
        }
        return choose;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("nextchoose.in")));
             PrintWriter pr = new PrintWriter("nextchoose.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            int k = Integer.parseInt(buf[1]);
            buf = br.readLine().split(" ");
            int[] choose = new int[k + 1];
            int[] origin = new int[k + 1];;
            for (int i = 0; i < k; i++) {
                choose[i] = Integer.parseInt(buf[i]);
                origin[i] = Integer.parseInt(buf[i]);
            }
            choose[k] = n + 1;
            origin[k] = n + 1;
            choose = nextchoose(n, k, choose);
            if (Arrays.equals(origin, choose)) {
                pr.print("-1");
            }
            else {
                for (int j = 0; j < k - 1; j++) {
                    pr.print(choose[j] + " ");
                }
                pr.print(choose[k - 1]);
            }
            /*int[] digit = new int[k + 1];
            for (int i = 0; i < k; i++) {
                digit[i] = i + 1;
            }
            digit[k] = n + 1;
            while (true) {
                boolean last = true;
                for (int j = 0; j < k - 1; j++) {
                    pr.print(digit[j] + " ");
                }
                pr.print(digit[k - 1]);
                pr.println();
                for (int j = k - 1; j >= 0; j--) {
                    if (Math.abs(digit[j] - digit[j + 1]) >= 2) {
                        last = false;
                        digit[j]++;
                        int temp = digit[j] + 1;
                        for (int l = j + 1; l < k; l++) {
                            digit[l] = temp;
                            temp++;
                        }
                        break;
                    }
                }
                if (last) {
                    break;
                }
            }*/
        }
    }
}
