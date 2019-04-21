import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class D_FindSubstring {

    private static String t;
    private static int n;
    private static int m;
    private static int[] zFunction;
    private static int[] prefix;
    private static StreamTokenizer in;
    private static String p;
    private static StringBuilder answer = new StringBuilder();

    private static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    private static String nextString() throws IOException {
        in.nextToken();
        return in.sval;
    }

    private static void calcZFunction(String s) {
        zFunction[0] = 0;
        int left = 0;
        int right = 0;
        for (int i = 1; i < s.length(); i++) {
            zFunction[i] = Math.max(0, Math.min(right - i, zFunction[i - left]));
            while (i + zFunction[i] < s.length() && s.charAt(zFunction[i]) == s.charAt(i + zFunction[i])) {
                ++zFunction[i];
            }
            if (i + zFunction[i] > right) {
                left = i;
                right = i + zFunction[i];
            }
        }
    }

    private static void calcPrefixFunction(String s) {
        prefix[0] = 0;
        for (int i = 1; i < s.length(); i++) {
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

    private static int findSubstringUsingZ_Fuction(String s) {
        int count = 0;
        for (int i = m + 1; i < s.length(); i++) {
            if (zFunction[i] == m) {
                answer.append(i - m).append(" ");
                ++count;
            }
        }
        return count;
    }

    private static int findSubstringPrefixFunction(String s) {
        int count = 0;
        for (int i = m + 1; i < s.length(); i++) {
            if (prefix[i] == m) {
                answer.append(i - m - m + 1).append(" ");
                ++count;
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        in = new StreamTokenizer(new InputStreamReader(System.in));
//        in = new StreamTokenizer(new FileReader("D.in"));
        p = nextString();
        t = nextString();
        StringBuilder s = new StringBuilder(p);
        s.append("#").append(t);
        n = t.length();
        m = p.length();
        zFunction = new int[s.length()];
        calcZFunction(s.toString());
//        prefix = new int[s.length()];
//        calcPrefixFunction(s.toString());
        int count = findSubstringUsingZ_Fuction(s.toString());
//        int count = findSubstringPrefixFunction(s.toString());
        System.out.println(count);
        if (count > 0) {
            System.out.println(answer);
        }
    }
}
