import java.io.*;

public class Exam_1 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("exam.in")));
             PrintWriter pr = new PrintWriter("exam.out")) {
            String[] vars = br.readLine().split(" ");
            int k = Integer.parseInt(vars[0]);
            int n = Integer.parseInt(vars[1]);
            double result = 0;
            for (int i = 0; i < k; i++) {
                vars = br.readLine().split(" ");
                result += Double.parseDouble(vars[0]) / n * Double.parseDouble(vars[1]) / 100;
            }
            pr.println(result);
        }
    }
}