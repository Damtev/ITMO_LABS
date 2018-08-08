import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class T_12 {

    static ArrayList<ArrayList<Integer>> nextSetPartition(ArrayList<ArrayList<Integer>> a) {
        // a — список, содержащий подмножества
        // used — список, в котором мы храним удаленные элементы
        int j = 0;
        ArrayList<Integer> used = new ArrayList<>();
        boolean fl = false;
        for (int i = a.size() - 1; i >= 0; i--) {
            if (used.size() != 0 && (used.get(used.size() - 1) > a.get(i).get(a.get(i).size() - 1))) {
            // если можем добавить в конец подмножества элемент из used
                a.get(i).add(used.get(used.size() - 1));   //добавляем
                used.remove(used.size() - 1);
                break;
            }
            for (j = a.get(i).size() - 1; j > 0; j--) {
                if (used.size() != 0 && used.get(used.size()) - 1 > a.get(i).get(j)) {
                    a.get(i).set(j, used.get(used.size()) - 1);
                    fl = true;
                    break;
                }
            }
            if (fl) {
                break;
            }
            used.add(a.get(i).get(j));   //добавляем в used j элемент i-го подмножества
            a.get(i).remove(j);   //удаляем j элемент i-го подмножества
            //далее выведем все получившиеся подмножества
        }
        used.sort(null);
        for (int i = 0; i < used.size(); i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(used.get(i));
            a.add(temp);   //добавляем лексикографически минимальных хвост
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("part2sets.in")));
             PrintWriter pr = new PrintWriter("part2sets.out")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            long k = Integer.parseInt(buf[1]);
            ArrayList<ArrayList<Integer>> first = new ArrayList<>();
            int begin = 1;
            for (int i = 0; i < k - 1; i++) {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(begin);
                first.add(temp);
                begin++;
            }
            ArrayList<Integer> temp = new ArrayList<>();
            for (int i = begin; i <= n; i++) {
                temp.add(i);
                if (i == n) {
                    first.add(temp);
                }
            }
            for (int i = 0; i < 6; i++) {
                first = nextSetPartition(first);
                for (ArrayList<Integer> cur : first) {
                    pr.println(cur.toString());
                }
                pr.println();
            }
//            for (ArrayList<Integer> cur: first) {
//                pr.println(cur.toString());
//            }
        }
    }
}
