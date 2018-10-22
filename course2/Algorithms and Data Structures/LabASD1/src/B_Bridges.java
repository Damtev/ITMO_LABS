import javafx.util.Pair;

import java.util.*;

public class B_Bridges {

    private static int n, m;
    private static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    private static int[] used;
    private static int[] timeIn;
    private static int[] up;
    private static HashMap<Pair<Integer, Integer>, Integer> edgeNumber = new HashMap<>();
    private static TreeSet<Integer> bridges = new TreeSet<>();

    private static void getUndirectedGraph() {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        used = new int[n + 1];
        timeIn = new int[n + 1];
        up = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
            used[i] = 0;
        }

        for (int i = 1; i <= m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
            edgeNumber.put(new Pair<>(u, v), i);
        }
    }

    private static void findBridges() {
        for (int v = 1; v <= n; v++) {
            if (used[v] == 0) {
                dfs(v, 0);
            }
        }
    }

    private static int time = 0;

    private static void dfs(int v, int p) {
        used[v] = 1;
        ++time;
        timeIn[v] = time;
        up[v] = time;
        for (int u : graph.get(v)) {
            if (u == p) {
                continue;
            }
            if (used[u] == 1) {
                up[v] = Math.min(up[v], timeIn[u]);
            } else {
                dfs(u, v);
                up[v] = Math.min(up[v], up[u]);
                if (timeIn[v] < up[u]) {
                    Pair<Integer, Integer> edge = new Pair<>(u, v);
                    if (!edgeNumber.containsKey(edge)) {
                        edge = new Pair<>(v, u);
                    }
                    bridges.add(edgeNumber.get(edge));
                }
            }
        }
    }

    public static void main(String[] args) {
        getUndirectedGraph();
        findBridges();
        System.out.println(bridges.size());
        for (int bridge : bridges) {
            System.out.print(bridge + " ");
        }
    }
}