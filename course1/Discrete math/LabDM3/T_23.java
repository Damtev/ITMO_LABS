import java.io.*;

public class T_23 {

    static StringBuilder prevvector(int n, int[] vector) {
        int[] temp = new int[n];
        for (int i = 0; i < n; i++) {
            temp[i] = vector[i];
        }
        StringBuilder prev = new StringBuilder();
        n--;
        while (n >=0 && vector[n] == 0) {
            temp[n] = 1;
            n--;
        }
        if (n < 0) {
            prev.append("-");
            return prev;
        }
        else {
            temp[n] = 0;
            for (int i = 0; i < temp.length; i++) {
                prev.append(temp[i]);
            }
            return prev;
        }
    }

    static StringBuilder nextvector(int n, int[] vector) {
        int[] temp = new int[n];
        for (int i = 0; i < n; i++) {
            temp[i] = vector[i];
        }
        StringBuilder next = new StringBuilder();;
        n--;
        while (n >=0 && vector[n] == 1) {
            temp[n] = 0;
            n--;
        }
        if (n < 0) {
            next.append("-");
            return next;
        }
        else {
            temp[n] = 1;
            for (int i = 0; i < vector.length; i++) {
                next.append(temp[i]);
            }
            return next;
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("nextvector.in")));
             PrintWriter pr = new PrintWriter("nextvector.out")) {
            String buf = br.readLine();
            int n = buf.length();
            int[] vector = new int[n];
            for (int i = 0; i < n; i++) {
                vector[i] = Integer.parseInt(buf.substring(i, i + 1));
            }
            String prev = prevvector(n, vector).toString();
            String next = nextvector(n, vector).toString();
            pr.println(prev);
            pr.println(next);
        }
    }
}
