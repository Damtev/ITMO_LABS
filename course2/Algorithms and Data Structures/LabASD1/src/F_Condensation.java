import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

public class F_Condensation {

    private static int n, m;
    private static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> invertedGraph = new ArrayList<>();
    private static HashSet<Pair<Integer, Integer>> uniqueEdges = new HashSet<>();
    private static int[] used;
    private static Stack<Integer> order = new Stack<>();
    private static int[] colors;

    private static void getDirectedGraph() {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        used = new int[n + 1];
        colors = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
            invertedGraph.add(new ArrayList<>());
            used[i] = 0;
            colors[i] = 0;
        }

        for (int i = 1; i <= m; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            if (from != to) {
                Pair<Integer, Integer> curEdge = new Pair<>(from, to);
                if (!uniqueEdges.contains(curEdge)) {
                    graph.get(from).add(to);
                    invertedGraph.get(to).add(from);
                    uniqueEdges.add(curEdge);
                }
            }
        }
    }

    private static void dfs1(int v) {
        used[v] = 1;
        for (int u : graph.get(v)) {
            if (used[u] == 0) {
                dfs1(u);
            }
        }
        order.push(v);
    }

    private static void dfs2(int v, int color) {
        colors[v] = color;
        for (int u : invertedGraph.get(v)) {
            if (colors[u] == 0) {
                dfs2(u, color);
            }
        }
    }

    private static int findStrongComponents() {
        for (int v = 1; v <= n; v++) {
            if (used[v] == 0) {
                dfs1(v);
            }
        }
        int maxColor = 1;
        while (!order.isEmpty()) {
            int v = order.pop();
            if (colors[v] == 0) {
                dfs2(v, maxColor);
                ++maxColor;
            }
        }
        return maxColor - 1;
    }

    public static void main(String[] args) {
        getDirectedGraph();
        findStrongComponents();
        int countEdges = 0;
        boolean[][] wasEdge = new boolean[n + 1][n + 1];
        for (int v = 1; v <= n; v++) {
            for (int u : graph.get(v)) {
                if (colors[u] != colors[v] && !wasEdge[ colors[u] ][ colors[v] ]) {
                    ++countEdges;
                    wasEdge[ colors[u] ][ colors[v] ] = true;
                }
            }
        }
        System.out.println(countEdges);
    }
}
