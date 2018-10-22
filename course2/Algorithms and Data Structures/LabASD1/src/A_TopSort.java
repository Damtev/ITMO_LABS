import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class A_TopSort {

    private static int n, m;
    private static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    private static int[] color;
    private static Stack<Integer> topSort = new Stack<>();
    private static boolean hasCycle = false;

    private static void getDirectedGraph() {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        color = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
            color[i] = 0;
        }
        for (int i = 0; i < m; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            graph.get(from).add(to);
        }
    }

    private static void topSort(int v) {
        color[v] = 1;
        for (int u : graph.get(v)) {
            if (color[u] == 0) {
                topSort(u);
            } else if (color[u] == 1) {
                hasCycle = true;
            }
        }
        color[v] = 2;
        topSort.push(v);
    }

    public static void main(String[] args) {
        getDirectedGraph();
        for (int v = 1; v <= n; v++) {
            if (color[v] == 0) {
                topSort(v);
            }
        }
        if (hasCycle) {
            System.out.println(-1);
        } else {
            while (!topSort.isEmpty()) {
                System.out.print(topSort.pop() + " ");
            }
        }
    }
}
