import java.io.*;
import java.util.ArrayList;

public class T_8 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("choose.in")));
             PrintWriter pr = new PrintWriter("choose.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            int k = Integer.parseInt(buf[1]);
            int[] digit = new int[k + 1];
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
            }
        }
    }
}
