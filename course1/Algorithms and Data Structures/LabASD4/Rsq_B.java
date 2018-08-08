import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class SumSegmentTree {

    public int size;
    public int newN;
    long[] tree;

    void treeBuild(long[] array) {
        int size = (Integer.bitCount(array.length) == 1) ? array.length : 1;
        while (size < array.length) {
            size <<= 1;
        }
        this.size = (size << 1) - 1;
        tree = new long[this.size];
        System.arraycopy(array, 0, tree, this.size - size, array.length);
        for (int i = size - 2; i >= 0; --i) {
            tree[i] = tree[(i << 1) + 1] + tree[(i << 1) + 2];
        }
        newN = size;
    }
}

public class Rsq_B {

    private static SumSegmentTree SumSegmentTree;

    private static void update(int i, int tl, int tr, int pos, long newVal) {
        while (tl != tr - 1) {
            int tm = (tl + tr) >> 1;
            int i_2 = i << 1;
            if (pos < tm) {
                i = i_2 + 1;
                tr = tm;
            } else {
                i = i_2 + 2;
                tl = tm;
            }
        }
        SumSegmentTree.tree[i] = newVal;
        while (i != 0) {
            i = (i - 1) >> 1;
            int i_2 = i << 1;
            SumSegmentTree.tree[i] =
                    SumSegmentTree.tree[i_2 + 1] + SumSegmentTree.tree[i_2 + 2];
        }
    }

    private static long query(int left, int right) {
        long leftRes = 0;
        long rightRes = 0;
        while (left < right) {
            if (left % 2 == 0) {
                leftRes = leftRes + SumSegmentTree.tree[left];
            }
            left >>= 1;
            if (right % 2 == 1) {
                rightRes = SumSegmentTree.tree[right] + rightRes;
            }
            right = (right >> 1) - 1;
        }
        if (left == right) {
            leftRes = leftRes + SumSegmentTree.tree[left];
        }
        return leftRes + rightRes;
    }

    private static int getIndexInTree(int i) {
        return SumSegmentTree.size - SumSegmentTree.newN + i;
    }

    public static void main(String[] args) throws IOException {
        try (PrintWriter pr = new PrintWriter("rsq.out")) {
            List<String> list = Files.readAllLines(Paths.get("rsq.in"));
            int n = Integer.parseInt(list.get(0));
            long[] array = new long[n];
            String[] numbers = list.get(1).split(" ");
            for (int i = 0; i < n; i++) {
                array[i] = Long.parseLong(numbers[i]);
            }
            SumSegmentTree = new SumSegmentTree();
            SumSegmentTree.treeBuild(array);
            int i = 2;
            for (; i < list.size(); i++) {
                String[] command = list.get(i).split(" ");
                String action = command[0];
                if (action.equals("set")) {
                    int place = Integer.parseInt(command[1]) - 1;
                    long newValue = Long.parseLong(command[2]);
                    update(0, 0, SumSegmentTree.newN, place, newValue);
                } else {
                    int a = Integer.parseInt(command[1]);
                    int b = Integer.parseInt(command[2]);
                    pr.println(query(getIndexInTree(a - 1), getIndexInTree(b - 1)));
                }
            }
        }
    }
}
