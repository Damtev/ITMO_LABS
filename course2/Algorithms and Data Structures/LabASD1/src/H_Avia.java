import java.io.*;
import java.util.Arrays;

public class H_Avia {

    private static int n;
    private static int[][] graph;
    private static boolean[][] g;
    private static boolean[] used;
    private static int size;

    private static void dfs1(int v) {
        used[v] = true;
        for (int u = 1; u <= n; u++) {
            if (!used[u] && g[v][u]) {
                dfs1(u);
            }
        }
    }

    private static void dfs2(int v) {
        used[v] = true;
        for (int u = 1; u <= n; u++) {
            if (!used[u] && g[u][v]) {
                dfs2(u);
            }
        }
    }

    private static boolean allVerticesReached() {
        for (int v = 1; v <= n; v++) {
            if (!used[v]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader("avia.in"));
             PrintWriter out = new PrintWriter("avia.out")) {
            n = Integer.parseInt(in.readLine());
            graph = new int[n + 1][n + 1];
            used = new boolean[n + 1];
            g = new boolean[n + 1][n + 1];
            int maxEdge = Integer.MIN_VALUE;
            int minEdge = Integer.MAX_VALUE;
            for (int v = 1; v <= n; v++) {
                String[] weights = in.readLine().split(" ");
                for (int u = 1; u <= n; u++) {
                    if (v == u) {
                        continue;
                    }
                    graph[v][u] = Integer.parseInt(weights[u - 1]);
                    maxEdge = Math.max(graph[v][u], maxEdge);
                    minEdge = Math.min(graph[v][u], minEdge);
                }
            }

            size = (maxEdge + minEdge) / 2;
            while (minEdge < maxEdge) {
                for (int v = 1; v <= n; v++) {
                    for (int u = 1; u <= n; u++) {
                        g[v][u] = graph[v][u] <= size;
                    }
                }
                Arrays.fill(used, false);

                dfs1(1);
                boolean needMore = false;
                if (allVerticesReached()) {
                    Arrays.fill(used, false);
                    dfs2(1);
                    if (!allVerticesReached()) {
                        needMore = true;
                    }
                } else {
                    needMore = true;
                }
                if (needMore) {
                    minEdge = size + 1;
                } else {
                    maxEdge = size;
                }
                size = (minEdge + maxEdge) / 2;
            }

            out.print(size);
        }
    }
}
