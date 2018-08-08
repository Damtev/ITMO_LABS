import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class NKA_E {

    private static int n;
    private static final int cosntMod = 1000000000 + 7;
    private static boolean[] terminals;
    private static TreeMap<Integer, TreeMap<Character, HashSet<Integer>>> transition;
    private static DFAState[] dfa;

    private static class DFAState {

        int id;
        TreeMap<Character, Integer> transfers;
        boolean isTerminal;

        DFAState(int id) {
            this.id = id;
            transfers = new TreeMap<>();
        }

    }


    private static void thompson() {
        LinkedList<HashSet<Integer>> P = new LinkedList<>();
        HashSet<HashSet<Integer>> qd1 = new HashSet<>();
        HashSet<Integer> start = new HashSet<>();
        start.add(0);
        P.add(start);
        HashMap<HashSet<Integer>, TreeMap<Character, HashSet<Integer>>> newTransition = new HashMap<>();
        qd1.add(start);
        while (!P.isEmpty()) {
            HashSet<Integer> pd = P.poll();
            for (int i = 0; i < 26; i++) {
                HashSet<Integer> qd = new HashSet<>();
                char symbol = (char) (i + 97);
                for (int stateSet : pd) {
                    if (transition.get(stateSet) != null) {
                        if (transition.get(stateSet).get(symbol) != null) {
                            qd.addAll(transition.get(stateSet).get(symbol));
                            TreeMap<Character, HashSet<Integer>> curTrans = new TreeMap<>();
                            curTrans.put(symbol, qd);
                            if (newTransition.get(pd) == null) {
                                newTransition.put(pd, curTrans);
                            } else {
                                newTransition.get(pd).put(symbol, qd);
                            }
                        }
                    }
                }
                if (!qd1.contains(qd)) {
                    P.add(qd);
                    qd1.add(qd);
                }
            }
        }
        HashSet<HashSet<Integer>> td = new HashSet<>();
        for (HashSet<Integer> stateSet : qd1) {
            for (int state : stateSet) {
                if (terminals[state]) {
                    td.add(stateSet);
                }
            }
        }
        n = qd1.size();
        dfa = new DFAState[n];
        int index = 0;
        HashMap<HashSet<Integer>, Integer> rename = new HashMap<>();
        //переобозначение вершин
        for (HashSet<Integer> newState : qd1) {
            rename.put(newState, index);
            dfa[index] = new DFAState(index);
            ++index;
        }
        for (HashSet<Integer> newState : rename.keySet()) {
            index = rename.get(newState);
            if (td.contains(newState)) {
                dfa[index].isTerminal = true;
            }
            dfa[index].transfers = new TreeMap<>();
            if (newTransition.containsKey(newState)) {
                for (char symbol : newTransition.get(newState).keySet()) {
                    HashSet<Integer> nfaStates = newTransition.get(newState).get(symbol);
                    int nomer = rename.get(nfaStates);
                    dfa[index].transfers.put(symbol, nomer);
                }
            }
        }
    }

    private static long countWords(int l) {
        long[][] dp = new long[l][];
        dp[0] = new long[n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = dfa[i].isTerminal ? 1 : 0;
        }
        for (int i = 1; i < l; i++) {
            dp[i] = new long[n];
            for (int j = 0; j < n; j++) {
                long curSum = 0;
                for (char symbol : dfa[j].transfers.keySet()) {
                    curSum = (curSum + dp[i - 1][dfa[j].transfers.get(symbol)]) % cosntMod;
                }
                dp[i][j] = curSum;
            }
        }
        long answer = 0;
        for (char symbol : dfa[0].transfers.keySet()) {
            answer = (answer + dp[l - 1][dfa[0].transfers.get(symbol)]) % cosntMod;
        }
        return answer;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("problem5.in")));
             PrintWriter pr = new PrintWriter("problem5.out")) {
            String[] amounts = br.readLine().split(" ");
//            TreeMap<HashSet<Integer>, Pair<HashSet<Pair<Character, HashSet<Integer>>>, Boolean>> dfa
            n = Integer.parseInt(amounts[0]);
            int m = Integer.parseInt(amounts[1]);
            int k = Integer.parseInt(amounts[2]);
            int l = Integer.parseInt(amounts[3]);
            terminals = new boolean[n];
            String[] terms = br.readLine().split(" ");
            for (int i = 0; i < k; i++) {
                terminals[(Integer.parseInt(terms[i]) - 1)] = true;
            }
            transition = new TreeMap<>();
            for (int i = 0; i < m; i++) {
                String[] transfer = br.readLine().split(" ");
                int from = Integer.parseInt(transfer[0]) - 1;
                int to = Integer.parseInt(transfer[1]) - 1;
                char symbol = transfer[2].charAt(0);
                if (transition.get(from) != null) {
                    if (transition.get(from).get(symbol) != null) {
                        transition.get(from).get(symbol).add(to);
                    } else {
                        HashSet<Integer> inner = new HashSet<>();
                        inner.add(to);
                        transition.get(from).put(symbol, inner);
                    }
                } else {
                    TreeMap<Character, HashSet<Integer>> outer = new TreeMap<>();
                    HashSet<Integer> inner = new HashSet<>();
                    inner.add(to);
                    outer.put(symbol, inner);
                    transition.put(from, outer);
                }
            }
            thompson();
            pr.println(countWords(l));
        }
    }
}
