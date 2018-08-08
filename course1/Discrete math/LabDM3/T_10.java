import java.io.*;
import java.util.ArrayList;

public class T_10 {

    static ArrayList<Integer> next(ArrayList<Integer> list) {
        list.set(list.size() - 1, list.get(list.size() - 1) - 1);
        list.set(list.size() - 2, list.get(list.size() - 2) + 1);
        if (list.get(list.size() - 2) > list.get(list.size() - 1)) {
            list.set(list.size() - 2, list.get(list.size() - 2) + list.get(list.size() - 1));
            list.remove(list.size() - 1);
        }
        else {
            while (list.get(list.size() - 2) * 2 <= list.get(list.size() - 1)) {
                int temp = list.get(list.size() - 1) - list.get(list.size() - 2);
                list.remove(list.size() - 1);
                list.add(list.get(list.size() - 1));
                list.add(temp);
            }
        }
        return list;
    }

        public static void main(String[] args) throws IOException {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("partition.in")));
                 PrintWriter pr = new PrintWriter("partition.out")) {
                int n = Integer.parseInt(br.readLine());
                ArrayList<Integer> first = new ArrayList<>();
                for (int i = 0; i < n - 1; i++) {
                    first.add(1);
                    pr.print(1 + "+");
                }
                first.add(1);
                pr.print(1);
                pr.println();
                ArrayList<Integer> next;
                while ((next = next(first)).size() != 1) {
                    for (int i = 0; i < next.size() - 1; i++) {
                        pr.print(next.get(i) + "+");
                    }
                    pr.print(next.get(next.size() - 1));
                    pr.println();
                }
                pr.print(n);
            }
        }
}