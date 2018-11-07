import com.sun.deploy.net.HttpRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class D_Tournament {

    private static int n;
    private static boolean[][] graph;
    private static Node head;
    private static Node tail;
    private static int cycleLen;

    private static void getDirectedGraph() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("guyaury.in"))) {
            n = Integer.parseInt(reader.readLine());
            graph = new boolean[n + 1][n + 1];
            for (int v = 1; v <= n; v++) {
                String edges = reader.readLine();
                for (int u = 0; u < edges.length(); u++) {
                    if (edges.charAt(u) == '1') {
                        graph[v][u + 1] = true;
                    } else {
                        graph[u + 1][v] = true;
                    }
                }
            }
        }
    }

    private static void findHamiltonianPath() {
        for (int v = 2; v <= n; v++) {
            Node newVertex = new Node(v);
            Node curNode = head;
            Node prevCurNode = null;
            while (true) {
                if (curNode == null || !graph[curNode.number][v]) {
                    break;
                } else {
                    prevCurNode = curNode;
                    curNode = curNode.next;
                }
            }
            if (curNode != null && prevCurNode != null) { // вставляем между prev и cur
                prevCurNode.next = newVertex;
                newVertex.prev = prevCurNode;
                newVertex.next = curNode;
                curNode.prev = newVertex;
            }
            // TODO: отличий от коммента вроде нет, но вдруг
            if (curNode != null && prevCurNode == null) { // вставка до головы
                    curNode.prev = newVertex;
                    newVertex.next = curNode;
                    head = newVertex;
            }
            if (prevCurNode != null && curNode == null) { // вставка в конец
                    prevCurNode.next = newVertex;
                    newVertex.prev = prevCurNode;
                    tail = newVertex;
            }
                /*if (prevCurNode == null) { // вставка до головы
                    curNode.prev = newVertex;
                    newVertex.next = curNode;
                    head = newVertex;
                } else { // вставка в конец
                    prevCurNode.next = newVertex;
                    newVertex.prev = prevCurNode;
                    tail = newVertex;
                }*/
        }
    }

    private static void findHamiltonianCycle() {
        while (/*true*/cycleLen != n) {
//            Node kNode = head.next.next;
//            Node curNode = kNode;
            Node kNode = tail; // точно не нулл
            cycleLen = n;
            while (/*curNode != null*//*true*/cycleLen > 3) {
                // TODO: почему kNode иногда оказывется нулл?
                if (kNode == null) {
                    return;
                }
                /*if (!graph[kNode.number][head.number] || kNode.next == null) {
                    break;
                } else {
                    kNode = kNode.next;
                }*/

                if (graph[kNode.number][head.number]) {
                    break;
                } else {
                    kNode = kNode.prev;
                    --cycleLen;
                }

                /*if (graph[curNode.number][head.number]) {
                    kNode = curNode;
                }
                curNode = curNode.next;*/
            }

            if (kNode.next == null) { // весь путь - это цикл
                return;
            }

            // разделяем на цикл и путь
            Node newVertex = kNode.next; // точно не нулл

            Node iNode = head; // точно не нулл
//            Node secondNode = head.next;
            while (!graph[newVertex.number][iNode.number]) {
                iNode = iNode.next;
                if (iNode == null || iNode.equals(kNode.next)) {
                    break;
                }
            }

            if (iNode != null && !iNode.equals(kNode.next)) {
                boolean isTail = false;
                if (newVertex.next == null) { // newVertex - это хвост
//                    newVertex.prev.next = null;
//                    tail = newVertex.prev;
//                    tail.next = null;
                    kNode.next = null;
                    tail = kNode;
                    // TODO: вроде тогда можно закончить?
                    isTail = true;
                }

                if (iNode.prev == null) { // iNode - это голова
                    iNode.prev = newVertex;
                    newVertex.next = iNode;
                    newVertex.prev = null;
                    head = newVertex;
                } else {
                    iNode.prev.next = newVertex;
                    newVertex.prev = iNode.prev;
                    newVertex.next = iNode;
                    iNode.prev = newVertex;
                }

                if (isTail) {
                    return;
                }
            } else { // в цикле не нашли нужную вершину
                Node jNode = newVertex.next; // точно не null
                while (true) {
                    if (jNode == null) {
                        return;
                    }
                    iNode = head; // точно не null
                    while (!graph[jNode.number][iNode.number]) {
                        iNode = iNode.next;
                        if (iNode == null || iNode.equals(kNode.next)) { // не нашли в цикле
                            break;
                        }
                    }

                    if (iNode != null && !iNode.equals(kNode.next)) {
                        boolean isTail = false;
                        if (jNode.next == null) { // jNode - это хвост
                            kNode.next = null;
                            tail = kNode;
                            // TODO: вроде тогда можно закончить?
                            isTail = true;
                        }
                        if (iNode.prev == null) { // iNode - это голова
                            iNode.prev = jNode;
                            jNode.next = iNode;
                            newVertex.prev = null;
                            head = newVertex;
                        } else {
                            iNode.prev.next = newVertex; // iNode.prev точно не нулл
                            newVertex.prev = iNode.prev;
                            iNode.prev = jNode;
                            jNode.next = iNode;
                        }

                        if (isTail) {
                            return;
                        }

                        break;
                    } else {
                        jNode = jNode.next;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        getDirectedGraph();

        head = new Node(1);
        tail = head;
        findHamiltonianPath();

        cycleLen = 3;
        findHamiltonianCycle();


        try (PrintWriter writer = new PrintWriter(new FileWriter("guyaury.out"))) {
            while (head != null) {
                writer.print(head.number + " ");
                head = head.next;
            }
        }


    }

    private static class Node {
        private Node prev = null;
        private Node next = null;
        private int number;

        Node(Node node) {
            prev = node.prev;
            next = node.next;
            number = node.number;
        }

        Node(Node left, Node right, int number) {
            this.prev = left;
            this.next = right;
            this.number = number;
        }

        Node(int number) {
            this.number = number;
        }
    }
}
