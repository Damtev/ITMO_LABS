import java.util.*;

public class FullKit {
	
	public static int notZero(String s) {
		if (s.charAt(0) == '1') 
			return 1;
		return 0;
	}
	
	public static int notOne(String s) {
		if (s.charAt(s.length() - 1) == '0') 
			return 1;
		return 0;
	}
	
	public static int notSD(String s) {
		if (s.length() == 1) {
			return 1;
		}
		else {
			for (int i = 0; i < s.length() / 2; i++) {
				if (s.charAt(i) == s.charAt(s.length() - 1 - i))
					return 1;
			}
		}
		return 0;
	}
			
	public static int notM(String s) {
		int answer = 0;	
		int mid = s.length() / 2;//четное число
		int step = mid;
		int k = 1;
		int tostep = 0;
		while (Math.pow(2, k) <= s.length()) {
			for (int i = 0; i < k; i++) {
				//if (Integer.parseInt(s.substring(i * step, (i + 1) * step), 2) > Integer.parseInt(s.substring(mid + i * step, mid + (i + 1) * step), 2)) {
				if (Integer.parseInt(s.substring((i + tostep) * step, (i + 1 + tostep) * step), 2) > Integer.parseInt(s.substring((i + 1+ tostep) * step, (i + 2 + tostep) * step), 2)) {	
					return 1;
				}
				tostep++;
			}
			tostep = 0;
			step = step / 2;
			k++;
		}
		return answer;
	}	
	
	public static int notLinear(String s) {
		int answer = 0;
		int[] values = new int[s.length()];
		int[] temp = new int[s.length()];
		int[] kf = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			String buf = "";
			buf += s.charAt(i);
			values[i] = Integer.parseInt(buf);
			temp[i] = values[i];
		}
		kf[0] = values[0];
		int left = 1;
		while (left < s.length()) {
			for (int i = left; i < s.length(); i++) {
				values[i] = temp[i - 1] ^ values[i];
			}
			for (int i = left; i < s.length(); i++) {
				temp[i] = values[i];
			}
			kf[left] = values[left];
			left++;
		}
		for (int i = 1; i < s.length(); i++) {
			if (notPower2(i) && kf[i] == 1)
				return 1;
		}
		return answer;
	}
	
	public static boolean notPower2(int i) {
		while (i % 2 == 0) {
			i /= 2;
		}
		return (i != 1);
	}
	
	/*public static void check() {
			String s = "0000";
			 
			System.out.print(notM(s));
			 
			s = "0001";
			 
			System.out.print(notM(s));
			 
			s = "0010";
			 
			System.out.print(notM(s));
			 
			s = "0011";
			 
			System.out.print(notM(s));
			 
			s = "0100";
			 
			System.out.print(notM(s));
			 
			s = "0101";
			 
			System.out.print(notM(s));
			 
			s = "0110";
			 
			System.out.print(notM(s));
			 
			s = "0111";
			 
			System.out.print(notM(s));
			 
			s = "1000";
			 
			System.out.print(notM(s));
			 
			s = "1001";
			 
			System.out.print(notM(s));
			 
			s = "1010";
			 
			System.out.print(notM(s));
			 
			s = "1011";
			 
			System.out.print(notM(s));
			 
			s = "1100";
			 
			System.out.print(notM(s));
			 
			s = "1101";
			 
			System.out.print(notM(s));
			 
			s = "1110";
			 
			System.out.print(notM(s));
			 
			s = "1111";
			 
			System.out.print(notM(s));
			 
	}*/
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = Integer.parseInt(in.nextLine());
		String[] kits = new String[n];
		for (int i = 0; i < n; i++) {
			String[] buf = in.nextLine().split(" ");
			kits[i] = buf[1];
		}
		int[] answers = new int[5];
		for (int i = 0; i < 5; i++) {
			answers[i] = 0;
		}
		for (int i = 0; i < n; i++) {
			answers[0] += notZero(kits[i]);
			answers[1] += notOne(kits[i]);
			answers[2] += notSD(kits[i]);
			answers[3] += notM(kits[i]);
			answers[4] += notLinear(kits[i]);
		}
		String s = "YES";
		for (int i = 0; i < 5; i++) {
			if (answers[i] == 0) {
				s = "NO";
				break;
			}
		}
		System.out.println(s);
		//check();
	}
}