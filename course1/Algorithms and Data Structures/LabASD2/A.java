import java.io.*;
import java.util.*;

public class A {

    public static void main(String[] args) throws IOException {
        ArrayList<Character> s = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("decode.in")));
             BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("decode.out")))) {
            int ch = 0;
            while ((ch = br.read()) != -1) {
                s.add((char) ch);
                while (s.size() > 1 && (s.get(s.size() - 1) == s.get(s.size() - 2))) {
                    s.remove(s.size() - 1);
                    s.remove(s.size() - 1);
                }
            }
            for (int i = 0; i < s.size(); i++)
                wr.write(s.get(i));
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERROR" + e.getMessage());
        }
    }
}