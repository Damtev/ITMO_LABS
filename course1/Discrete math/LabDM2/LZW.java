import java.io.*;
import java.util.*;

public class LZW {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("lzw.in")));
             BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("lzw.out")))) {
            StringBuilder buf = new StringBuilder(br.readLine());
            buf.append('$');
            String s = buf.toString();
            ArrayList<String> list = new ArrayList<>();
            for (char c = 'a'; c <= 'z'; c++)
                list.add(Character.toString(c));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (ch != '$') {
                    sb.append(ch);
                    if (!(list.contains(sb.toString()))) {
                        list.add(sb.toString());
                        int ans = list.indexOf(sb.substring(0, sb.length() - 1));
                        wr.write(ans + " ");
                        sb = new StringBuilder(sb.substring(sb.length() - 1));
                    }
                }
                else {
                    int ans = list.indexOf(sb.toString());
                    wr.write(String.valueOf(ans));
                }
            }
        }
    }
}
