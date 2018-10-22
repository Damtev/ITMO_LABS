import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class E_VerticesBiconnectedComponents {

    private static int n, m;
    private static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    private static HashMap<Integer, Pair<Integer, Integer>> edges = new HashMap<>();
    private static int[] used;
    private static int[] timeIn;
    private static int[] up;
    private static int[] colors;

    private static void getUndirectedGraphWithMultipleEdges() {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        used = new int[n + 1];
        timeIn = new int[n + 1];
        up = new int[n + 1];
        colors = new int[m + 1];

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
            used[i] = 0;
        }

        for (int i = 1; i <= m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph.get(u).add(i);
            graph.get(v).add(i);
            edges.put(i, new Pair<>(u, v));
            colors[i] = 0;
        }
    }

    private static int curColor = 0;

    private static int time = 0;

    private static void dfs(int v, int fromEdgeNumber) {
        used[v] = 1;
        ++time;
        timeIn[v] = time;
        up[v] = time;
        for (int curEdgeNumber : graph.get(v)) {
            if (curEdgeNumber == fromEdgeNumber) {
                continue;
            }
            Pair<Integer, Integer> edge = edges.get(curEdgeNumber);
            int u = edge.getKey();
            if (u == v) {
                u = edge.getValue();
            }
            if (used[u] == 1) {
                up[v] = Math.min(up[v], timeIn[u]);
            } else {
                dfs(u, curEdgeNumber);
                up[v] = Math.min(up[v], up[u]);
            }
        }
    }

    private static void paint(int v, int fromEdgeNumber, int color) {
        used[v] = 1;
        for (int curEdgeNumber : graph.get(v)) {
            if (curEdgeNumber == fromEdgeNumber) {
                continue;
            }
            Pair<Integer, Integer> edge = edges.get(curEdgeNumber);
            int u = edge.getKey();
            if (u == v) {
                u = edge.getValue();
            }
            if (used[u] == 0) {
                if (timeIn[v] <= up[u]) {
                    int newColor = ++curColor;
                    colors[curEdgeNumber] = newColor;
                    paint(u, curEdgeNumber, newColor);
                } else {
                    colors[curEdgeNumber] = color;
                    paint(u, curEdgeNumber, color);
                }
            } else if (timeIn[v] > timeIn[u]){
                colors[curEdgeNumber] = color;
            }
        }
    }

    private static void findVBComponents() {
        for (int v = 1; v <= n; v++) {
            if (used[v] == 0) {
                dfs(v, 0);
            }
        }
        for (int i = 1; i <= n; i++) {
            used[i] = 0;
        }
        for (int v = 1; v <= n; v++) {
            if (used[v] == 0) {
                paint(v, 0, curColor);
            }
        }
    }

    public static void main(String[] args) {
        getUndirectedGraphWithMultipleEdges();
        findVBComponents();
        System.out.println(curColor);
        for (int edge = 1; edge <= m; edge++) {
            System.out.print(colors[edge] + " ");
        }
    }
}
