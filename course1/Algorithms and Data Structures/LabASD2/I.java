import java.io.*;

class Stack_I {

    private long[] s = new long[1000000];
    private int top = -1;

    boolean isEmpty() {
        return top == -1;
    }

    void push (long x) {
        s[++top] = x;
    }

    long pop () {
        if (isEmpty())
            return ('-');
        else {
            return s[top--];
        }
    }

}

public class I {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("hemoglobin.in")));
             PrintWriter pr = new PrintWriter("hemoglobin.out")) {
            int n = Integer.parseInt(br.readLine());
            Stack_I stack = new Stack_I();
            long[] sum = new long[1000000];
            int index = -1;
            String sub;
            long ans;
            int temp  =0;
            for (int i = 0; i < n; i++) {
                String[] buf = br.readLine().trim().split(" ");
                switch (buf[0].charAt(0)) {
                    case '+':
                        sub = buf[0].substring(1, buf[0].length());
                        stack.push(Long.parseLong(sub));
                        if (index == -1) {
                            sum[++index] = Long.parseLong(sub);
                        }
                        else if (index > -1) {
                            sum[++index] = Long.parseLong(sub) + sum[index - 1];
                        }
                        break;
                    case '?':
                        sub = buf[0].substring(1, buf[0].length());
                        if (index == 0) {
                            ans = sum[0];
                            pr.println(ans);
                        }
                        if (index > Integer.parseInt(sub) - 1) {
                            ans = sum[index] - sum[index - Integer.parseInt(sub)];
                            pr.println(ans);
                        }
                        else if (index != 0){
                            ans = sum[index];
                            pr.println(ans);
                        }
                        break;
                    case '-':
                        if (!(stack.isEmpty())) {
                            ans = stack.pop();
                            pr.println(ans);
                            index--;
                        }
                }
                temp++;
                temp++;
            }
        }
    }
}
