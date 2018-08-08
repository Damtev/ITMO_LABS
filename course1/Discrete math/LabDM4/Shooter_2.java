import java.io.*;

public class Shooter_2 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("shooter.in")));
             PrintWriter pr = new PrintWriter("shooter.out")) {
            String[] vars = br.readLine().split(" ");
            int n = Integer.parseInt(vars[0]);
            int m = Integer.parseInt(vars[1]);
            int k = Integer.parseInt(vars[2]);
            double[] p = new double[n];
            vars = br.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                p[i] = Double.parseDouble(vars[i]);
            }
            double denominator = 0;
            for (int i = 0; i < n; i++) {
                denominator += Math.pow(1 - p[i], m);
            }
            pr.println(Math.pow(1 - p[k - 1], m) / denominator);
        }
    }
}
