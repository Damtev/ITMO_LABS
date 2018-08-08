import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Painter_D {

    private static Node[] tree;
    private static int leafAmount;
    private static int size;

    private static void treeBuild(int i, int tl, int tr) {
        if (tl == tr) {
            return;
        }
        tree[i].l = tl;
        tree[i].r = tr;
        if (tr - tl != 1) {
            int tm = (tl + tr) >> 1;
            int i2 = i << 1;
            treeBuild(i2 + 1, tl, tm);
            treeBuild(i2 + 2, tm, tr);
            tree[i].hasChildren = true;
//            if (!tree[i2 + 1].hasChildren) {
//                tree[i].leftLeaf = tree[i2 + 1];
//                tree[i].rightLeaf = tree[i2 + 2];
//            } else {
//                tree[i].leftLeaf = tree[i2 + 1].leftLeaf;
//                tree[i].rightLeaf = tree[i2 + 2].rightLeaf;
//            }
        }
    }


    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("painter.in")));
             PrintWriter pr = new PrintWriter("painter.out")) {
            int m = Integer.parseInt(br.readLine());
            int n = Integer.MIN_VALUE;
            int delta = Integer.MAX_VALUE;
            List<String> commands = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                commands.add(br.readLine());
                String[] command = commands.get(i).split(" ");
                n = Math.max(n , Integer.parseInt(command[1]) + Integer.parseInt(command[2]));
                delta = Math.min(delta, Integer.parseInt(command[1]));
            }
            if (delta > 0) {
                delta = 0;
            }
            size = (Integer.bitCount(n - delta) == 1) ? n - delta : 1;
            while (size < n - delta) {
                size <<= 1;
            }
            tree = new Node[(size << 1) - 1];
            for (int i = 0; i < (size << 1) - 1; i++) {
                tree[i] = new Node();
            }
            treeBuild(0, 0, size);
            for (int i = 0; i < m; i++) {
                String[] command = commands.get(i).split(" ");
                int newVal;
                newVal =  (command[0].equals("W")) ? 0: 1;
                int a = Integer.parseInt(command[1]) - delta;
                int l = Integer.parseInt(command[2]);
                update(0, a, a + l + 1, newVal);
                pr.println(tree[0].amount + " " + /*query(0, 0, size)*/tree[0].value);
//                for (int j = 0; j < (size << 1) - 1; j++) {
//                    pr.println(j + "    " + tree[j].value + "   " + tree[j].isSet + "   " + tree[j].set + "   ");
//                }
//                pr.println();
            }
        }
    }

    private static void update(int node, int a, int b, int newVal) {
        int l = tree[node].l;
        int r = tree[node].r;
        if (r < a || l >= b) {
            return;
        }
        if (l >= a && r <= b) {
//            if (!tree[node].hasChildren) {
//                tree[node].amount = (newVal == 1) ? 1: 0;
//            }
            tree[node].setValue(newVal);
            return;
        }
        int i2 = node << 1;
        push(node);
        if (i2 + 2 < tree.length) {
            update(i2 + 1, a, b, newVal);
            update(i2 + 2, a, b, newVal);
            tree[node].value = tree[i2 + 1].value + tree[i2 + 2].value;
            push(i2 + 1);
            push(i2 + 2);//TODO: надо или нет?
            tree[node].amount = tree[i2 + 1].amount + tree[i2 + 2].amount;
//            if (tree[tree.length - leafAmount + tree[i2 + 1].r - 1].value == 1 &&
//                    tree[tree.length - leafAmount + tree[i2 + 2].l].value == 1) {
//                --tree[node].amount;
//            }
            /*if (tree[i2 + 1].rightLeaf.value == 1 && tree[i2 + 2].leftLeaf.value == 1) {
                --tree[node].amount;
            }*/
            if (get(0, 0, size, tree.length - size + tree[i2 + 1].r - 1) == 1 &&
                    get(0, 0, size, tree.length - size + tree[i2 + 2].l) == 1) {
                --tree[node].amount;
            }
        }
    }

    private static int query(int node, int a, int b) {
//        int l = tree[node].l;
//        int r = tree[node].r;
//        if (r < a || l >= b) {
//            return 0;
//        }
//        if (l >= a && r <= b) {
            return tree[node].value;
//        }
//        push(node);
//        int i2 = node << 1;
//        int ans;
//        ans = query(i2 + 1, a, b) + query(i2 + 2, a, b);
//        tree[node].value = tree[i2 + 1].value + tree[i2 + 2].value;
//        return ans;
//        return 0;
    }

    private static int get(int node, int tl, int tr, int pos) {
        if (tl == tr - 1) {
            return tree[node].value;
        }
        push(node);
        int tm = (tl + tr) >> 1;
        if (tree[pos].l >= tm) {
            return get(2 * node + 2, tm, tr, pos);
        } else {
            return get(2 * node + 1, tl, tm, pos);
        }
    }

    private static void push(int node) {
        /*if (!tree[node].hasChildren) {
            return;
        }
        int i2 = node << 1;
        if (tree[node].isSet) {
            tree[i2 + 1].setValue(tree[node].set);
            tree[i2 + 2].setValue(tree[node].set);
            tree[node].isSet = false;
        }*/
        if (tree[node].hasChildren && tree[node].isSet) {
            int i2 = node << 1;
            tree[i2 + 1].setValue(tree[node].set);
            tree[i2 + 2].setValue(tree[node].set);
            tree[node].isSet = false;
        }
    }

    static class Node {
        int l;
        int r;
        int value;
        int set;
        int amount = 0;
        boolean isSet = false;
        boolean hasChildren = false;
        Node leftLeaf;
        Node rightLeaf;

        void setValue(int x) {
            if (!hasChildren) {
                amount = (set == 1) ? 1: 0;
                value = x;
                return;
            }
            set = x;
            isSet = true;
            value = x;
        }
    }
}
