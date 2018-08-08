import java.io.*;

class List {

    private static int left;
    private static int right;

    List() {
        left = 0;
        right = 0;
    }

    public static int getLeft() {
        return left;
    }

    public static int getRight() {
        return right;
    }

    public static void setLeft(int l) {
        left = l;
    }

    public static void setRight(int r) {
        right = r;
    }

}

public class F {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("formation.in")));
             PrintWriter pr = new PrintWriter(new FileOutputStream("formation.out"))) {
            String[] buf_first = br.readLine().split(" ");
            int n = Integer.parseInt(buf_first[0]);
            //List[] a = new List[n + 1];
            //int[] a = new int[n + 1];
            int[] left = new int[n + 1];
            int[] right = new int[n + 1];
            int m = Integer.parseInt(buf_first[1]);
            int i = 0;
            int j = 0;
            //int temp1 = -1;
            //int temp2 = -1;
            //int temp3 = -1;
            //int temp4 = -1;
            //int left;
            //int right;
            //int ansl;
            //int ansr;
            for (int k = 0; k < m; k++) {
                String[] buf = br.readLine().split(" ");
                switch (buf[0]) {
                    case "left":
                        i = Integer.parseInt(buf[1]);
                        j = Integer.parseInt(buf[2]);
                        right[left[j]] = i;
                        left[i] = left[j];
                        right[i] = j;
                        left[j] = i;
                        /*left = a[j].getLeft();
                        //right = a[j].getRight();
                        a[i].setLeft(left);
                        pr.print(a[i].getLeft() + " ");
                        a[i].setRight(j);
                        pr.print(a[i].getRight() + " ");
                        a[left].setRight(i);
                        pr.print(a[left].getRight() + " ");
                        a[j].setLeft(i);
                        pr.print(a[j].getLeft() + " ");
                        temp1 = a[i].getLeft();
                        temp2 = a[i].getRight();
                        temp3 = a[j].getLeft();
                        temp4 = a[j].getRight();
                        pr.println();*/
                        break;
                    case "right":
                        i = Integer.parseInt(buf[1]);
                        j = Integer.parseInt(buf[2]);
                        left[right[j]] = i;
                        right[i] = right[j];
                        left[i] = j;
                        right[j] = i;
                        /*left = a[j].getLeft();
                        right = a[j].getRight();
                        a[i].setRight(right);
                        pr.print(a[i].getRight() + " ");
                        a[i].setLeft(j);
                        pr.print(a[i].getLeft() + " ");
                        a[right].setLeft(i);
                        pr.print(a[right].getLeft() + " ");
                        a[j].setRight(i);
                        pr.print(a[j].getRight() + " ");
                        temp1 = a[j].getLeft();
                        temp2 = a[j].getRight();
                        temp3 = a[i].getLeft();
                        temp4 = a[i].getRight();
                        pr.println();*/
                        break;
                    case "leave":
                        i = Integer.parseInt(buf[1]);
                        right[left[i]] = right[i];
                        left[right[i]] = left[i];
                        /*left = a[i].getLeft();
                        right = a[i].getRight();
                        a[left].setRight(right);
                        a[right].setLeft(left);*/
                        break;
                    case "name":
                        j = Integer.parseInt(buf[1]);
                        //ansl = a[j].getLeft();
                        //ansr = a[j].getRight();
                        pr.println(left[j] + " " + right[j]);
                }
                //List[] temp = a;
            }

        }
    }
}
