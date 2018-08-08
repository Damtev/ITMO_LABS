import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class Automaton_A {

    private static class NFAState {
        char id;
        HashMap<Character, HashSet<NFAState>> transtions;

        NFAState(char id) {
            this.id = id;
            transtions = new HashMap<>();
        }
    }

    private static boolean check(char[] word, NFAState[] nfa, int start) {
        HashSet<NFAState> curStates = new HashSet<>();
        curStates.add(nfa[start]);
        for (char curLetter : word) {
            HashSet<NFAState> newStates = new HashSet<>();
            for (NFAState curState : curStates) {
                HashSet<NFAState> to = curState.transtions.get(curLetter);
                if (to != null) {
                    newStates.addAll(to);
                }
            }
            curStates = newStates;
        }
        for (NFAState finalState : curStates) {
            if (finalState.id == (char) 91) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader("automaton.in"));
            PrintWriter pr = new PrintWriter(new FileWriter("automaton.out"))) {
            String[] s = br.readLine().split(" ");
            int n = Integer.parseInt(s[0]);
            int start = s[1].charAt(0) - 65;
            NFAState[] nfa = new NFAState[27];
            for (int i = 0; i < 27; i++) {
                nfa[i] = new NFAState((char) (i + 65));
            }
            for (int i = 0; i < n; i++) {
                s = br.readLine().split(" -> ");
                int nonterminal = s[0].charAt(0) - 65;
                String rule = s[1];
                char symbol = rule.charAt(0);
                if (rule.length() == 2) {
                    if (!nfa[nonterminal].transtions.containsKey(symbol)) {
                        HashSet<NFAState> temp = new HashSet<>();
                        temp.add(nfa[rule.charAt(1) - 65]);
                        nfa[nonterminal].transtions.put(symbol, temp);
                    } else {
                        nfa[nonterminal].transtions.get(symbol).add(nfa[rule.charAt(1) - 65]);
                    }
                } else {
                    if (!nfa[nonterminal].transtions.containsKey(symbol)) {
                        HashSet<NFAState> temp = new HashSet<>();
                        temp.add(nfa[26]);
                        nfa[nonterminal].transtions.put(symbol, temp);
                    } else {
                        nfa[nonterminal].transtions.get(symbol).add(nfa[26]);
                    }
                }
            }
            int m = Integer.parseInt(br.readLine());
            for (int i = 0; i < m; i++) {
                char[] word = br.readLine().toCharArray();
                if (check(word, nfa, start)) {
                    pr.println("yes");
                } else {
                    pr.println("no");
                }
            }
        }
    }
}
