import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Dinner {

    int cost;
    int prevBalance;
//    int wasted;
    boolean used;


    public Dinner() {
        cost = 0;
        prevBalance = 0;
//        wasted = 0;
        used = false;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

//    public int getWasted() {
//        return wasted;
//    }
//
//    public void setWasted(int wasted) {
//        this.wasted = wasted;
//    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getPrevBalance() {
        return prevBalance;
    }

    public void setPrevBalance(int prevBalance) {
        this.prevBalance = prevBalance;
    }
}

public class E {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        if (n > 0) {
            int[] values = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                values[i] = Integer.parseInt(in.nextLine());
            }
            Dinner[][] dp = new Dinner[n + 1][n + 1]; //после i-го обеда у Пети будет j купонов
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= n; j++) {
                    dp[i][j] = new Dinner();
                }
            }
            for (int j = 0; j <= n; j++) {
                dp[1][j].setCost(1000000);
            }
            if (values[1] > 100) {
                dp[1][1].setCost(values[1]);
            }
            else {
                dp[1][0].setCost(values[1]);
            }
            for (int i = 2; i <= n; i++) {
                if (values[i] > 100) {
                    dp[i][n].setCost(1000000);
                    for (int j = 0; j < n; j++) { //получим купон => j > 0
                        if (j > 0) {
                            int min = dp[i - 1][j + 1].getCost();
                            int prevBalance = j + 1;
//                        int wasted = dp[i - 1][j + 1].getWasted() + 1;
                            boolean used = true;
                            if (dp[i - 1][j - 1].getCost() + values[i] <= min) {
                                min = dp[i - 1][j - 1].getCost() + values[i];
                                prevBalance = j - 1;
//                            wasted = dp[i - 1][j - 1].getWasted();
                                used = false;
                            }
                            dp[i][j].setCost(min);
                            dp[i][j].setPrevBalance(prevBalance);
//                        dp[i][j].setWasted(wasted);
                            dp[i][j].setUsed(used);
                        }
                        else {
                            dp[i][j].setCost(dp[i - 1][j + 1].getCost());
                            dp[i][j].setPrevBalance(j + 1);
//                        dp[i][j].setWasted(dp[i - 1][j + 1].getWasted() + 1);
                            dp[i][j].setUsed(true);
//                        dp[i][j] = dp[i - 1][j + 1];
                        }
                    }
                }
                else {
                    dp[i][n].setCost(1000000);
                    for (int j = 0; j < n; j++) { //не получим купон => j >= 0
                        int min = dp[i - 1][j + 1].getCost();
                        int prevBalance = j + 1;
//                    int wasted = dp[i - 1][j + 1].getWasted() + 1;
                        boolean used = true;
                        if (dp[i - 1][j].getCost() + values[i] <= min) {
                            min = dp[i - 1][j].getCost() + values[i];
                            prevBalance = j;
//                        wasted = dp[i - 1][j].getWasted();
                            used = false;
                        }
                        dp[i][j].setCost(min);
                        dp[i][j].setPrevBalance(prevBalance);
//                    dp[i][j].setWasted(wasted);
                        dp[i][j].setUsed(used);
//                    dp[i][j] = Math.min(dp[i - 1][j + 1], dp[i - 1][j] + values[i]);
                    }
                }
            }
            int min = dp[n][0].getCost();
            int label = 0;
            for (int j = 1; j <= n; j++) {
                if (dp[n][j].getCost() != 0 && dp[n][j].getCost() <= min) {
                    min = dp[n][j].getCost();
                    label = j;
                }
            }
            System.out.println(min);
            int countWasted = 0;
            ArrayList<Integer> days = new ArrayList<>();
            int prevBalance = label;
            for (int i = n + 1; i > 1; i--) {
                if (dp[i - 1][prevBalance].isUsed()) {
                    days.add(i - 1);
                    countWasted++;
                }
                prevBalance = dp[i - 1][prevBalance].getPrevBalance();
            }
            System.out.println(label + " " + countWasted);
            for (int i = days.size() - 1; i >= 0; i--) {
                System.out.println(days.get(i));
            }
//            System.out.println();
//            for (int i = 0; i <= n; i++) {
//                for (int j = 0; j < n + 1; j++) {
//                    System.out.print(dp[i][j].getCost() + " ");
//                }
//                System.out.println();
//            }
        }
        else {
            System.out.println(0);
            System.out.println("0 0");
        }
    }
}
