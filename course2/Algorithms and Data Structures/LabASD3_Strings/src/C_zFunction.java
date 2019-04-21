import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class C_zFunction {

    private static String s;
    private static int n;
    private static int[] zFunction;
    private static StreamTokenizer in;

    private static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    private static String nextString() throws IOException {
        in.nextToken();
        return in.sval;
    }

    private static void calcZFunction() {
        zFunction[0] = 0;
        int left = 0;
        int right = 0;
        for (int i = 1; i < n; i++) {
            zFunction[i] = Math.max(0, Math.min(right - i, zFunction[i - left]));
            while (i + zFunction[i] < n && s.charAt(zFunction[i]) == s.charAt(i + zFunction[i])) {
                ++zFunction[i];
            }
            if (i + zFunction[i] > right) {
                left = i;
                right = i + zFunction[i];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        in = new StreamTokenizer(new InputStreamReader(System.in));
        s = nextString();
        n = s.length();
        zFunction = new int[n];
        calcZFunction();
        StringBuilder answer = new StringBuilder();
        for (int z : zFunction) {
            answer.append(z).append(" ");
        }
        System.out.println(answer.substring(2));
    }
}
