import java.io.*;
import java.util.Stack;

public class T_20 {

    static long brackets2num2(int n, String brackets, long[][] d) {
        long number = 0;
        int depth = 0;
        Stack<Character> stack = new Stack<>();
        //if (n != 1) {
            for (int i = 0; i < 2 * n; i++) {
                if (brackets.charAt(i) == '(') {
                    depth++;
                    stack.push(')');
                }
                else if (brackets.charAt(i) == ')') {
                    if (depth < n) {
                        number += d[2 * n - i - 1][depth + 1] * Math.pow(2, (2 * n - i - 1 - depth - 1) / 2);
                    }
                    depth--;
                }
                else if (brackets.charAt(i) == '[') {
                    if (depth < n) {
                        number += d[2 * n - i - 1][depth + 1] * Math.pow(2, (2 * n - i - 1 - depth - 1) / 2);
                    }
                    long temp = 0;
                    if (depth > 0) {
                       temp =  d[2 * n - i - 1][depth - 1] * (long) Math.pow(2, (2 * n - i - 1 - depth + 1) / 2);
                    }
                    if (!stack.empty() && stack.peek() == '[') {
                        temp = 0;
                    }
                    number += temp;
                    stack.push('[');
                    depth++;
                }
                else if (brackets.charAt(i) == ']') {
                    if (depth < n) {
                        number += 2 * d[2 * n - i - 1][depth + 1] * Math.pow(2, (2 * n - i - 1 - depth - 1) / 2);
                    }
                    long temp = 0;
                    if (depth > 0) {
                        temp =  d[2 * n - i - 1][depth - 1] * (long) Math.pow(2, (2 * n - i - 1 - depth + 1) / 2);
                    }
                    if (!stack.empty() && stack.peek() == '[') {
                        temp = 0; //не можем поставить ) к [
                    }
                    number += temp;
                    stack.pop();
                    depth--;
                }
            }
        //}
        /*else {
            if (brackets.charAt(0) == '[') {
                number++;
            }
        }*/
        return number;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("brackets2num2.in")));
             PrintWriter pr = new PrintWriter("brackets2num2.out")) {
            String brackets = br.readLine();
            int n = brackets.length() / 2;
            long[][] d = new long[2 * n + 1][n + 1];
            d[0][0] = 1;
            for (int i = 0; i < 2 * n; i++) {
                for (int j = 0; j <= n; j++) {
                    if (j + 1 <= n) {
                        d[i + 1][j + 1] += d[i][j];
                        int first = i + 1;
                        int second = j + 1;
                        long ans = d[i + 1][j + 1];
                        int temp = 0;
                    }
                    if (j > 0) {
                        d[i + 1][j - 1] += d[i][j];
                        int first = i + 1;
                        int second = j - 1;
                        long ans = d[i + 1][j - 1];
                        int temp = 0;
                    }
                }
            }
//            for (int i = 1; i < 2 * n; i++) {
//                for (int j = 0; j < 2 * n - 1; j++) {
//                    if (j != 0) {
//                        d[i][j] = d[i - 1][j - 1] + d[i - 1][j + 1];
//                    }
//                    else {
//                        d[i][j] = d[i - 1][j + 1];
//                    }
//                }
//            }
            pr.println(brackets2num2(n, brackets, d));
        }
    }
}
