import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class B_PrefixFunction {

    private static String s;
    private static int n;
    private static int[] prefix;
    private static StreamTokenizer in;

    private static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    private static String nextString() throws IOException {
        in.nextToken();
        return in.sval;
    }

    private static void calcPrefixFunction() {
        prefix[0] = 0;
        for (int i = 1; i < n; i++) {
            int k = prefix[i - 1];
            while (k > 0 && s.charAt(i) != s.charAt(k)) {
                k = prefix[k - 1];
            }
            if (s.charAt(i) == s.charAt(k)) {
                ++k;
            }
            prefix[i] = k;
        }
    }

    public static void main(String[] args) throws IOException {
        in = new StreamTokenizer(new InputStreamReader(System.in));
        s = nextString();
        n = s.length();
        prefix = new int[n];
        calcPrefixFunction();
        StringBuilder answer = new StringBuilder();
        for (int p : prefix) {
            answer.append(p).append(" ");
        }
        System.out.println(answer);
    }
}
