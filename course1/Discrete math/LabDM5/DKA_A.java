import java.io.*;

public class DKA_A {

    public static void main(String[] args) throws IOException{
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("problem1.in")));
             PrintWriter pr = new PrintWriter("problem1.out")) {
            String word = br.readLine();
            String[] amounts = br.readLine().split(" ");
            int n = Integer.parseInt(amounts[0]);
            int m = Integer.parseInt(amounts[1]);
            int k = Integer.parseInt(amounts[2]);
            int[][] dka = new int[n + 1][26];
            boolean[] terminals = new boolean[n + 1];
            String[] terms = br.readLine().split(" ");
            for (int i = 0; i < k; i++) {
                terminals[Integer.parseInt(terms[i])] = true;
            }
            for (int i = 0; i < m; i++) {
                String[] transfer = br.readLine().split(" ");
                int from = Integer.parseInt(transfer[0]);
                int to = Integer.parseInt(transfer[1]);
                char symbol = transfer[2].charAt(0);
                dka[from][symbol - 97] = to;
            }
            char[] symbols = word.toCharArray();
            int cur = 1;
            boolean accepted = true;
            for (char symbol : symbols) {
                if (dka[cur][symbol - 97] != 0) {
                    cur = dka[cur][symbol - 97];
                } else {
                    accepted = false;
                    break;
                }
            }
            if (accepted) {
                if (terminals[cur]) {
                    pr.println("Accepts");
                } else {
                    pr.println("Rejects");
                }
            } else {
                pr.println("Rejects");
            }
        }
    }
}
