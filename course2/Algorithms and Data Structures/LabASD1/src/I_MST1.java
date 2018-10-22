import java.util.Scanner;

public class I_MST1 {

    private static int n;
    private static Vertex[] vertices;
    private static boolean[] used;
    private static float[] minEdge;
    private static short[] edgeEnd;
    private static float mst = 0;

    private static final float INF = Float.MAX_VALUE;

    private static void getWeightedUndirectedGraph() {
        Scanner in = new Scanner(System.in);
        n = Integer.parseInt(in.nextLine());
        vertices = new Vertex[n + 1];
        used = new boolean[n + 1];
        minEdge = new float[n + 1];
        edgeEnd = new short[n + 1];
        for (short v = 1; v <= n; v++) {
            vertices[v] = new Vertex(in.nextShort(), in.nextShort());
            used[v] = false;
            minEdge[v] = INF;
            edgeEnd[v] = 0;
        }
    }

    private static void prim() {
        minEdge[1] = 0;
        for (short i = 1; i <= n; i++) {
            short v = 0;
            for (short u = 1; u <= n; u++) {
                if (!used[u] && (v == 0 || minEdge[u] < minEdge[v])) {
                    v = u;
                }
            }
            used[v] = true;
            if (edgeEnd[v] != 0) {
                mst += weight(v, edgeEnd[v]);
            }
            for (short u = 1; u <= n; u++) {
                float weight = weight(v, u);
                if (weight < minEdge[u]) {
                    minEdge[u] = weight;
                    edgeEnd[u] = v;
                }
            }
        }
    }

    private static float weight(short u, short v) {
        return (float) Math.sqrt(Math.pow(vertices[v].getX() - vertices[u].getX(), 2)
                + Math.pow(vertices[v].getY() - vertices[u].getY(), 2));
    }

    public static void main(String[] args) {
        getWeightedUndirectedGraph();
        prim();
        System.out.println(mst);
    }

    private static class Vertex {
        short x;
        short y;

        Vertex(short x, short y) {
            this.x = x;
            this.y = y;
        }

        short getX() {
            return x;
        }

        short getY() {
            return y;
        }
    }
}
