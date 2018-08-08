import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

class Pair implements Comparable<Pair>{

    int first;
    int second;

    public Pair() {
        first = 0;
        second = 0;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public int compareTo(Pair pair) {
        if (pair.first - this.first == 0) {
            return 0;
        }
        if (pair.first - this.first < 0) {
            return -1;
        }
        return 1;
    }
}

public class L{

    static ArrayList<ArrayList<Integer>> list = new ArrayList<>(1 << 18);
    static Pair[] masks = new Pair[1 << 18];
    static int[] weight = new int[18];
    static int n;
    static int w;

    static void get(int mask) {
        int total = 0;
        for (int i = 0; i < n; i++) {
            if (mask == (1 << i)) {
                list.get(mask).add(i);
                total += weight[i];
            }
//            masks[mask] = new Pair<>(total, mask);
            masks[mask].setFirst(total);
            masks[mask].setSecond(mask);
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("skyscraper.in")));
             PrintWriter pr = new PrintWriter("skyscraper.out")) {
            String[] buf = br.readLine().split(" ");
            n = Integer.parseInt(buf[0]);
            w = Integer.parseInt(buf[1]);
            for (int i = 0; i < (1 << 18); i++) {
                masks[i] = new Pair();
            }
            for (int i = 0; i < (1 << 18); i++) {
                list.add(new ArrayList<>());
            }
            for (int mask = 0; mask < (1 << n); mask++) {
                get(mask);
            }
            Arrays.sort(masks, Pair::compareTo);

            int mask = 0;
            ArrayList<Integer> ans = new ArrayList<>();
            for (int i = 1; i < (1 << n); i++) {
                if (((masks[i].getSecond() & mask) == 0) && masks[i].getFirst() <= w) {
                    ans.add(masks[i].getSecond());
                    mask |= masks[i].getSecond();
                }
            }
            pr.println(ans.size());
            for (int i = 0; i < ans.size(); i++) {
                pr.print(list.get(ans.get(i)).size() + " ");
                for (int j = 0; j < list.get(ans.get(i)).size(); j++) {
                    pr.print(list.get(ans.get(i)).get(j) + 1 + " ");
                }
                pr.println();
            }
        }
    }
}
