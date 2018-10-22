import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class K_TwoChinese {

    private static int numberComponents;
    private static int[] used;
    private static Stack<Integer> order = new Stack<>();
    private static int[] colors;
    private static int root;

    private static void dfs1(ArrayList<ArrayList <Pair<Integer, Integer> >> graph, int v) {
        used[v] = 1;
        for (Pair<Integer, Integer> edge : graph.get(v)) {
            int u = edge.getKey();
            if (used[u] == 0) {
                dfs1(graph, u);
            }
        }
        order.push(v);
    }

    private static void dfs2(ArrayList<ArrayList <Pair<Integer, Integer> >> invertedGraph, int v, int color) {
        colors[v] = color;
        for (Pair<Integer, Integer> edge : invertedGraph.get(v)) {
            int u = edge.getKey();
            if (colors[u] == 0) {
                dfs2(invertedGraph, u, color);
            }
        }
    }

    private static int findStrongComponents(ArrayList<ArrayList <Pair<Integer, Integer> >> graph,
                          ArrayList<ArrayList <Pair<Integer, Integer> >> invertedGraph) {
        for (int i = 1; i <= numberComponents; i++) {
            used[i] = 0;
            colors[i] = 0;
        }
        for (int v = 1; v <= numberComponents; v++) {
            if (used[v] == 0) {
                dfs1(graph, v);
            }
        }
        int maxColor = 1;
        while (!order.isEmpty()) {
            int v = order.pop();
            if (colors[v] == 0) {
                dfs2(invertedGraph, v, maxColor);
                ++maxColor;
            }
        }
        return maxColor - 1;
    }

    private static int canReachAll(ArrayList<ArrayList <Pair<Integer, Integer> >> graph) {
        for (int i = 1; i <= numberComponents; i++) {
            used[i] = 0;
        }
        dfs1(graph, root);
        order = new Stack<>();
        for (int v = 1; v <= numberComponents; v++) {
            if (used[v] == 0) {
                return 0;
            }
        }
        return 1;
    }

    private static long findMst(ArrayList<ArrayList <Pair<Integer, Integer> >> graph,
                                ArrayList<ArrayList <Pair<Integer, Integer> >> invertedGraph) {
        long mst = 0;
        int[] minEdges = new int[numberComponents + 1];
        for (int i = 1; i <= numberComponents; i++) {
            minEdges[i] = Integer.MAX_VALUE;
        }
        for (ArrayList<Pair<Integer, Integer>> edges : graph) {
            for (Pair<Integer, Integer> edge : edges) {
                int to = edge.getKey();
                int weight = edge.getValue();
                minEdges[to] = Math.min(weight, minEdges[to]);
            }
        }
        for (int v = 1; v <= numberComponents; v++) {
            if (v == root) {
                continue;
            }
            mst += minEdges[v];
        }

        ArrayList <ArrayList <Pair<Integer, Integer> >> zeroEdges = new ArrayList<>();
        ArrayList <ArrayList <Pair<Integer, Integer> >> zeroInvertedEdges = new ArrayList<>();
        for (int i = 0; i <= numberComponents; i++) {
            zeroEdges.add(new ArrayList<>());
            zeroInvertedEdges.add(new ArrayList<>());
        }
        for (int from = 1; from <= numberComponents; ++from) {
            ArrayList<Pair<Integer, Integer>> edges = graph.get(from);
            for (Pair<Integer, Integer> edge : edges) {
                int weight = edge.getValue();
                int to = edge.getKey();
                if (weight == minEdges[to]) {
                    zeroEdges.get(from).add(new Pair<>(to, 0));
                    zeroInvertedEdges.get(to).add(new Pair<>(from, 0));
                }
            }
        }
        if (canReachAll(zeroEdges) == 1) {
            return mst;
        }

        ArrayList<ArrayList <Pair<Integer, Integer> >> newGraph = new ArrayList<>();
        ArrayList<ArrayList <Pair<Integer, Integer> >> newInvertedGraph = new ArrayList<>();
//        numberComponents = findStrongComponents(graph, invertedGraph);
        numberComponents = findStrongComponents(zeroEdges, zeroInvertedEdges);
        for (int i = 0; i <= numberComponents; i++) {
            newGraph.add(new ArrayList<>());
            newInvertedGraph.add(new ArrayList<>());
        }
        root = colors[root];
        for (int from = 1; from < graph.size(); from++) {
            ArrayList<Pair<Integer, Integer>> edges = graph.get(from);
            for (Pair<Integer, Integer> edge : edges) {
                int weight = edge.getValue();
                int to = edge.getKey();
                if (colors[from] != colors[to]) {
                    newGraph.get(colors[from]).add(new Pair<>(colors[to], weight - minEdges[to]));
                    newInvertedGraph.get(colors[to]).add(new Pair<>(colors[from], weight - minEdges[to]));
                }
            }
        }

        mst += findMst(newGraph, newInvertedGraph);
        return mst;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n, m;
        ArrayList<ArrayList <Pair<Integer, Integer> >> graph = new ArrayList<>();
        ArrayList<ArrayList <Pair<Integer, Integer> >> invertedGraph = new ArrayList<>();
        n = in.nextInt();
        m = in.nextInt();
        numberComponents = n;
        used = new int[n + 1];
        colors = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            used[i] = 0;
            colors[i] = 0;
            graph.add(new ArrayList<>());
            invertedGraph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            int weight = in.nextInt();
            graph.get(from).add(new Pair<>(to, weight));
            invertedGraph.get(to).add(new Pair<>(from, weight));
        }
        root = 1;

        if (canReachAll(graph) == 0) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
            System.out.println(findMst(graph, invertedGraph));
        }
    }
}
