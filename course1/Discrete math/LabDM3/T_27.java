import java.io.*;

public class T_27 {

    static String next(String s) {
        int close = 0;
        int open = 0;
        int mark = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                open++;
                if (close > open) {
                    mark = i;
                    break;
                }
            }
            else {
                close++;
            }
        }
        StringBuilder next = new StringBuilder(s.substring(0, mark));
        if (next.length() == 0) {
            return "-";
        }
        next.append(')');
        for (int i = 0; i < open; i++) {
            next.append('(');
        }
        for (int i = 0; i < close - 1; i++) {
            next.append(')');
        }
        return next.toString();
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("nextbrackets.in")));
             PrintWriter pr = new PrintWriter("nextbrackets.out")) {
            //PrintWriter pr = new PrintWriter("brackets.out")) {
            String s = br.readLine();
            pr.println(next(s));
        }
    }
}
