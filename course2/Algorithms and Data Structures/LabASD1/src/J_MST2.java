import java.util.ArrayList;
import java.util.Scanner;

public class J_MST2 {

    private static int n, m;
    private static ArrayList<Edge> edges = new ArrayList<>();
    private static int[] parent;
    private static int[] range;
    private static long mst = 0;

    private static void getWeightedUndirectedGraph() {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int weight = in.nextInt();
            edges.add(new Edge(u, v, weight));
        }
    }

    private static void init() {
        parent = new int[n + 1];
        range = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            range[i] = 0;
        }
    }

    private static int get(int v) {
        if (parent[v] != v) {
            parent[v] = get(parent[v]);
        }
        return parent[v];
    }

    private static void union(int v, int u) {
        v = get(v);
        u = get(u);
        if (v != u) {
            if (range[v] == range[u]) {
                ++range[v];
            }
            if (range[v] < range[u]) {
                parent[v] = u;
            } else {
                parent[u] = v;
            }
        }
    }

    private static void kruskal() {
        edges.sort(Edge::compareTo);
        for (Edge edge : edges) {
            int u = edge.getU();
            int v = edge.getV();
            int weight = edge.getWeight();
            if (get(u) != get(v)) {
                mst += weight;
                union(v, u);
            }
        }
    }

    public static void main(String[] args) {
        getWeightedUndirectedGraph();
        init();
        kruskal();
        System.out.println(mst);
    }

    private static class Edge implements Comparable<Edge>{
        private int u;
        private int v;
        private int weight;

        private Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge) {
            return Integer.compare(weight, edge.weight);
        }

        int getU() {
            return u;
        }

        int getV() {
            return v;
        }

        int getWeight() {
            return weight;
        }
    }
}
