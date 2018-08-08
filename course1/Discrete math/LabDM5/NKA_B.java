import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class NKA_B {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("problem2.in")));
             PrintWriter pr = new PrintWriter("problem2.out")) {
            String word = br.readLine();
            String[] amounts = br.readLine().split(" ");
            int n = Integer.parseInt(amounts[0]);
            int m = Integer.parseInt(amounts[1]);
            int k = Integer.parseInt(amounts[2]);
            int[][][] nka = new int[n + 1][26][n + 1];
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
                nka[from][symbol - 97][to] = 1;
            }
            char[] symbols = word.toCharArray();
            boolean accepted = true;
            TreeSet<Integer> curStates = new TreeSet<>();
            curStates.add(1);
            for (char symbol : symbols) {
                TreeSet<Integer> temp = new TreeSet<>();
                for (int state : curStates) {
                    for (int i = 1; i <= n; i++) {
                        if (nka[state][symbol - 97][i] == 1) {
                            temp.add(i);
                        }
                    }
                }
                curStates = temp;
            }
            boolean definite = false;
            if (accepted) {
                for (int cur : curStates) {
                    if (terminals[cur]) {
                        definite = true;
                        pr.println("Accepts");
                        break;
                    }
                }
            } else {
                definite = true;
                pr.println("Rejects");
            }
            if (!definite) {
                pr.println("Rejects");
            }
        }
    }
}
