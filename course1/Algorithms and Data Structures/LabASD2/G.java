import java.io.*;
import java.util.ArrayList;

public class G {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("hospital.in")));
             PrintWriter pr = new PrintWriter(new FileOutputStream("hospital.out"))) {
            int n = Integer.parseInt(br.readLine());
            ArrayList<Integer> queue = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                String[] buf = br.readLine().split(" ");
                switch (buf[0]) {
                    case "+":
                        queue.add(Integer.parseInt(buf[1]));
                        break;
                    case "*":
                        if (queue.size() == 1 || queue.size() == 2) {
                            queue.add(1, Integer.parseInt(buf[1]));
                        }
                        else if (queue.size() % 2 == 0) {
                            queue.add((queue.size() / 2), Integer.parseInt(buf[1]));
                        }
                        else {
                            queue.add(((queue.size() - 1) / 2) + 1, Integer.parseInt(buf[1]));
                        }
                        break;
                    case "-":
                        pr.println(queue.remove(0));
                }
            }
        }
    }
}
