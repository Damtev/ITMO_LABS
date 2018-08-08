import java.io.*;

public class Crypto_E {

    private static int size;
    private static Matrix[] tree;

    private static Matrix query(int left, int right) {
        Matrix leftRes = null;
        Matrix rightRes = null;
        left = size - 1 + left;
        right = size - 1 + right;
        while (left < right) {
            if (left % 2 == 0) {
                leftRes = Matrix.multiply(leftRes, tree[left]);
            }
            left >>= 1;
            if (right % 2 == 1) {
                rightRes = Matrix.multiply(tree[right], rightRes);
            }
            right = (right >> 1) - 1;
        }
        if (left == right) {
            leftRes = Matrix.multiply(leftRes, tree[left]);;
        }
        return Matrix.multiply(leftRes, rightRes);
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("crypto.in")));
             PrintWriter pr = new PrintWriter("crypto.out")) {
            String[] vars = br.readLine().split(" ");
            int r = Integer.parseInt(vars[0]);
            int n = Integer.parseInt(vars[1]);
            int m = Integer.parseInt(vars[2]);
            size = (Integer.bitCount(n) == 1) ? n : 1;
            while (size < n) {
                size <<= 1;
            }
            tree = new Matrix[(size << 1) - 1];
            Matrix.r = r;
            for (int i = 0; i < n; i++) {
                String line;
                while ((line = br.readLine()).isEmpty());
                String[] numbers = line.split(" ");
                int a = Integer.parseInt(numbers[0]);
                int b = Integer.parseInt(numbers[1]);
                while ((line = br.readLine()).isEmpty()) ;
                numbers = line.split(" ");
                int c = Integer.parseInt(numbers[0]);
                int d = Integer.parseInt(numbers[1]);
                tree[size - 1 + i] = new Matrix(a, b, c, d);
            }
            for (int k = size - 2; k >= 0; --k) {
                tree[k] = Matrix.multiply(tree[(k << 1) + 1], tree[(k << 1) + 2]);
            }
            for (int i = 0; i < m; i++) {
                String line;
                while ((line = br.readLine()).isEmpty());
                String[] request = line.split(" ");
                int left = Integer.parseInt(request[0]);
                int right = Integer.parseInt(request[1]);
                Matrix result = query(left - 1, right - 1);
                pr.println(result.a + " " + result.b + "\n" + result.c + " " + result.d);
            }
        }
    }

    static class Matrix {
        static int r;
        int a;
        int b;
        int c;
        int d;

        Matrix(int a, int b, int c, int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        static Matrix multiply(Matrix first, Matrix second) {
            if (first == null) {
                return second;
            } else {
                return first.multiply(second);
            }
        }

        Matrix multiply(Matrix matrix) {
            if (matrix == null) {
                return this;
            }
            int am = (a * matrix.a + b * matrix.c) % r;
            int bm = (a * matrix.b + b * matrix.d) % r;
            int cm = (c * matrix.a + d * matrix.c) % r;
            int dm = (c * matrix.b + d * matrix.d) % r;
            return new Matrix(am, bm, cm, dm);
        }

    }
}
