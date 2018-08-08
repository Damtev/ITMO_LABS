import java.io.*;

class SegmentTree {

    private int size;
    int[] a;
    Node[] tree;

    SegmentTree(int n) {
        int size = (Integer.bitCount(n) == 1) ? n : 1;
        while (size < n) {
            size *= 2;
        }
        a = new int[size];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        for (int i = n; i < size; i++) {
            a[i] = 1000000;
        }
        this.size = size * 2 - 1;
        tree = new Node[this.size];
        for (int i = 0; i < this.size; i++) {
            tree[i] = new Node();
        }
    }

    void treeBuild(int i, int tl, int tr) {
        if (tl == tr) {
            return;
        }
        if (tr - tl == 1) {
            tree[i].value = a[tl];
        } else {
            int tm = (tl + tr) / 2;
            treeBuild(2 * i + 1, tl, tm);
            treeBuild(2 * i + 2, tm, tr);
            tree[i].value = Math.min(tree[2 * i + 1].value, tree[2 * i + 2].value);
        }
        tree[i].l = tl;
        tree[i].r = tr;
    }
}

class Node {

    int l;
    int r;
    int value;
}

public class Parking_L {

    private static SegmentTree segmentTree;
    private final static int infinity = 1000000;

    private static void update(int i, int tl, int tr, int pos, int newVal) {
        if (tl == tr - 1)
            segmentTree.tree[i].value = newVal;
        else {
            int tm = (int) Math.ceil((double) (tl + tr) / 2);
            if (pos < tm) {
                update(i * 2 + 1, tl, tm, pos, newVal);
            }
            else {
                update(i * 2 + 2, tm, tr, pos, newVal);
            }
            segmentTree.tree[i].value =
                    Math.min(segmentTree.tree[i * 2 + 1].value, segmentTree.tree[i * 2 + 2].value);
        }
    }

    private static int enter(int l, int r) {
        int result = query(0, l, r);
        if (result == infinity) {
            return -1;
        } else {
            update(0, 0, segmentTree.a.length, result, infinity);
            return result;
        }
    }

    private static int query(int node, int a, int b) {
        int l = segmentTree.tree[node].l;
        int r = segmentTree.tree[node].r;
        if (l >= b || r <= a) {
            return infinity;
        }
        if (l >= a && r <= b) {
            return segmentTree.tree[node].value;
        }
        return Math.min(query(node * 2 + 1, a, b), query(node * 2 + 2, a, b));
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("parking.in")));
             PrintWriter pr = new PrintWriter("parking.out")) {
            String[] vars = br.readLine().split(" ");
            int n = Integer.parseInt(vars[0]);
            int m = Integer.parseInt(vars[1]);
            segmentTree = new SegmentTree(n);
            segmentTree.treeBuild(0, 0, segmentTree.a.length);
            for (int i = 0; i < m; i++) {
                String[] command = br.readLine().split(" ");
                String action = command[0];
                int place = Integer.parseInt(command[1]) - 1;
                if (action.equals("exit")) {
                    update(0, 0, segmentTree.a.length, place, place);
                } else {
                    int index;
                    pr.println(((index = enter(place, segmentTree.a.length)) != -1) ? (index + 1) :
                            (enter(0, place) + 1));
                }
            }
        }
    }
}
