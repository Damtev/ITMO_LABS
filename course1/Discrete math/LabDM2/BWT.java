import java.io.*;
import java.util.Arrays;

public class BWT {

    public static String[] Sort(String[] lines, int n, int m, int k) {
        int[] keys = new int[26];
        int[] pos = new int[25];
        for (int j = 0; j < 25; j++) {
            keys[j] = 0;
            pos[j] = 0;
        }
        keys[25] = 0;
        String[] s_lines = new String[n];

        for (int j = 0; j < n; j++)
            keys[(int) lines[j].charAt(m - 1) - 97]++;
        for (int j = 1; j < 25; j++)
            pos[j] = pos[j - 1] + keys[j - 1];
        for (int j = 0; j < n; j++)
            s_lines[pos[(int) lines[j].charAt(m - 1) - 97]++] = lines[j];
        if (k ==1) return s_lines;
        else {
            k--;
            m--;
            return Sort(s_lines, n, m, k);
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("bwt.in")));
             BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("bwt.out")))) {
            String s = br.readLine();
            int m = s.length();
            String[] shift = new String[m];
            shift[0] = s;
            for (int i = 1; i < m; i++) {
                StringBuilder buf = new StringBuilder();
                buf.append(shift[i - 1].charAt(m - 1));
                buf.append(shift[i - 1].substring(0, m - 1));
                shift[i] = buf.toString();
            }
            Arrays.sort(shift);
            //String[] s_shift = Sort(shift, m, m, m);
            StringBuilder ans = new StringBuilder();
            for (int i = 0; i < m; i++) {
                //ans.append(s_shift[i].charAt(m - 1));
                ans.append(shift[i].charAt(m - 1));
            }
            wr.write(ans.toString());
        }catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }catch (IOException e) {
            System.err.println("ERROR" + e.getMessage());
        }
    }
}
