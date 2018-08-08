import java.io.*;

public class T_3 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("antigray.in")));
             PrintWriter pr = new PrintWriter("antigray.out")) {
            int n = Integer.parseInt(br.readLine());
            int wtf = 0;
            for (int i = 0; i < Math.pow(3, n - 1); i++) {
                int temp = i;
                int[] antigray = new int[n];
                if (temp == 0) {
                    for (int j = 0; j < n; j++) {
                        antigray[j] = 0;
                    }
                }
                else {
                    int k = 0;
                    while (temp != 0) {
                        antigray[n - k - 1] = temp % 3;
                        k++;
                        temp /= 3;
                    }
                    for (int z = k; z < n; z++) {
                        antigray[n - z - 1] = 0;
                    }
                }
                wtf++;
                for (int j = 0; j < 3; j++) {
                    for (int z = 0; z < n; z++) {
                        pr.print(antigray[z]);
                    }
                    pr.println();
                    for (int z = 0; z < n; z++) {
                        antigray[z] = (antigray[z] + 1) % 3;
                        wtf++;
                    }
                }
            }
        }
    }
}
