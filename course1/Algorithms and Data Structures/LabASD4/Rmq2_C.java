import java.io.*;

public class Rmq2_C {

    private static int size;
    private static Node[] tree;
    private static long[] leaves;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("rmq2.in")));
             PrintWriter pr = new PrintWriter("rmq2.out")) {
            int n = Integer.parseInt(br.readLine());
            size = (Integer.bitCount(n) == 1) ? n : 1;
            while (size < n) {
                size <<= 1;
            }
            tree = new Node[(size << 1) - 1];
            for (int i = 0; i < (size << 1) - 1; i++) {
                tree[i] = new Node(Long.MAX_VALUE);
            }
            String[] numbers = br.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                leaves[i] = Long.parseLong(numbers[i]);
            }
            for (int i = n; i < size; i++) {
                leaves[i] = Long.MAX_VALUE;
            }
            treeBuild(0, 0, size);
            String line;
            while ((line = br.readLine()) != null) {
                String[] commands = line.split(" ");
                int l = Integer.parseInt(commands[1]);
                int r = Integer.parseInt(commands[2]);
                if (commands[0].equals("set")) {
                    updateSet(0, l - 1, r, Integer.parseInt(commands[3]));
                } else if (commands[0].equals("add")) {
                    updateAdd(0, l - 1, r, Integer.parseInt(commands[3]));
                } else {
                    pr.println(query(0, l - 1, r));
                }
            }
        }
    }

    private static void treeBuild(int i, int tl, int tr) {
        if (tl == tr) {
            return;
        }
        tree[i].l = tl;
        tree[i].r = tr;
        if (tr - tl == 1) {
            tree[i].value = leaves[tl];
        } else {
            int tm = (tl + tr) >> 1;
            int i2 = i << 1;
            treeBuild(i2 + 1, tl, tm);
            treeBuild(i2 + 2, tm, tr);
            tree[i].value = Math.min(tree[i2 + 1].value, tree[i2 + 2].value);
            tree[i].hasChildren = true;
        }
    }

    private static void updateSet(int node, int a, int b, long newVal) {
            int l = tree[node].l;
            int r = tree[node].r;
            if (r < a || l >= b) {
                return;
            }
            if (l >= a && r <= b) {
                tree[node].setValue(newVal);
                return;
            }
            int i2 = node << 1;
            push(node);
            if (i2 + 2 < tree.length) {
                updateSet(i2 + 1, a, b, newVal);
                updateSet(i2 + 2, a, b, newVal);
                tree[node].value = Math.min(tree[i2 + 1].value,
                        tree[i2 + 2].value);
            }
    }

    private static void updateAdd(int node, int a, int b, long addValue) {
            int l = tree[node].l;
            int r = tree[node].r;
            if (r < a || l >= b) {
                return;
            }
            if (l >= a && r <= b) {
                tree[node].addValue(addValue);
                return;
            }
            int i2 = node << 1;
            push(node);
            if (i2 + 2 < tree.length) {
                updateAdd(i2 + 1, a, b, addValue);
                updateAdd(i2 + 2, a, b, addValue);
                tree[node].value = Math.min(tree[i2 + 1].value,
                        tree[i2 + 2].value);
            }
    }

    private static void push(int node) {
        int i2 = node << 1;
        if (i2 + 2 < tree.length) {
            if (tree[node].isSet) {
                tree[i2 + 1].value = tree[node].set;
                tree[i2 + 2].value = tree[node].set;
                tree[i2 + 1].set = tree[node].set;
                tree[i2 + 2].set = tree[node].set;
                tree[i2 + 1].isSet = true;
                tree[i2 + 2].isSet = true;
                tree[i2 + 1].add = 0;
                tree[i2 + 2].add = 0;
                tree[node].isSet = false;
            }
            if (tree[node].add != 0) {
                tree[i2 + 1].addValue(tree[node].add);
                tree[i2 + 2].addValue(tree[node].add);
                tree[node].add = 0;
            }
        }
    }

    private static long query(int node, int a, int b) {
        int l = tree[node].l;
        int r = tree[node].r;
        if (l >= b || r <= a) {
            return Long.MAX_VALUE;
        }
        if (l >= a && r <= b) {
            return tree[node].value;
        }
        push(node);
        int i2 = node << 1;
        long ans;
        ans = Math.min(query(i2 + 1, a, b), query(i2 + 2, a, b));
        tree[node].value = Math.min(tree[i2 + 1].value,
                tree[i2 + 2].value);
        return ans;
    }

    static class Node {
        int l;
        int r;
        long value;
        long add;
        long set;
        boolean isSet = false;
        boolean hasChildren = false;

        Node(long value) {
            this.value = value;
        }

        void addValue(long x) {
            if (!hasChildren) {
                value += x;
                return;
            }
            if (isSet) {
                set += x;
                value += x;
                return;
            }
            add += x;
            value += x;
        }

        void setValue(long x) {
            if (!hasChildren) {
                value = x;
                return;
            }
            add = 0;
            set = x;
            isSet = true;
            value = x;
        }
    }
}
