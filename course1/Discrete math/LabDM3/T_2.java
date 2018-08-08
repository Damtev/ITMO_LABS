import java.io.*;

public class T_2 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("gray.in")));
             PrintWriter pr = new PrintWriter("gray.out")) {
            int n = Integer.parseInt(br.readLine());
            for (int i = 0; i < Math.pow(2, n); i++) {
                int temp = i ^ (i / 2);
                StringBuilder gray  = new StringBuilder("");
                if (temp == 0) {
                    gray.append(0);
                }
                while (temp != 0) {
                    gray.append(temp % 2);
                    temp /= 2;
                }
                for (int j = gray.length(); j < n; j++) {
                    gray.append(0);
                }
                gray.reverse();
                pr.println(gray);
            }
        }
    }
}
