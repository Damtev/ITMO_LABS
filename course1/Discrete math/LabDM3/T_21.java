import java.io.*;
import java.util.ArrayList;

public class T_21 {

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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("num2part.in")));
             PrintWriter pr = new PrintWriter("num2part.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            long r = Long.parseLong(buf[1]);
            ArrayList<Integer> first = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                first.add(1);
            }
            for (int i = 0; i < r; i++) {
                first = next(first);
            }
            for (int i = 0; i < first.size(); i++) {
                pr.print(first.get(i));
                if (i != first.size() - 1) {
                    pr.print('+');
                }
            }
        }
    }
}
