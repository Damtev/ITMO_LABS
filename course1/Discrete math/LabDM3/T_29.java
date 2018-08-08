import java.io.*;
import java.util.ArrayList;

public class T_29 {

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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("nextpartition.in")));
             PrintWriter pr = new PrintWriter("nextpartition.out")) {
            String[] buf = br.readLine().split("=");
            String left = buf[0];
            String right = buf[1];
            if (left.length() == right.length()) {
                pr.println("No solution");
            }
            else {
                ArrayList<Integer> prev = new ArrayList<>();
                String[] partition = right.split("[^0-9]");
                for (int i = 0; i < partition.length; i++) {
                    prev.add(Integer.parseInt(partition[i]));
                }
                pr.print(left + "=");
                ArrayList<Integer> ans = next(prev);
                for (int i = 0; i < ans.size(); i++) {
                    pr.print(ans.get(i));
                    if (i != ans.size() - 1) {
                        pr.print("+");
                    }
                }
            }
        }
    }
}
