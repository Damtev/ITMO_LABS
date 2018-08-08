import java.io.*;
import java.util.ArrayList;

class Node {

    int data;
    Node next;
    Node prev;

    Node(int data) {
        this.data = data;
        next = null;
        prev = null;
    }

}

class Queue {

    Node head;
    Node tail;
    int count;

    Queue() {
        head = null;
        tail = null;
        count = 0;
    }

    void add(int data) {
        Node node = new Node(data);
        node.prev = tail;
        tail.next = node;
        tail = node;
    }

    void toEnd(Node node) {
        if (node.prev == null) { //это голова
            head = node.next;
            head.prev = null;
            tail.next = node;
            node.prev = tail;
            tail = node;
            tail.next = null;
        }
        else if (node.next == null) { //это хвост

        }
    }

    void remove(Node node) {
        if (node.next == null) { //это хвост
            tail = node.prev;
            tail.next = null;
        }
        else if (node.prev == null) { //это голова
            head = node.next;
            head.prev = null;
        }
        else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
}

    String print() {
        Node node = head;
        StringBuilder ans = new StringBuilder();
        while (node.next != null) {
            ans.append(node.data + " ");
            node = node.next;
            count++;
        }
        ans.append(tail.data + " ");
        count++;
        return ans.toString().trim();
    }

    int size() {
        print();
        return count;
    }

}

public class J {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("bureaucracyin.txt")));
             PrintWriter pr = new PrintWriter("bureaucracy.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            int m = Integer.parseInt(buf[1]);
            //ArrayList<Integer> list = new ArrayList<>();
            Queue queue = new Queue();
            buf = br.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    queue.head = new Node(Integer.parseInt(buf[0]));
                    queue.tail = queue.head;
                }
                else {
                    queue.add(Integer.parseInt(buf[i]));
                }
                //list.add(Integer.parseInt(buf[i]));
            }
            out:
            {
                for (int i = 0; i <= m / n; i++) {
                    Node temp = queue.head;
                    while (temp.next != null) {
                        if (m == 0) {
                            break out;
                        }
                        if (temp.data == 1) {
                            queue.remove(temp);
                            m--;
                        }
                        else {
                            temp.data--;
                            if (temp.prev == null) {
                                temp.next.prev = null;
                                queue.head = temp.next;
                                queue.tail.next = temp;
                                temp.prev = queue.tail;
                                queue.tail = temp;
                                temp.next = null;
                            }
                            else if (temp.next != null) {
                                temp.prev.next = temp.next;
                                temp.next.prev = temp.prev;
                                queue.tail.next = temp;
                                temp.prev = queue.tail;
                                queue.tail = temp;
                                temp.next = null;
                            }
                            m--;
                        }
                        temp = temp.next;
                    }
                    if (m > 0) {
                        if (temp.data == 1) {
                            queue.remove(temp);
                            m--;
                        } else {
                            temp.data--;
                            m--;
                        }
                    }
                    else {
                        break out;
                    }
                }
            }
            pr.println(queue.size());
            pr.print(queue.print());
        }
    }
}
