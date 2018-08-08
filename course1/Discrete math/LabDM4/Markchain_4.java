import java.io.*;

public class Markchain_4 {

    static int n;

    private static double[] matMult(double[][] a, double[] b) {
        double[] res = new double[n];
        for (int j = 0; j < n; j++) {
            double rowMultCol = 0;
            for (int k = 0; k < n; k++) {
                rowMultCol += (double) b[k] * a[k][j];
            }
            res[j] = rowMultCol;
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("markchain.in")));
             PrintWriter pr = new PrintWriter("markchain.out")) {
            n = Integer.parseInt(br.readLine());
            double[][] transitionMatrix = new double[n][n];
            double[] stateVector = new double[n];
            for (int i = 0; i < n; i++) {
                String[] vars = br.readLine().split(" ");
                stateVector[i] = (i == 0) ? 1 : 0;
                for (int j = 0; j < n; j++) {
                    transitionMatrix[i][j] = Double.parseDouble(vars[j]);
                }
            }
            double[] res = matMult(transitionMatrix, stateVector);
            for (int i = 0; i < 9999999 / (1.7 * n); i++) {
                res = matMult(transitionMatrix, res);
            }
            for (int i = 0; i < n; i++) {
                pr.println(Math.round(res[i] * 10000d) / 10000d);
            }
        }
    }
}
