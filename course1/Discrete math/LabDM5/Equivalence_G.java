import javafx.util.Pair;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Equivalence_G {

    //Вершина 0 - дьявольская

    private static int[][] dka1;
    private static int[][] dka2;
    private static boolean[] terminals1;
    private static boolean[] terminals2;
    private static Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
    private static boolean used1[];
    private static boolean used2[];

    private static boolean equivalence() {
        queue.add(new Pair<>(1, 1));
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> poll = queue.poll();
            int u = poll.getKey();
            int v = poll.getValue();
            if (terminals1[u] != terminals2[v]) {
                return false;
            }
            used1[u] = (u != 0);
            used2[v] = (v != 0);
            if ((u == 0) && (v == 0)) {
                continue;
            }
            for (int i = 0; i < 26; i++) {
                if (!used1[dka1[u][i]] || !used2[dka2[v][i]]) {
                    queue.add(new Pair<>(dka1[u][i], dka2[v][i]));
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("equivalence.in")));
             PrintWriter pr = new PrintWriter("equivalence.out")) {
            String[] amounts = br.readLine().split(" ");
            int n1 = Integer.parseInt(amounts[0]);
            int m1 = Integer.parseInt(amounts[1]);
            int k1 = Integer.parseInt(amounts[2]);
            dka1 = new int[n1 + 1][26];
            terminals1 = new boolean[n1 + 1];
            used1 = new boolean[n1 + 1];
            String[] terms = br.readLine().split(" ");
            for (int i = 0; i < k1; i++) {
                terminals1[Integer.parseInt(terms[i])] = true;
            }
            for (int i = 0; i < m1; i++) {
                String[] transfer = br.readLine().split(" ");
                int from = Integer.parseInt(transfer[0]);
                int to = Integer.parseInt(transfer[1]);
                char symbol = transfer[2].charAt(0);
                dka1[from][symbol - 97] = to;
            }
            amounts = br.readLine().split(" ");
            int n2 = Integer.parseInt(amounts[0]);
            int m2 = Integer.parseInt(amounts[1]);
            int k2 = Integer.parseInt(amounts[2]);
            dka2 = new int[n2 + 1][26];
            terminals2 = new boolean[n2 + 1];
            used2 = new boolean[n2 + 1];
            terms = br.readLine().split(" ");
            for (int i = 0; i < k2; i++) {
                terminals2[Integer.parseInt(terms[i])] = true;
            }
            for (int i = 0; i < m2; i++) {
                String[] transfer = br.readLine().split(" ");
                int from = Integer.parseInt(transfer[0]);
                int to = Integer.parseInt(transfer[1]);
                char symbol = transfer[2].charAt(0);
                dka2[from][symbol - 97] = to;
            }
            pr.println((equivalence()) ? "YES" : "NO");
        }
    }
}
