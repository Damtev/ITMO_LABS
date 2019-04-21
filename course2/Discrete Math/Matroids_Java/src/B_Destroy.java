import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class B_Destroy {

    private static int n, m;
    private static long s;
    private static ArrayList<Edge> edges = new ArrayList<>();
    private static HashMap<Edge, Integer> edgesNumbers = new HashMap<>();
    private static int[] parent;
    private static int[] range;
    private static HashSet<Edge> mst = new HashSet<>();

    private static void getWeightedUndirectedGraph() throws FileNotFoundException {
        Scanner in = new Scanner(new File("destroy.in"));
        n = in.nextInt();
        m = in.nextInt();
        s = in.nextLong();
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            long weight = in.nextLong();
            Edge curEdge = new Edge(u, v, weight);
            edges.add(curEdge);
            edgesNumbers.put(curEdge, i + 1);
        }
        in.close();
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

    private static void antiKruskal() { // строит остовку максимального веса
        edges.sort(Edge::compareTo);
        for (Edge edge : edges) {
            int u = edge.getU();
            int v = edge.getV();
            long weight = edge.getWeight();
            if (get(u) != get(v)) {
                mst.add(edge);
                union(v, u);
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("destroy.out"))) {
            getWeightedUndirectedGraph();
            init();
            antiKruskal();

            ArrayList<Pair> destroyedEdges = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                Edge edge = edges.get(i);
                if (!mst.contains(edge)) {
                    destroyedEdges.add(new Pair(edge.weight, edgesNumbers.get(edge)));
                }
            }
            destroyedEdges.sort(Pair::compareTo);

            ArrayList<Integer> answer = new ArrayList<>();
            long curSum = 0;
            for (Pair destroyedEdge : destroyedEdges) {
                long newSum = curSum + destroyedEdge.weight;
                if (newSum > s || newSum < 0) {
                    break;
                } else {
                    answer.add(destroyedEdge.index);
                    curSum = newSum;
                }
            }

            writer.println(answer.size());
            for (int i : answer) {
                writer.print(i + " ");
            }
        }
    }

    private static class Edge implements Comparable<Edge> {
        private int u;
        private int v;
        private long weight;

        private Edge(int u, int v, long weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge) {
            return (-1) * Long.compare(weight, edge.weight);
        }

        int getU() {
            return u;
        }

        int getV() {
            return v;
        }

        long getWeight() {
            return weight;
        }
    }

    private static class Pair implements Comparable<Pair> {
        private long weight;
        private int index;

        public Pair(long weight, int index) {
            this.weight = weight;
            this.index = index;
        }

        public long getWeight() {
            return weight;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public int compareTo(Pair pair) {
            return Long.compare(weight, pair.weight);
        }
    }
}
