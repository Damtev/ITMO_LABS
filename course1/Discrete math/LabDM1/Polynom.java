import java.util.*;

public class Polynom {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = Integer.parseInt(in.nextLine());
		int[] values = new int[(int) Math.pow(2, n)];
		int[] temp = new int[(int) Math.pow(2, n)];
		String[] kits = new String[(int) Math.pow(2, n)];
		for (int i = 0; i < (int) Math.pow(2, n); i++) {
			String[] buf = in.nextLine().split(" ");
			kits[i] = buf[0];
			values[i] = Integer.parseInt(buf[1]);
			temp[i] = values[i];
		}
		System.out.println(kits[0] + " " + Integer.toString(values[0]));
		int left = 1;
		while (left <= (int) Math.pow(2, n) - 1) {
			//int temp = values[left - 1];
			for (int i = left; i < (int) Math.pow(2, n); i++) {
				values[i] = temp[i - 1] ^ values[i];
				//temp = values[i];//косяк
			}
			for (int i = left; i < (int) Math.pow(2, n); i++) {
				temp[i] = values[i];
			}
			System.out.println(kits[left] + " " + Integer.toString(values[left]));
			left++;
		}
	}
}
			