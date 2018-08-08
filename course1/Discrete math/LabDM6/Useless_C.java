import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class Useless_C {

    private static void dfs(HashMap<Character, HashSet<String>> rules, HashSet<Character> reachable,
                            char nonterminal, HashSet<Character> generatingNonterminals, HashSet<String> generatingRules) {
        reachable.add(nonterminal);
        if (generatingNonterminals.contains(nonterminal)) {
            HashSet<String> curRules = rules.get(nonterminal);
            if (curRules != null) {
                for (String rule : curRules) {
                    if (generatingRules.contains(rule)) {
                        for (char letter : rule.toCharArray()) {
                            if (Character.isUpperCase(letter) && !reachable.contains(letter)) {
                                dfs(rules, reachable, letter, generatingNonterminals, generatingRules);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("useless.in"));
             PrintWriter pr = new PrintWriter(new FileWriter("useless.out"))) {
            String[] s = br.readLine().split(" ");
            int n = Integer.parseInt(s[0]);
            char start = s[1].charAt(0);
            HashMap<Character, HashSet<String>> rules = new HashMap<>();
            TreeSet<Character> nonterminals = new TreeSet<>();
            nonterminals.add(start);
            for (int i = 0; i < n; i++) {
                s = br.readLine().split(" -> ");
                char nonterminal = s[0].charAt(0);
                nonterminals.add(nonterminal);
                String rule;
                if (s.length == 2) {
                    rule = s[1];
                } else {
                    rule = "";
                }
                for (char letter : rule.toCharArray()) {
                    if (Character.isUpperCase(letter)) {
                        nonterminals.add(letter);
                    }
                }
                if (rules.containsKey(nonterminal)) {
                    rules.get(nonterminal).add(rule);
                } else {
                    HashSet<String> temp = new HashSet<>();
                    temp.add(rule);
                    rules.put(nonterminal, temp);
                }
            }
            HashSet<Character> generatingNonterminals = new HashSet<>();
            HashSet<String> generatingRules = new HashSet<>();
            while (true) {
                HashSet<Character> curGenerating = new HashSet<>();
                for (char nonterminal : rules.keySet()) {
                    HashSet<String> curRules = rules.get(nonterminal);
                    for (String rule : curRules) {
                        boolean isGenerating = true;
                        if (!rule.equals("")) {
                            for (char letter : rule.toCharArray()) {
                                if (Character.isUpperCase(letter) && !generatingNonterminals.contains(letter)) {
                                    isGenerating = false;
                                    break;
                                }
                            }
                        }
                        if (isGenerating) {
                            curGenerating.add(nonterminal);
                            generatingRules.add(rule);
                        }
                    }
                }
                if (generatingNonterminals.equals(curGenerating)) {
                    break;
                } else {
                    generatingNonterminals.addAll(curGenerating);
                }
            }
            HashSet<Character> reachable = new HashSet<>();
            dfs(rules, reachable, start, generatingNonterminals, generatingRules);
            for (char nonterminal : nonterminals) {
                if (!generatingNonterminals.contains(nonterminal) || !reachable.contains(nonterminal)) {
                    pr.print(nonterminal + " ");
                }
            }
        }
    }
}
