import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class A_ShowPlanarity {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(/*new File("input.in")*/System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
            edges.add(new Edge(Math.min(u, v), Math.max(u, v)));
        }

        double[] verticesCoordinates = new double[n + 1];
        double x = 0;
        ArrayList<Integer> hamCycle = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int v = in.nextInt();
            hamCycle.add(v);
            verticesCoordinates[v] = x++;
        }

        ArrayList<ArrayList<Integer>> edgedGraph = makeEdgedGraph(m, graph, edges, hamCycle);
        int[] colors = new int[m + 1];
        boolean[] used = new boolean[m + 1];
        Arrays.fill(colors, -1);
        for (int i = 1; i <= m; i++) {
            if (!used[i]) {
                isBiparted(i, 1, edgedGraph, colors, used);
            }
        }

        if (colors[0] != -1) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
            for (int v = 1; v <= n; v++) {
                System.out.print(verticesCoordinates[v] + " " + 0 + " ");
            }

            System.out.println();

            for (Edge edge : edges) {
                double xDiff = Math.abs(verticesCoordinates[edge.from] - verticesCoordinates[edge.to]);
                double y = xDiff / 2;
                int index = edges.indexOf(edge);
                if (colors[index + 1] == 2) {
                    y *= -1;
                }
                System.out.println((verticesCoordinates[edge.from] + verticesCoordinates[edge.to]) / 2 + " " + y);
            }
        }
    }

    private static ArrayList<ArrayList<Integer>> makeEdgedGraph(int m, ArrayList<ArrayList<Integer>> graph,
                                ArrayList<Edge> edges, ArrayList<Integer> hamCycle) {
        ArrayList<ArrayList<Integer>> edgedGraph = new ArrayList<>();
        for (int i = 0; i <= m; i++) {
            edgedGraph.add(new ArrayList<>());
        }

        for (Edge edge : edges) {
            int start = edge.from;
            int end = edge.to;

            int indexOfStart = hamCycle.indexOf(start);
            int indexOfEnd = hamCycle.indexOf(end);
            int startPos = Math.min(indexOfStart, indexOfEnd);
            int endPos = Math.max(indexOfStart, indexOfEnd);

            for (int vertexBetween = startPos + 1; vertexBetween < endPos; vertexBetween++) {
                int from = hamCycle.get(vertexBetween);
                for (int to : graph.get(from)) {
                    indexOfEnd = hamCycle.indexOf(to);
                    if (indexOfEnd < startPos || indexOfEnd > endPos) {
                        int index = 0;
                        for (int i = 0; i < edges.size(); i++) {
                            Edge second = edges.get(i);
                            if ((second.from == from && second.to == to) || (second.from == to && second.to == from)) {
                                index = i;
                                break;
                            }
                        }
                        edgedGraph.get(edges.indexOf(edge) + 1).add(index + 1);
                    }
                }
            }
        }
        return edgedGraph;
    }

    private static void isBiparted(int v, int curColor, ArrayList<ArrayList<Integer>> edgedGraph, int[] colors, boolean[] used) {
        if (colors[0] != -1 || used[v]) {
            return;
        }

        used[v] = true;
        colors[v] = 3 - curColor;

        for (int u : edgedGraph.get(v)) {
            if (!used[u]) {
                isBiparted(u, colors[v], edgedGraph, colors, used);
            } else {
                if (colors[v] == colors[u] && colors[u] != -1) {
                    colors[0] = 3;
                }
            }
        }
    }

    private static class Edge {
        int from;
        int to;

        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}
