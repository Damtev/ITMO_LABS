import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class Epsilon_B {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("epsilon.in"));
             PrintWriter pr = new PrintWriter(new FileWriter("epsilon.out"))) {
            String[] s = br.readLine().split(" ");
            int n = Integer.parseInt(s[0]);
            HashMap<Character, HashSet<String>> rules = new HashMap<>();
            for (int i = 0; i < n; i++) {
                s = br.readLine().split(" -> ");
                char nonterminal = s[0].charAt(0);
                String rule;
                if (s.length == 2) {
                    rule = s[1];
                } else {
                    rule = "";
                }
                if (rules.containsKey(nonterminal)) {
                    rules.get(nonterminal).add(rule);
                } else {
                    HashSet<String> temp = new HashSet<>();
                    temp.add(rule);
                    rules.put(nonterminal, temp);
                }
            }
            HashSet<Character> epsilon = new HashSet<>();
            while (true) {
                HashSet<Character> curEpsilon = new HashSet<>();
                for (char nonterminal : rules.keySet()) {
                    HashSet<String> curRules = rules.get(nonterminal);
                    for (String rule : curRules) {
                        boolean toEpsilon = true;
                        if (!rule.equals("")) {
                            for (char letter : rule.toCharArray()) {
                                if (!epsilon.contains(letter)) {
                                    toEpsilon = false;
                                    break;
                                }
                            }
                        }
                        if (toEpsilon) {
                            curEpsilon.add(nonterminal);
                        }
                    }
                }
                if (epsilon.equals(curEpsilon)) {
                    break;
                } else {
                    epsilon.addAll(curEpsilon);
                }
            }
            TreeSet<Character> answer = new TreeSet<>();
            answer.addAll(epsilon);
            for (char epsilonNonterminal : answer) {
                pr.println(epsilonNonterminal);
            }
        }
    }
}
