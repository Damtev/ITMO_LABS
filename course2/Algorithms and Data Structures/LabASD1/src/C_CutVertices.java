import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class C_CutVertices {

    private static int n, m;
    private static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    private static int[] used;
    private static int[] timeIn;
    private static int[] up;
    private static TreeSet<Integer> cutVertices = new TreeSet<>();

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
        }
    }

    private static void findCutVertices() {
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
        int count = 0;
        for (int u : graph.get(v)) {
            if (u == p) {
                continue;
            }
            if (used[u] == 1) {
                up[v] = Math.min(up[v], timeIn[u]);
            } else {
                ++count;
                dfs(u, v);
                up[v] = Math.min(up[v], up[u]);
                if (p != 0 && timeIn[v] <= up[u]) {
                    cutVertices.add(v);
                }
            }
            if (p == 0 && count >= 2) {
                cutVertices.add(v);
            }
        }
    }

    public static void main(String[] args) {
        getUndirectedGraph();
        findCutVertices();
        System.out.println(cutVertices.size());
        for (int cutVertex : cutVertices) {
            System.out.print(cutVertex + " ");
        }
    }
}
