import java.io.*;
import java.util.*;

public class MTF {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("mtf.in")));
             BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("mtf.out")))) {
            String s = br.readLine();
            ArrayList<Character> list = new ArrayList<>();
            for (char c = 'z'; c >= 'a'; c--)
                list.add(c);
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (list.contains(ch)) {
                    int ans = 25 - list.indexOf(ch) + 1;
                    wr.write(ans + " ");
                    list.remove((Character) ch);
                    list.add((Character) ch);
                }
            }
        }
    }
}
