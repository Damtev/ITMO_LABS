import java.io.*;

class Deque {

    private int[] d;
    private int head;
    private int tail;
    private int n;

    Deque(int n) {
        this.n = n;
        d = new int[n];
        head = 0;
        tail = 0;
    }

    int size() {
        if (head > tail) {
            return (n - head) + tail;
        }
        if (head == tail) {
            return 0;
        }
        return tail - head;
    }

    String toAns() {
        StringBuilder ans = new StringBuilder();
        if (head > tail) {
            for (int i = head; i < n; i++) {
                ans.append(d[i]).append(" ");
            }
            for (int i = 0; i < tail; i++) {
                ans.append(d[i]).append(" ");
            }
            return ans.toString();
        }
        for (int i = head; i < tail; i++) {
            ans.append(d[i]).append(" ");
        }
        return ans.toString();
    }

    void pushFront(int data) {
        if (head == 0) {
            head = n - 1;
            d[head] = data;
        }
        else {
            d[--head] = data;
        }
    }

    void pushBack(int data) {
        if (tail == n - 1) {
            tail = 0;
            d[tail] = data;
        }
        else {
            d[tail++] = data;
        }
    }

    int popFront() {
        if (head == n - 1) {
            head = 0;
            return d[n - 1];
        }
        return d[head++];
    }

    int popBack() {
        if (tail == 0) {
            tail = n - 1;
            return d[0];
        }
        return d[--tail];
    }

}

public class K {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("kenobi.in")));
             PrintWriter pr = new PrintWriter(new FileOutputStream("kenobi.out"))) {
            int n = Integer.parseInt(br.readLine());
            Deque left = new Deque(1000000);
            Deque right = new Deque(1000000);
            for (int i = 0; i < n; i++) {
                String[] buf = br.readLine().split(" ");
                switch (buf[0]) {
                    case "add":
                        if (left.size() < right.size()) {
                            left.pushBack(right.popFront());
                        }
                        right.pushBack(Integer.parseInt(buf[1]));
                        break;
                    case "take":
                        if ((left.size() + right.size()) != 0) {
                            right.popBack();
                            if (left.size() > right.size()) {
                                right.pushFront(left.popBack());
                            }
                        }
                        break;
                    case "mum!":
                        if ((left.size() + right.size()) > 1) {
                            Deque temp = left;
                            left = right;
                            right = temp;
                            if (left.size() > right.size()) {
                                right.pushFront(left.popBack());
                            }
                        }
                }
            }
            int size = left.size() + right.size();
            pr.println(size);
            String answer = (left.toAns().concat(right.toAns())).trim();
            pr.print(answer);
        }
    }
}
