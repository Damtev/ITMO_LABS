import java.io.*;
import java.util.*;

class Stack {

    private char[] s = new char[1000000];
    private int top = -1;

    public boolean isEmpty() {
        return top == -1;
    }

    public void push (char c) {
        s[++top] = c;
    }

    public char pop () {
        if (isEmpty())
            return ('-');
        else {
            return s[top--];
        }
    }

}

public class B {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("brackets.in")));
             PrintWriter pr = new PrintWriter (new FileOutputStream("brackets.out"))) {
            String line = br.readLine();
            String ans = "YES";
            Stack stack = new Stack();
            char temp;
            one:
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                switch (ch) {
                    case '(':
                        stack.push(ch);
                        break;
                    case '[':
                        stack.push(ch);
                        break;
                    case '{':
                        stack.push(ch);
                        break;
                    case ')':
                        temp = stack.pop();
                        if (temp != '(') {
                            ans = "NO";
                            break one;
                        }
                        break;
                    case ']':
                        temp = stack.pop();
                        if (temp != '[') {
                            ans = "NO";
                            break one;
                        }
                        break;
                    case '}':
                        temp = stack.pop();
                        if (temp != '{') {
                            ans = "NO";
                            break one;
                        }
                        break;
                    default:
                        break;
                }
            }
            if (!(stack.isEmpty()))
                ans = "NO";
            pr.print(ans);
        }
    }
}