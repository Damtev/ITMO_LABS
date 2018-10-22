import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class G_Party {

    private static int n, m;
    private static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> invertedGraph = new ArrayList<>();
    private static int[] used;
    private static Stack<Integer> order = new Stack<>();
    private static HashMap<String, Integer> verticesStrToInt = new HashMap<>();
    private static HashMap<Integer, String> verticesIntToStr = new HashMap<>();
    private static int[] colors;

    private static void getDirectedGraph() {
        Scanner in = new Scanner(System.in);
        String[] vars = in.nextLine().split(" ");
        n = Integer.parseInt(vars[0]);
        m = Integer.parseInt(vars[1]);
        colors = new int[2 * n + 1];
        used = new int[2 * n + 1];
        for (int i = 1; i <= n; i++) {
            String name = in.nextLine();
            verticesStrToInt.put(name, i);
            verticesIntToStr.put(i, name);
        }
        for (int i = 0; i <= 2 * n; i++) {
            graph.add(new ArrayList<>());
            invertedGraph.add(new ArrayList<>());
            colors[i] = 0;
            used[i] = 0;
        }
        for (int i = 0; i < m; i++) {
            String[] conditional = in.nextLine().split(" => ");
            String first = conditional[0];
            String second = conditional[1];
            int from = first.startsWith("+") ? verticesStrToInt.get(first.substring(1)) :
                    2 * n - verticesStrToInt.get(first.substring(1)) + 1;
            int to = second.startsWith("+") ? verticesStrToInt.get(second.substring(1)) :
                    2 * n - verticesStrToInt.get(second.substring(1)) + 1;
            graph.get(from).add(to);
            invertedGraph.get(to).add(from);

            graph.get(2 * n - to + 1).add(2 * n - from + 1);
            invertedGraph.get(2 * n - from + 1).add(2 * n - to + 1);
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

    private static void findStrongComponents() {
        for (int v = 1; v <= 2 * n; v++) {
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
    }

    public static void main(String[] args) {
        getDirectedGraph();
        findStrongComponents();
        int answer = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (colors[i] == colors[2 * n - i + 1]) {
                answer = -1;
                break;
            } else if (colors[i] > colors[2 * n - i + 1]) {
                list.add(i);
                ++answer;
            }
        }
        if (answer > 0) {
            System.out.println(answer);
            for (Integer name : list) {
                System.out.println(verticesIntToStr.get(name));
            }
        } else if (answer < 0) {
            System.out.println(-1);
        }
    }
}
