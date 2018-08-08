import java.io.*;

public class T_9 {

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
            return "Очень жаль, вы проиграли";
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

    static void brackets(int n, String file) throws FileNotFoundException {
        try (PrintWriter pr = new PrintWriter(file)) {
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < n; i++) {
                sb.append('(');
            }
            for (int i = 0; i < n; i++) {
                sb.append(')');
            }
            pr.println(sb.toString());
            while (!(next(sb.toString()).equals("Очень жаль, вы проиграли"))) {
                pr.println(sb = new StringBuilder(next(sb.toString())));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("brackets.in")))) {
             //PrintWriter pr = new PrintWriter("brackets.out")) {
            int n = Integer.parseInt(br.readLine());
            brackets(n, "brackets.out");
        }
    }
}
