import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Isomorph_F {

    static class DKAState {

        int id;
        TreeMap<Character, Integer> transfers;
        boolean terminal = false;

        DKAState(int id) {
            this.id = id;
            transfers = new TreeMap<>();
        }

    }

    private static boolean[] visited;
    static DKAState[] dka1;
    static DKAState[] dka2;

    static boolean dfs(DKAState u, DKAState v) {
        visited[u.id] = true;
        if (u.terminal != v.terminal) {
            return false;
        }
        boolean result = true;
        for (char key : u.transfers.keySet()) {
            try {
                DKAState t1 = dka1[u.transfers.get(key)];
                DKAState t2 = dka2[v.transfers.get(key)];
                if (!visited[t1.id]) {
                    result = result & dfs(t1, t2);
                }
            } catch (NullPointerException error) {
                return false;
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("isomorphism.in")));
             PrintWriter pr = new PrintWriter("isomorphism.out")) {
            String[] amounts = br.readLine().split(" ");
            int n1 = Integer.parseInt(amounts[0]);
            int m1 = Integer.parseInt(amounts[1]);
            int k1 = Integer.parseInt(amounts[2]);
            dka1 = new DKAState[n1 + 1];
            for (int i = 1; i <= n1; i++) {
                dka1[i] = new DKAState(i);
            }
            String[] terms = br.readLine().split(" ");
            for (int i = 0; i < k1; i++) {
                dka1[Integer.parseInt(terms[i])].terminal = true;
            }
            for (int i = 0; i < m1; i++) {
                String[] transfer = br.readLine().split(" ");
                int from = Integer.parseInt(transfer[0]);
                int to = Integer.parseInt(transfer[1]);
                char symbol = transfer[2].charAt(0);
                dka1[from].transfers.put(symbol, to);
            }
            amounts = br.readLine().split(" ");
            int n2 = Integer.parseInt(amounts[0]);
            int m2 = Integer.parseInt(amounts[1]);
            int k2 = Integer.parseInt(amounts[2]);
            dka2 = new DKAState[n2 + 1];
            for (int i = 1; i <= n2; i++) {
                dka2[i] = new DKAState(i);
            }
            terms = br.readLine().split(" ");
            for (int i = 0; i < k2; i++) {
                dka2[Integer.parseInt(terms[i])].terminal = true;
            }
            for (int i = 0; i < m2; i++) {
                String[] transfer = br.readLine().split(" ");
                int from = Integer.parseInt(transfer[0]);
                int to = Integer.parseInt(transfer[1]);
                char symbol = transfer[2].charAt(0);
                dka2[from].transfers.put(symbol, to);
            }
            if (n1 != n2) {
                pr.println("NO");
            } else {
                visited = new boolean[n1 + 1];
                boolean answer = dfs(dka1[1], dka2[1]);
                pr.println((answer) ? "YES" : "NO");
            }
        }
    }
}
