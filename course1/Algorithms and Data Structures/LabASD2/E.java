import java.io.*;

public class E {

    static int[] p = new int[1000001];
    static int[] r = new int[1000001];
    static int[] min = new int[1000001];
    static int[] max = new int[1000001];
    static int[] count = new int [1000001];

    private static int find(int x) {
        if (p[x] != x)
            p[x] = find(p[x]);
        return p[x];
    }

    private static int[] get(int x) {
        int i = find(x);
        int[] ans = {min[i], max[i], count[i]};
        return ans;
    }

    private static void init(int n) {
        for (int i = 0; i < n; i++) {
            p[i + 1] = i + 1;
            count[i + 1] = 1;
            min[i + 1] = i + 1;
            max[i + 1] = i + 1;
            r[i + 1] = 0;
        }
    }

    public static void union (int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y)
            return;
        else if (r[x] == r[y]) { //to x
            p[y] = x;
            r[x]++;
            count[x] += count[y];
            min[x] = Math.min(min[x], min[y]);
            max[x] = Math.max(max[x], max[y]);
        }
        else if (r[x] < r[y]) { //to y
            p[x] = y;
            r[y] = Math.max(r[x], r[y]);
            count[y] += count[x];
            min[y] = Math.min(min[x], min[y]);
            max[y] = Math.max(max[x], max[y]);
        }
        else { //to x
            p[y] = x;
            r[x] = Math.max(r[x], r[y]);
            count[x] += count[y];
            min[x] = Math.min(min[x], min[y]);
            max[x] = Math.max(max[x], max[y]);
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("dsu.in")));
             PrintWriter pr = new PrintWriter(new FileOutputStream("dsu.out"))) {
            int n = Integer.parseInt(br.readLine());
            init(n);
            //int temp0 = 0;
            //int temp1 = 0;
            //int temp2 = 0;
            //int wtf = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] buf = line.split(" ");
                switch (buf[0]) {
                    case "union":
                        union(Integer.parseInt(buf[1]), Integer.parseInt(buf[2]));
                        break;
                    case "get":
                        int[] temp = get(Integer.parseInt(buf[1]));
                        //temp0 = temp[0];
                        //temp1 = temp[1];
                        //temp2 = temp[2];
                        for (int j = 0; j < 2; j++)
                            pr.print(temp[j] + " ");
                        pr.println(temp[2]);
                        //pr.println(get(Integer.parseInt(buf[1])));
                        break;
                }
                /*int[] p0 = p;
                int[] r0 = r;
                int[] min0 = min;
                int[] max0 = max;
                int[] c0 = count;
                int ans0 = temp0;
                int ans1 = temp1;
                int ans2 = temp2;
                wtf++;*/
            }
        }
    }
}
