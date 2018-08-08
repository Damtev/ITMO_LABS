import java.io.*;
import java.util.*;

class Stack_min {

    private int[] s = new int[1000001];
    private int top = -1;

    public boolean isEmpty() {
        return (top == -1);
    }

    public void push (int i) {
        if (isEmpty()) {
            s[++top] = i;
        }
        else {
            int head = top;
            s[++top] = Math.min(i, s[head]);
        }
    }

    public void pop () {
        top--;
    }

    public int get_min() {
        return s[top];
    }

}

public class D {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("stack-min.in.")));
             PrintWriter pr = new PrintWriter (new FileOutputStream("stack-min.out"))) {
            Stack_min stack = new Stack_min();
            int n = Integer.parseInt(br.readLine());
            int temp = 0;
            for (int i = 0; i < n; i++) {
                String[] s = br.readLine().split(" ");
                if (s[0].equals("1")) {
                    stack.push(Integer.parseInt(s[1]));
                }
                else if (s[0].equals("2")) {
                    stack.pop();
                }
                else if (s[0].equals("3")) {
                    pr.println(String.valueOf(stack.get_min()));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERROR" + e.getMessage());
        }
    }
}