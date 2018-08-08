import java.io.*;
import java.util.TreeMap;

public class DKA_D {

    private static int n;
    private static DKAState[] dka;
    private static final int cosntMod = 1000000000 + 7;

    private static class DKAState {

        int id;
        TreeMap<Character, Integer> transfers;
        boolean isTerminal;

        DKAState(int id) {
            this.id = id;
            transfers = new TreeMap<>();
        }

    }

    private static long countWords(int l) {
        long[][] dp = new long[l][];
        dp[0] = new long[n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = dka[i].isTerminal ? 1 : 0;
        }
        for (int i = 1; i < l; i++) {
            dp[i] = new long[n];
            for (int j = 0; j < n; j++) {
                long curSum = 0;
                for (char symbol : dka[j].transfers.keySet()) {
                    curSum = (curSum + dp[i - 1][dka[j].transfers.get(symbol)]) % cosntMod;
                }
                dp[i][j] = curSum;
            }
        }
        long answer = 0;
        for (char symbol : dka[0].transfers.keySet()) {
            answer = (answer + dp[l - 1][dka[0].transfers.get(symbol)]) % cosntMod;
        }
        return answer;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("problem4.in")));
             PrintWriter pr = new PrintWriter("problem4.out")) {
            String[] amounts = br.readLine().split(" ");
            n = Integer.parseInt(amounts[0]);
            int m = Integer.parseInt(amounts[1]);
            int k = Integer.parseInt(amounts[2]);
            int l = Integer.parseInt(amounts[3]);
            dka = new DKAState[n];
            for (int i = 0; i < n; i++) {
                dka[i] = new DKAState(i);
            }
            String[] terms = br.readLine().split(" ");
            for (int i = 0; i < k; i++) {
                dka[Integer.parseInt(terms[i]) - 1].isTerminal = true;
            }
            for (int i = 0; i < m; i++) {
                String[] transfer = br.readLine().split(" ");
                int from = Integer.parseInt(transfer[0]) - 1;
                int to = Integer.parseInt(transfer[1]) - 1;
                char symbol = transfer[2].charAt(0);
                dka[from].transfers.put(symbol, to);
            }
            pr.println(countWords(l));
        }
    }
}
