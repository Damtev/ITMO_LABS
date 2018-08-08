import java.io.*;
import java.util.*;

class Stack1 {

    private int[] s = new int[1000];
    private int top = -1;

    public boolean isEmpty() {
        return (top == -1);
    }

    public void push (int i) {
        s[++top] = i;
    }

    public int pop () {
        if (isEmpty())
            return ('-');
        else {
            return s[top--];
        }
    }

}

public class C {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("postfix.in")));
             PrintWriter pr = new PrintWriter (new FileOutputStream("postfix.out"))) {
            Stack1 stack = new Stack1();
            String[] op = br.readLine().split(" ");
            for (int i = 0; i < op.length; i++) {
                int temp = 0;
                if (Character.isDigit(op[i].charAt(0))) {
                    temp = Integer.parseInt(op[i]);
                    stack.push(temp);
                }
                else {
                    int i2 = stack.pop();
                    int i1 = stack.pop();
                    if (Objects.equals(op[i], "+")) {
                        temp = i1 + i2;
                        stack.push(temp);
                    }
                    else if (Objects.equals(op[i], "-")) {
                        temp = i1 - i2;
                        stack.push(temp);
                    }
                    else if (Objects.equals(op[i], "*")) {
                        temp = i1 * i2;
                        stack.push(temp);
                    }
                }
            }
            pr.print(String.valueOf(stack.pop()));
        }
    }
}