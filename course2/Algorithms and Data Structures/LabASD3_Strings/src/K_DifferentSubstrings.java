import java.io.*;
import java.util.Arrays;

public class K_DifferentSubstrings {

    // латинский алфавит + костыльный символ в конце
    private static final int ALPHABET = 27;

    private static String s;
    private static int n;
    private static int[] suffArr;
    private static int[] lcp;
    private static StreamTokenizer in;

    private static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    private static String nextString() throws IOException {
        in.nextToken();
        return in.sval;
    }

    private static void calcSuffArr() {
        int[] count = new int[Math.max(n, ALPHABET)];
        for (int i = 0; i < n; i++) {
            ++count[s.charAt(i) - 'a' + 1];
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        for (int i = 0; i < n; i++) {
            suffArr[--count[s.charAt(i) - 'a' + 1]] = i;
//            suffArr[count[s.charAt(i) - 'a']] = i;
        }
        int[] classes = new int[n];
        classes[suffArr[0]] = 0;
        int curClass = 1;
        for (int i = 1; i < n; i++) {
            if (s.charAt(suffArr[i]) != s.charAt(suffArr[i - 1])) {
                ++curClass;
            }
            classes[suffArr[i]] = curClass - 1;
        }

        int[] sortedBy2 = new int[n];
        for (int curLen = 0; (1 << curLen) < n; curLen++) {
            for (int i = 0; i < n; i++) {
                sortedBy2[i] = suffArr[i] - (1 << curLen);
                if (sortedBy2[i] < 0) {
                    sortedBy2[i] += n;
                }
            }
            Arrays.fill(count, 0);
            for (int i = 0; i < n; i++) {
                ++count[classes[sortedBy2[i]]];
            }
            for (int i = 1; i < curClass; i++) {
                count[i] += count[i - 1];
            }
            for (int i = n - 1; i >= 0; i--) {
                suffArr[--count[classes[sortedBy2[i]]]] = sortedBy2[i];
            }
            int[] newClasses = new int[n];
            newClasses[suffArr[0]] = 0;
            curClass = 1;
            for (int i = 1; i < n; i++) {
                int mid1 = (suffArr[i] + (1 << curLen)) % n;
                int mid2 = (suffArr[i - 1] + (1 << curLen)) % n;
                if (classes[suffArr[i]] != classes[suffArr[i - 1]] || classes[mid1] != classes[mid2]) {
                    ++curClass;
                }
                newClasses[suffArr[i]] = curClass - 1;
            }
            classes = newClasses;
        }
    }

    private static void calcLcp() {
        int[] pos = new int[n];
        for (int i = 0; i < n; i++) {
            pos[suffArr[i]] = i;
        }
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (k > 0) {
                --k;
            }
            if (pos[i] == n- 1) {
                k = 0;
            } else {
                int j = suffArr[pos[i] + 1];
                while (Math.max(i + k, j + k) < n && s.charAt(i + k) == s.charAt(j + k)) {
                    ++k;
                }
                lcp[pos[i]] = k;
            }
        }
    }

    private static void readString() throws IOException {
        in = new StreamTokenizer(new FileReader("count.in"));
        s = nextString();
        // костыль на суффикс длины 1
        char lastChar = 'a' - 1;
        s += lastChar;
        n = s.length();
    }

    private static long calcDifferentSubstrings() {
        // не считаем последний костыльный символ
        n--;
        long answer = 0;
        for (int i = 1; i < n; i++) {
            answer += (n - suffArr[i]) - lcp[i];
        }
        answer += n - suffArr[n];
        return answer;
    }

    private static void writeAnswer(long answer) throws IOException {
        try (PrintWriter pr = new PrintWriter(new FileWriter("count.out"))) {
            pr.println(answer);
        }
    }

    private static void solve() throws IOException {
        readString();
        suffArr = new int[n];
        lcp = new int[n - 1];
        calcSuffArr();
        calcLcp();
        writeAnswer(calcDifferentSubstrings());
    }

    public static void main(String[] args) throws IOException {
        solve();
    }
}
