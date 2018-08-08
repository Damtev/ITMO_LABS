import java.io.*;
import java.time.LocalTime;

/*class Queue {

    private int[] s = new int[50001];
    private int head = 0;
    private int tail = 0;
    private int count = 0;

    boolean isEmpty() {
        return head == tail;
    }

    void push (int x) {
        s[tail++] = x;
        count++;
    }

    int pop () {
        if (isEmpty())
            return ('-');
        else {
            count--;
            return s[head--];
        }
    }
}*/

public class H {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("saloon.in")));
             PrintWriter pr = new PrintWriter("saloon.out")) {
            int n = Integer.parseInt(br.readLine());
            LocalTime[] input = new LocalTime[n];
            int[] impat = new int[n];
            LocalTime[] output = new LocalTime[n];
            for (int i = 0; i < n; i++) {
                String[] buf = br.readLine().trim().split(" ");
                input[i] = LocalTime.of(Integer.parseInt(buf[0]), Integer.parseInt(buf[1]));
                impat[i] = Integer.parseInt(buf[2]);
            }
            for (int i = 0; i < n; i++) {
                int count = 0;
                int delta = 0;
                for (int j = i - 1; j >= 0; j--) {
                    if (output[j].compareTo(input[i]) > 0) {
                        count++;
                        delta = i - j;
                    }
                }
                if (impat[i] < count) {
                    output[i] = input[i];
                }
                else {
                    output[i] = input[i].plusMinutes(20);
                    for (int j = i - 1; j >= i - delta; j--)
                        if (output[j].compareTo(input[i]) > 0)
                        {
                            output[i] = output[j].plusMinutes(20);
                            break;
                        }
                }
            }
            for (int i = 0; i < n; i++) {
                pr.println(output[i].getHour() + " " + output[i].getMinute());
            }
        }
    }
}
