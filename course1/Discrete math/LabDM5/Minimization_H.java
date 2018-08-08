import javafx.util.Pair;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Minimization_H {


    private static int[][] dka; //откуда, как
    private static boolean[] terminals;

    private static int minNumberStates = 0;
    private static int minNumberTerminals = 0;
    private static int minNumberTransfers = 0;
    private static int[][] minDKA;
    private static boolean[] minTerminals;

    private static boolean[][] buildTable(int n, boolean[] isTerminal, boolean[][][] reversedDKA) {
        Queue<Pair<Integer, Integer>> Q = new LinkedList<>();
        boolean[][] marked = new boolean[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (!marked[i][j] && (isTerminal[i] != isTerminal[j])) {
                    marked[i][j] = marked[j][i] = true;
                    Q.add(new Pair<>(i, j));
                }
            }
        }
        while (!Q.isEmpty()) {
            Pair<Integer, Integer> curPair = Q.poll();
            int u = curPair.getKey();
            int v = curPair.getValue();
            for (int c = 0; c < 26; c++) {
                for (int r = 1; r <= n; r++) { //r - откуда
                    if (reversedDKA[u][c][r]) {
                        for (int s = 1; s <= n; s++) {
                            if (reversedDKA[v][c][s]) { //s - откуда
                                if (!marked[r][s]) {
                                    marked[r][s] = marked[s][r] = true;
                                    Q.add(new Pair<>(r, s));
                                }
                            }
                        }
                    }
                }
            }
        }
        return marked;
    }

    private static void buildDFA(int[] component) {
        HashSet<Integer> wasComponent = new HashSet<>();
        int componentsCount = 0;
        for (int aComponent : component) {
            if (aComponent > 0) {
                if (!wasComponent.contains(aComponent)) {
                    wasComponent.add(aComponent);
                    componentsCount++;
                }
            }
        }
//        ArrayList<HashSet<Integer>> components = new ArrayList<>();
        HashSet<Integer>[] components = new HashSet[componentsCount + 1];
        /*for (int i = 1; i < component.length; i++) {
            if (component[i] > 0) {
                if (*//*component[i] != component[i - 1]*//*!wasComponent.contains(component[i])) {
                    HashSet<Integer> curComponent = new HashSet<>();
                    curComponent.add(i);
                    components.add(curComponent);
                    wasComponent.add(component[i]);
                    minNumberStates++;
                } else {
//                    components.get(components.size() - 1).add(i);
                }
            }
        }*/
        for (int i = 1; i <= componentsCount; i++) {
            components[i] = new HashSet<>();
        }
        minNumberStates = componentsCount;
        for (int i = 1; i < component.length; i++) {
            if (component[i] > 0) {
                components[component[i]].add(i);
            }
        }
        /*HashMap<HashSet<Integer>, Integer> rename = new HashMap<>();
        //переобозначение вершин
        int index = 1;
        for (HashSet<Integer> newState : components) {
            rename.put(newState, index);
            ++index;
        }*/
        minDKA = new int[minNumberStates + 1][26];
        minTerminals = new boolean[minNumberStates + 1];
        for (int newState = 1; newState <= minNumberStates; newState++) {
//            HashSet<Integer> curComponent = components.get(newState - 1);
            HashSet<Integer> curComponent = components[newState];
            for (int prevState : curComponent) {
                if (terminals[prevState]) {
                    minTerminals[newState] = true;
//                    minNumberTerminals++;
                }
                for (int symbol = 0; symbol < 26; symbol++) {
                    if (dka[prevState][symbol] > 0) {
                        minDKA[newState][symbol] = component[dka[prevState][symbol]];
//                        minNumberTransfers++;
                    }
                }
            }
        }
    }

    private static void reverse(boolean[][][] reversedDKA) {
        //строим обратные ребра
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(1);
        while (!queue.isEmpty()) {
            int curState = queue.poll();
            int[] curTransfers = dka[curState];
            for (int i = 0; i < 26; i++) {
                if (curTransfers[i] != 0) {
                    if (!reversedDKA[curTransfers[i]][i][curState]) {
                        queue.add(curTransfers[i]);
                        reversedDKA[curTransfers[i]][i][curState] = true;
                    }
                }
            }
        }
    }

    private static void minimization(int n, boolean[] isTerminal, int[][] dka) {

        //строим обратные ребра
        boolean[][][] reversedDKA = new boolean[n + 1][26][n + 1]; //куда, как, откуда - есть ли переход
        reverse(reversedDKA);

        //помечаем недостижимые состояния
        boolean reachable[] = new boolean[n + 1];
        boolean flag = true;
        reachable[1] = true;
        boolean visited[] = new boolean[n + 1];
        while (flag) {
            flag = false;
            for (int state = 1; state <= n; state++) {
                if (reachable[state] && !visited[state]) {
                    visited[state] = true;
                    flag = true;
                    for (int symbol = 0; symbol < 26; symbol++) {
                        reachable[dka[state][symbol]] = /*true*/(dka[state][symbol] != 0);
                    }
                }
            }
        }

        //помечаем состояния, из которых достижимы терминальные(полезные состояния)
        boolean[] canReachTerminals = new boolean[n + 1];
        Queue<Integer> queue1 = new LinkedList<>();
        visited = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            if (terminals[i]) {
                queue1.add(i);
                visited[i] = true;
            }
        }
        while (!queue1.isEmpty()) {
            int state = queue1.poll();
            canReachTerminals[state] = true;
            boolean[][] toTerminals = reversedDKA[state];
            for (int j = 0; j < 26; j++) {
                for (int k = 1; k <= n; k++) {
                    if (toTerminals[j][k]) {
                        if (!visited[k]) {
                            visited[k] = true;
                            queue1.add(k);
                        }
                    }
                }
            }
        }

        //объединяем достижимые и полезные
        boolean[] important = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            important[i] = (reachable[i] & canReachTerminals[i]);
        }

        //строим массив достижимости
        boolean[][] marked = buildTable(n, terminals, reversedDKA);

        //разбиваем на компоненты
        int[] component = new int[n + 1];
        /*for (int i = 0; i <= n; i++) {
            component[i] = -1;
        }*/
        for (int i = 1; i <= n; i++) {
            if (!marked[1][i] && important[i]) { //TODO: второе условие добавил
                component[i] = 1;
            }
        }
        int componentsCount = 0;
        for (int i = 2; i <= n; i++) {
            if (/*reachable[i]*/important[i]) {
                if (component[i] == 0) {
                    componentsCount++;
                    component[i] = componentsCount + 1;
                    for (int j = i + 1; j <= n; j++) {
                        if (!marked[i][j] && important[j]) { //TODO: второе условие добавил
                            component[j] = componentsCount + 1;
                        }
                    }
                }
            }
        }

        //строим автомат
        buildDFA(component);
    }

    private static void brzozowski(int n, boolean[] isTerminal, int[][] dka) {
        //строим обратные ребра
        boolean[][][] reversedDKA = new boolean[n + 1][26][n + 1]; //куда, как, откуда - есть ли переход
        reverse(reversedDKA);
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("minimization.in")));
             PrintWriter pr = new PrintWriter("minimization.out")) {
            String[] amounts = br.readLine().split(" ");
            int numberStates = Integer.parseInt(amounts[0]);
            int numberTransfers = Integer.parseInt(amounts[1]);
            int numberTerminals = Integer.parseInt(amounts[2]);
            dka = new int[numberStates + 1][26];
            terminals = new boolean[numberStates + 1];
            String[] terms = br.readLine().split(" ");
            for (int i = 0; i < numberTerminals; i++) {
                terminals[Integer.parseInt(terms[i])] = true;
            }
            for (int i = 0; i < numberTransfers; i++) {
                String[] transfer = br.readLine().split(" ");
                int from = Integer.parseInt(transfer[0]);
                int to = Integer.parseInt(transfer[1]);
                char symbol = transfer[2].charAt(0);
                dka[from][symbol - 97] = to;
            }
            minimization(numberStates, terminals, dka);
//            brzozowski(numberStates, terminals, dka);
            for (int state = 1; state <= minNumberStates; state++) {
                if (minTerminals[state]) {
                    minNumberTerminals++;
                }
                for (int symbol = 0; symbol < 26; symbol++) {
                    if (minDKA[state][symbol] > 0) {
                        minNumberTransfers++;
                    }
                }
            }
            pr.println(minNumberStates + " " + minNumberTransfers + " " + minNumberTerminals);
            for (int i = 1; i <= minNumberStates; i++) {
                if (minTerminals[i]) {
                    pr.print(i + " ");
                }
            }
            pr.println();
            /*if (minNumberTerminals > 0) {
                pr.println();
            }*/
            for (int state = 1; state <= minNumberStates; state++) {
                for (int symbol = 0; symbol < 26; symbol++) {
                    if (minDKA[state][symbol] > 0) {
                        pr.println(state + " " + minDKA[state][symbol] + " " + (char) (symbol + 97));
                    }
                }
            }
        }
//        BigInteger b1 = new BigInteger("123456789876543212345678987654321");
//        BigInteger b2 = new BigInteger("-9876543212345678987654321");
//        System.out.println(b1.add(b2));
//        System.out.println(b1.subtract(b2));
//        System.out.println(b2.subtract(b1));
//        System.out.println(b1.multiply(b2));
    }
}
