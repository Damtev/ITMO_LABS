import java.io.*;

public class T_6 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("vectors.in")));
             PrintWriter pr = new PrintWriter("vectors.out")) {
            int n = Integer.parseInt(br.readLine());
            boolean flag = true;
            int count = 0;
            String[] ans = new String[(int) Math.pow(2, n)];
            //StringBuilder[] sb = new StringBuilder[(int) Math.pow(2, n)];
            for (int i = 0; i < Math.pow(2, n); i++) {
                StringBuilder sb = new StringBuilder("");
                int temp = i;
                if (temp == 0) {
                    sb.append(0);
                }
                while (temp != 0) {
                    sb.append(temp % 2);
                    temp /= 2;
                }
                for (int j = sb.length(); j < n; j++) {
                    sb.append(0);
                }
                sb.reverse();
                for (int j = 0; j < sb.length() - 1; j++) {
                    if (sb.charAt(j) == '1' && sb.charAt(j + 1) == '1') {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    ans[count] = sb.toString();
                    count++;
                }
                flag = true;
            }
            pr.println(count);
            for (int i = 0; i < count; i++) {
                pr.println(ans[i]);
            }
        }
    }
}
