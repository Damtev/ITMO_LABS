import java.io.*;

public class A_ComparingSubstrings {

    private static String s;
    private static int n;
    private static long[] hash;
    private static long[] pow;
    private static final int p = 37;
    private static int m;
    private static StreamTokenizer in;

    private static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    private static String nextString() throws IOException {
        in.nextToken();
        return in.sval;
    }


    private static void calcHash() {
        hash = new long[n];
        pow = new long[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                pow[0] = 1;
            } else {
                pow[i] = pow[i - 1] * p;
            }
            hash[i] = (s.charAt(i) - 'a' + 1) * pow[i];
            if (i > 0) {
                hash[i] += hash[i - 1];
            }
        }
    }

    private static boolean checkEqual(int a, int b, int c, int d) {
        long hash1 = hash[b];
        if (a > 0) {
            hash1 -= hash[a - 1];
        }
        long hash2 = hash[d];
        if (c > 0) {
            hash2 -= hash[c - 1];
        }
        return a == c && hash1 == hash2 || a < c && hash1 * pow[c - a] == hash2 || a > c && hash1 == hash2 * pow[a - c];
    }

    public static void main(String[] args) throws IOException {
        in = new StreamTokenizer(new InputStreamReader(System.in));
        s = nextString();
        n = s.length();
        calcHash();
        m = nextInt();
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < m; i++) {
            int a, b, c, d;
            a = nextInt() - 1;
            b = nextInt() - 1;
            c = nextInt() - 1;
            d = nextInt() - 1;
            answer.append(checkEqual(a, b, c, d) ? "Yes" : "No").append("\n");
        }
        System.out.println(answer.toString());
    }
}