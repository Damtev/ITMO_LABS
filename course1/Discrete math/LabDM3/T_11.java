import java.io.*;

public class T_11 {

    static void generate(int n, int i, StringBuilder sb, PrintWriter pr) throws IOException {
        pr.println(sb.toString());
        StringBuilder temp = new StringBuilder("");
        for (int j = i + 1; j <= n; j++) {
            temp.append(sb);
            generate(n, j, temp.append(" " + j), pr);
            temp = new StringBuilder("");
        }
        return;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("subsets.in")));
             PrintWriter pr = new PrintWriter("subsets.out")) {
            int n = Integer.parseInt(br.readLine());
            pr.println();
            for (int i = 1; i <= n; i++) {
                StringBuilder temp = new StringBuilder("");
                temp.append(i);
                generate(n, i, temp, pr);
            }
        }
    }
}
