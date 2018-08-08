import java.io.*;
import java.util.ArrayList;

public class T_4 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("chaincode.in")));
             PrintWriter pr = new PrintWriter("chaincode.out")) {
            int n = Integer.parseInt(br.readLine());
            /*StringBuilder[] chain = new StringBuilder[1000000];
            ArrayList<String> chain = new ArrayList<>();
            for (int i = 0; i < Math.pow(2, n); i++) {
                chain[i] = new StringBuilder("");
            }
            for (int i = 0; i < n; i++) {
                chain[0].append(0);
            }
            StringBuilder zero = new StringBuilder("");
            for (int i = 0; i < n; i++) {
                zero.append(0);
            }
            chain.add(zero.toString());*/
            boolean[] used = new boolean[(int) Math.pow(2, n)];
            StringBuilder old = new StringBuilder("");
            for (int i = 0; i < n; i++) {
                old.append(0);
            }
            pr.println(old.toString());
            for (int i = 0; i < Math.pow(2, n); i++) {
                used[i] = false;
            }
            used[0] = true;
            //int k = 1;
            exit:
            {
                while (true) {
                    StringBuilder temp = new StringBuilder(old.substring(1));
                    temp.append(1);
                    int digit = Integer.parseInt(temp.toString(), 2);
                    if (!(used[digit])) {
                        used[digit] = true;
                        pr.println(temp.toString());
                        old = temp;
                    }
                    else if (!(used[digit - 1])) {
                        used[digit - 1] = true;
                        pr.println(temp.toString().substring(0, temp.length() - 1) + "0");
                        old = new StringBuilder(temp.toString().substring(0, temp.length() - 1) + "0");
                    }
                    else {
                        break exit;
                    }
                    /*chain[k] = new StringBuilder(chain[k - 1].substring(1));
                    chain[k].append(1);
                    StringBuilder temp = new StringBuilder("");
                    temp = new StringBuilder(chain.get(k - 1).substring(1));
                    temp.append(1);
                    boolean used = false;
                    /*for (int i = 0; i < k; i++) {
                        if (chain[k].toString().equals(chain[i].toString())) {
                            used = true;
                            break;
                        }
                    }
                    if (chain.contains(temp.toString())) {
                    if (used) {
                        chain[k] = new StringBuilder(chain[k].substring(0, chain[k].length() - 1));
                        chain[k].append(0);
                        temp = new StringBuilder(temp.substring(0, temp.length() - 1));
                        temp.append(0);
                        for (int i = 0; i < k; i++) {
                            if (chain[k].toString().equals(chain[i].toString())) {
                                break exit;
                            }
                        }
                        if (chain.contains(temp.toString())) {
                            break exit;
                        }
                    }
                    chain.add(temp.toString());*/
                    //k++;
                }
            }
            /*for (int i = 0; i < Math.pow(2, n); i++) {
                pr.println(chain[i]);
            }
            for (String ans: chain) {
                pr.println(ans);
            }*/
        }
    }
}
