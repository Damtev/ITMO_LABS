//TODO: WA5

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class A_Rainbow {

    private static final int maxColor = 100;

    private static int n;
    private static int m;
    private static TreeSet<Edge> graph = new TreeSet<>();
    private static HashMap<Integer, Edge> map = new HashMap<>();
    private static StreamTokenizer in;

    private static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    private static String nextString() throws IOException {
        in.nextToken();
        return in.sval;
    }

    private static void read() throws IOException {
        n = nextInt();
        m = nextInt();
        for (int i = 1; i <= m; i++) {
            Edge curEdge = new Edge(i, nextInt(), nextInt(), nextInt());
            graph.add(curEdge);
            map.put(curEdge.id, curEdge);
        }
    }

    private static boolean dfs(int v, int[] used, TreeSet<Edge> edges) {
        used[v] = 1;
        for (Edge edge : edges) {
            if (edge.from == v) {
                if (used[edge.to] == 1) {
                    return true;
                } else if (used[edge.to] == 0){
                    return dfs(edge.to, used, edges);
                }
            }
        }
        used[v] = 2;
        return false;
    }

    private static boolean hasCycle(TreeSet<Edge> edges) {
        int[] used = new int[n + 1];
        boolean hasCycle = false;
        for (int v = 1; v <= n; v++) {
            if (used[v] == 0) {
                hasCycle |= dfs(v, used, edges);
            }
        }
        return hasCycle;
    }

    private static boolean dfs(int v, int[] used, TreeSet<Edge> edges, Edge removedEdge, Edge addedEdge) {
        used[v] = 1;
        for (Edge edge : edges) {
            if (edge == removedEdge) {
                continue;
            }
            if (edge.from == v) {
                if (used[edge.to] == 1) {
                    return true;
                } else if (used[edge.to] == 0){
                    return dfs(edge.to, used, edges, removedEdge, addedEdge);
                }
            }
        }
        if (addedEdge.from == v) {
            if (used[addedEdge.to] == 1) {
                return true;
            } else if (used[addedEdge.to] == 0) {
                return dfs(addedEdge.to, used, edges, removedEdge, addedEdge);
            }
        }
        used[v] = 2;
        return false;
    }

    private static boolean hasCycle(TreeSet<Edge> edges, Edge removedEdge, Edge addedEdge) {
        int[] used = new int[n + 1];
        boolean hasCycle = false;
        for (int v = 1; v <= n; v++) {
            if (used[v] == 0) {
                hasCycle |= dfs(v, used, edges, removedEdge, addedEdge);
            }
        }
        return hasCycle;
    }

    private static boolean hasDiffColors(TreeSet<Edge> edges) {
        boolean[] colors = new boolean[maxColor + 1];
        for (Edge edge : edges) {
            if (colors[edge.color]) {
                return false;
            } else {
                colors[edge.color] = true;
            }
        }
        return true;
    }

    private static boolean hasDiffColors(TreeSet<Edge> edges, Edge removedEdge, Edge addedEdge) {
        boolean[] colors = new boolean[maxColor + 1];
        for (Edge edge : edges) {
            if (edge == removedEdge) {
                continue;
            }
            if (colors[edge.color]) {
                return false;
            } else {
                colors[edge.color] = true;
            }
        }
        return !colors[addedEdge.color];
    }

    //TODO: похоже, работает за куб, как ускорить?
    private static TreeSet<Edge> buildReplacementGraph(TreeSet<Edge> leftPart, TreeSet<Edge> rightPart) {
        TreeSet<Edge> replacementGraph = new TreeSet<>();
        for (Edge leftEdge : leftPart) {
            TreeSet<Edge> replacement = new TreeSet<>(leftPart);
            replacement.remove(leftEdge);
            for (Edge rightEdge : rightPart) {
                //TODO: петель вроде быть не должно
                if (leftEdge == rightEdge) {
                    continue;
                }
                replacement.add(rightEdge);
                if (!hasCycle(replacement/*leftPart, leftEdge, rightEdge*/)) {
                    replacementGraph.add(new Edge(replacementGraph.size() + 1, leftEdge.id, rightEdge.id, 1));
                }
                if (hasDiffColors(replacement/*leftPart, leftEdge, rightEdge*/)) {
                    replacementGraph.add(new Edge(replacementGraph.size() + 1, rightEdge.id, leftEdge.id, 1));
                }
                replacement.remove(rightEdge);
            }
        }
        return replacementGraph;
    }

    private static TreeSet<Integer> findShortestPath(TreeSet<Integer> x1, TreeSet<Integer> x2,
            TreeSet<Edge> leftPart, TreeSet<Edge> rightPart/*TreeSet<Edge> edges*/) {
        TreeSet<Integer> shortestPath = new TreeSet<>();
        for (int v : x1) {
            if (x2.contains(v)) {
                shortestPath.add(v);
                return shortestPath;
            }
        }

//        for (int v : x1) {
//            edges.add(new Edge(edges.size() + 1, m + 1, v, 1));
//        }
//        int[] parents = new int[edges.size() + 1];
        int[] parents = new int[m + 1];
        //        queue.add(m + 1); // фиктивная вершина
        LinkedList<Integer> queue = new LinkedList<>(x1);
        boolean canReach = false;
        int firstReached = 0;
        while (!queue.isEmpty()) {
            int edgeId = queue.pop();
//            for (Edge curEdge : edges) {
//                if (edgeId == curEdge.from) {
//                    parents[curEdge.to] = edgeId;
//                    if (x2.contains(curEdge.to) && edgeId != m + 1) {
//                        canReach = true;
//                        firstReached = curEdge.to;
//                        break;
//                    }
//                    queue.add(curEdge.to);
//                }
//            }
            boolean inLeftPart = false;
            Edge edge = map.get(edgeId);
            if (leftPart.contains(edge)) {
                leftPart.remove(edge);
                inLeftPart = true;
            } else {
                leftPart.add(edge);
            }
            for (Edge neighbour : graph) {
                if (edge.id == neighbour.id) {
                    continue;
                }
                boolean hasEdge = false;
                if (inLeftPart) {
                    leftPart.add(neighbour);
                    if (!hasCycle(leftPart)) {
                        hasEdge = true;
                    }
                    leftPart.remove(neighbour);
                } else {
                    leftPart.remove(neighbour);
                    if (hasDiffColors(leftPart)) {
                        hasEdge = true;
                    }
                    leftPart.add(neighbour);
                }
                if (hasEdge) {
                    queue.add(neighbour.id);
                    parents[neighbour.id] = edgeId;
                    if (x2.contains(neighbour.id)) {
                        canReach = true;
                        firstReached = neighbour.id;
                        break;
                    }
                }
            }
            if (inLeftPart) {
                leftPart.add(edge);
            } else {
                leftPart.remove(edge);
            }
            if (canReach) {
                break;
            }
        }
        if (canReach) {
            shortestPath.add(firstReached);
            int parent = parents[firstReached];
            while (parent != /*m + 1*/0) {
                shortestPath.add(parent);
                parent = parents[parent];
            }
        }
        return shortestPath;
    }

    private static void printAnwers(TreeSet<Edge> edges) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter("rainbow.out"))) {
            printWriter.println(edges.size());
            StringBuilder answer = new StringBuilder();
            for (Edge edge : edges) {
                answer.append(edge.id).append(" ");
            }
            printWriter.print(answer);
        }
    }

    private static void solve() throws IOException {
        in = new StreamTokenizer(new FileReader("rainbow.in"));
        read();
        TreeSet<Edge> leftPart = new TreeSet<>();
//        int count = 1;
        while (true) {
            TreeSet<Edge> rightPart = graph.stream().filter(edge -> !leftPart.contains(edge))
                    .collect(Collectors.toCollection(TreeSet::new));
//            TreeSet<Edge> rightPart = new TreeSet<>();
//            for (Edge curEdge : graph) {
//                if (!leftPart.contains(curEdge)) {
//                    rightPart.add(curEdge);
//                }
//            }

            //TODO: добавил из дока, сильно тормозит построение
            /*TreeSet<Edge> newRightPart = new TreeSet<>(rightPart);
            for (Edge edge : rightPart) {
                leftPart.add(edge);
                if (!hasCycle(leftPart) && hasDiffColors(leftPart)) {
                    continue;
                } else {
                    leftPart.remove(edge);
                    newRightPart.add(edge);
                }
            }
            rightPart = newRightPart;*/

            //TODO: очень долго
//            long before = System.nanoTime();
//            TreeSet<Edge> replacementGraph = buildReplacementGraph(leftPart, rightPart);
//            long diff = System.nanoTime() - before;
//            System.out.println("Building graph " + count + " spent: " + diff / 1e9);
//            ++count;

            TreeSet<Integer> x1 = new TreeSet<>();
            TreeSet<Integer> x2 = new TreeSet<>();
            for (Edge curEdge : rightPart) {
                leftPart.add(curEdge);
                if (!hasCycle(leftPart)) {
                    x1.add(curEdge.id);
                }
                if (hasDiffColors(leftPart)) {
                    x2.add(curEdge.id);
                }
                leftPart.remove(curEdge);
            }
            if (x1.size() == 0 || x2.size() == 0) {
                break;
            }
//            long before = System.nanoTime();
            TreeSet<Integer> shortestPath = findShortestPath(x1, x2, leftPart, rightPart/*replacementGraph*/);
//            long diff = System.nanoTime() - before;
//            System.out.println("Building path " + count + " spent: " + diff / 1e9 + "\n");
//            ++count;
            if (shortestPath.size() == 0) {
                break;
            } else {
                TreeSet<Edge> edgesFromPath = new TreeSet<>();
                for (int id : shortestPath) {
                    edgesFromPath.add(map.get(id));
                }
                TreeSet<Edge> intersection = new TreeSet<>(leftPart);
                intersection.retainAll(edgesFromPath);
                leftPart.addAll(edgesFromPath);
                leftPart.removeAll(intersection);
            }
        }
        printAnwers(leftPart);
    }

    private static void smallTest() throws IOException {
        try (PrintWriter printWriter = new PrintWriter("rainbow.in")) {
            printWriter.println("100 99");
            for (int i = 1; i < 100; i++) {
                printWriter.println(i + " " + (i + 1) + " " + i);
            }
        }
    }

    private static void bigTest() throws IOException {
        try (PrintWriter printWriter = new PrintWriter("rainbow.in")) {
            printWriter.println("100 4000");
            int edgeNumber = 0;
            for (int i = 1; i < 100; i++) {
                printWriter.println(i + " " + (i + 1) + " " + i);
                ++edgeNumber;
            }
            for (int i = 1; i < 100; i++) {
                for (int j = i + 2; j < 100; j++) {
                    printWriter.println(i + " " + j + " " + j);
                    ++edgeNumber;
                }
            }
            printWriter.println(edgeNumber);
        }
    }

    public static void main(String[] args) throws IOException {
//        smallTest();
//        bigTest();
//        long begin = System.nanoTime();
        solve();
//        long diff = System.nanoTime() - begin;
//        System.out.println("Total time: " + diff / 1e9);
        /*int count = 4500;
        long k = 0;
        long begin = System.nanoTime();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                for (int l = 0; l < count; l++) {
                    ++k;
                }
            }
        }
        long diff = System.nanoTime() - begin;
        System.out.println("Total time: " + diff / 1e9);*/
    }

    private static class Edge implements Comparable {
        int id;
        int from;
        int to;
        int color;

        Edge(int id, int from, int to, int color) {
            this.id = id;
            this.from = from;
            this.to = to;
            this.color = color;
        }

        @Override
        public int compareTo(Object o) {
            Edge edge = (Edge) o;
            return Integer.compare(id, edge.id);
        }
    }
}