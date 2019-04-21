import java.io.*;
import java.util.HashSet;

public class F_Substrings3 {

    private static long[][] hashes;
    private static long[] pow;
    private static final int p = 37;
    private static StreamTokenizer in;
    private static int k;
    private static String[] strings;
    private static int maxLength;
    private static long answer;
    private static int subSize;

    private static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    private static String nextString() throws IOException {
        in.nextToken();
        return in.sval;
    }

    private static void calcPow() {
        pow = new long[maxLength];
        pow[0] = 1;
        for (int i = 1; i < maxLength; i++) {
            pow[i] = pow[i - 1] * p;
        }
    }

    private static long[] calcHash(String s) {
        int n = s.length();
        long[] hash = new long[n];
        hash[0] = (s.charAt(0) - 'a' + 1);
        for (int i = 1; i < n; i++) {
            hash[i] = hash[i - 1] * p + (s.charAt(i) - 'a' + 1);
        }
        return hash;
    }

    private static long hashSubstring(int number, int l, int r) {
        if (l == 0) {
            return hashes[number][r];
        }
        return (hashes[number][r] - hashes[number][l - 1] * pow[r - l + 1]);
    }

    private static long findSubstring(int size) {
        HashSet<Long> commonSubstrings = new HashSet<>();
        for (int i = 0; i < strings[0].length() - size + 1; i++) {
            commonSubstrings.add(hashSubstring(0, i, i + size - 1));
        }
        for (int i = 1; i < k; i++) {
            HashSet<Long> curCommonSubstrings = new HashSet<>();
            for (int j = 0; j < strings[i].length() - size + 1; j++) {
                long curHash = hashSubstring(i, j, j + size - 1);
                if (commonSubstrings.contains(curHash)) {
                    curCommonSubstrings.add(curHash);
                }

            }
            commonSubstrings = curCommonSubstrings;
        }
        return commonSubstrings.size() == 0 ? -1 : commonSubstrings.iterator().next();
    }

    private static void binSearch() {
        int left = -1;
        int right = maxLength + 1;
        int size = (left + right) / 2;
        while (left < right) {
            // костыль на невалидный size
            if (size == 0) {
                left = size + 1;
                size = (left + right) / 2;
                continue;
            }
            long tempAns = findSubstring(size);
            if (tempAns == -1) {
                right = size;
            } else {
                left = size + 1;
                answer = tempAns;
                subSize = size;
            }
            size = (left + right) / 2;
        }
    }

    public static void main(String[] args) throws IOException {
        in = new StreamTokenizer(new InputStreamReader(System.in));
//        in = new StreamTokenizer(new FileReader("F.in"));
        k = nextInt();
        strings = new String[k];
        maxLength = 0;
        for (int i = 0; i < k; i++) {
            strings[i] = nextString();
            maxLength = Math.max(maxLength, strings[i].length());
        }
        if (k == 1) {
            System.out.println(strings[0]);
        } else {
            calcPow();
            hashes = new long[k][];
            for (int i = 0; i < k; i++) {
                hashes[i] = calcHash(strings[i]);
            }
            binSearch();
            if (subSize > 0) {
                int left = 0;
                for (int i = 0; i < strings[0].length() - subSize + 1; i++) {
                    if (hashSubstring(0, i, i + subSize - 1) == answer) {
                        left = i;
                        break;
                    }
                }
                System.out.println(strings[0].substring(left, left + subSize));
            } else {
                System.out.println();
            }
        }
    }
}
