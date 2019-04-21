import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class L_Shifts {

    private static final int ALPHABET = 200;

    private static final int BEGIN = 32;

    private static String s;
    private static int n;
    private static int maxLen;
    private static int[] suffArr;
    private static int[] classes;
    private static int k;

    private static void calcSuffArr() {
        int[] count = new int[maxLen];
        for (int i = 0; i < n; i++) {
            ++count[s.charAt(i) - BEGIN];
        }
        for (int i = 1; i < ALPHABET; i++) {
            count[i] += count[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            suffArr[--count[s.charAt(i) - BEGIN]] = i;
//            suffArr[count[s.charAt(i) - 'a']] = i;
        }
        classes = new int[maxLen];
        classes[suffArr[0]] = 0;
        int curClass = 1;
        for (int i = 1; i < n; i++) {
            if (s.charAt(suffArr[i]) != s.charAt(suffArr[i - 1])) {
                ++curClass;
            }
            classes[suffArr[i]] = curClass - 1;
        }

        int[] sortedBy2 = new int[maxLen];
        int[] newClasses = new int[maxLen];
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

    private static void readString() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("shifts.in"),
                StandardCharsets.US_ASCII))) {
            s = in.readLine();
            n = s.length();
            k = Integer.parseInt(in.readLine());
        }
    }

    private static void writeAnswer() throws IOException {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter
                (new FileOutputStream("shifts.out"), StandardCharsets.US_ASCII))) {
            boolean isPossible = false;
            for (int i = 0; i < maxLen; ++i) {
                if (classes[i] + 1 == k) {
//                    pr.print(s.substring(classes[i]));
//                    pr.print(s.substring(0, classes[i]));
                    for (int j = i; j < s.length(); j++) {
                        out.write(s.charAt(j));
                    }
                    for (int j = 0; j < i; j++) {
                        out.write(s.charAt(j));
                    }
//                    pr.println();
                    isPossible = true;
                    break;
                }
            }
            if (!isPossible) {
                out.write("IMPOSSIBLE");
            }
        }
    }

    private static void solve() throws IOException {
        readString();
        maxLen = Math.max(ALPHABET, n);
        suffArr = new int[maxLen];
        calcSuffArr();
        writeAnswer();
    }

    public static void main(String[] args) throws IOException {
        solve();
    }
}
