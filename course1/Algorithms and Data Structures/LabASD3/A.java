import java.io.*;
import java.util.ArrayList;

class Jump {

    int coin;
    int prev;

    public Jump() {
        coin = 0;
        prev = 0;
    }

    public int getCoin() {
        return coin;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}

public class A {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
             PrintWriter pr = new PrintWriter("output.txt")) {
            String[] buf = br.readLine().split(" ");
            int n = Integer.parseInt(buf[0]);
            int k = Integer.parseInt(buf[1]);
            buf = br.readLine().split(" ");
            int[] value = new int[n + 1];
            value[1] = 0;//1 столб
            for (int i = 2; i < n; i++) {
                value[i] = (Integer.parseInt(buf[i - 2]));
            }
            value[n] = 0;//n столб
            ArrayList<Jump> dp = new ArrayList<>();
            for (int i = 0; i <= n; i++) {//делаем больше, чтобы рассматривать по номерам столбиков
                dp.add(new Jump());
            }
            for (int i = 2; i <= n; i++) {
                Jump temp = dp.get(i);
                int max = dp.get(i - 1).getCoin();
                int label = i - 1;
                int countOp = Math.min(i - 1, k);//сколько может просмотреть назад
                int j = 1;
                while (j <= countOp) {
                    if (dp.get(i - j).getCoin() >= max) {
                        max = dp.get(i - j).getCoin();
                        label = i - j;
                    }
                    j++;
                }
                int curCoin = value[i] + max;
                dp.get(i).setCoin(curCoin);
                dp.get(i).setPrev(label);
            }
            pr.println(dp.get(dp.size() - 1).getCoin());
            StringBuilder temp = new StringBuilder("");
            int last = dp.size() - 1;
            temp.append(dp.size() - 1);
            while (dp.get(last).getPrev() != 0) {
                temp.append(" " + dp.get(last).getPrev());
                last = dp.get(last).getPrev();
            }
            buf = temp.toString().trim().split(" ");
            pr.println(buf.length - 1);
            for (int i = buf.length - 1; i >= 0; i--) {
                pr.print(buf[i] + " ");
            }
        }
    }
}