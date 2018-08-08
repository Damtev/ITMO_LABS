import java.io.*;
import java.util.ArrayList;

public class T_26 {

    static ArrayList<ArrayList<Integer>> nextSetPartition(ArrayList<ArrayList<Integer>> cur) {
        ArrayList<Integer> used = new ArrayList<>();
        int j;
        label:
        {
            for (int i = cur.size() - 1; i >= 0; i--) {
                int curSize = cur.get(i).size() - 1;
                j = cur.get(i).size() - 1;
                while (j >= 0) {
                    if (j  == curSize && used.size() > 0 && used.get(used.size() - 1) > cur.get(i).get(cur.get(i).size() - 1)) { //если можем дополнить множество
                        int min = used.get(used.size() - 1);
                        int p = used.size() - 1;
                        boolean flag = false;
                        while (p > 0 && used.get(p - 1) > cur.get(i).get(cur.get(i).size() - 1)) { //выбираем минимальный элемент в used, больший рассматриваемого
                            min = used.get(p - 1);
                            flag = true;
                            p--;
                        }
                        int index = Math.min(used.size() - 1, p);
                        cur.get(i).add(min);
                        used.remove(index);
                        break label;
                    }
                        if (used.size() > 0 && j != 0 && used.get(used.size() - 1) > cur.get(i).get(j)) { //ели можем заменить элемент из множества
                            int min = used.get(used.size() - 1);
                            int p = used.size() - 1;
                            while (p > 0 && used.get(p - 1) > cur.get(i).get(j)) { //выбираем минимальный элемент в used, больший рассматриваемого
                                min = used.get(p - 1);
                                p--;
                            }
                            int index = Math.min(used.size() - 1, p);
                            int temp = cur.get(i).get(j);
                            cur.get(i).set(j, min);
                            used.remove(index);
                            used.add(temp);
                            break label;
                        }
                    used.add(cur.get(i).get(j));
                    cur.get(i).remove(cur.get(i).size() - 1);
                    used.sort(null);
                    j--;
                }


                used.sort(null);
                cur.remove(i);
            }
        }
        used.sort(null);
        for (int i = 0; i < used.size(); i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(used.get(i));
            cur.add(temp);
        }
        return cur;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("nextsetpartition.in")));
             PrintWriter pr = new PrintWriter("nextsetpartition.out")) {
            String line;
            while (!(line = br.readLine()).equals("0 0")) {
                String[] buf = line.split(" ");
                int n = Integer.parseInt(buf[0]);
                int k = Integer.parseInt(buf[1]);
                ArrayList<ArrayList<Integer>> list = new ArrayList<>();
                for (int i = 0; i < k; i++) {
                    buf = br.readLine().split(" ");
                    ArrayList<Integer> temp = new ArrayList<>();
                    for (int j = 0; j < buf.length; j++) {
                        temp.add(Integer.parseInt(buf[j]));
                    }
                    list.add(temp);
                }
                list = nextSetPartition(list);
                pr.println(n + " " + list.size());
                for (ArrayList<Integer> temp : list) {
                    for (Integer x: temp) {
                        pr.print(x + " ");
                    }
                    pr.println();
                }
                pr.println();
                line = br.readLine();
            }
        }
    }
}
