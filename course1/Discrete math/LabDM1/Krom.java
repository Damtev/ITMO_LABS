import java.util.*;
import java.io.*;

public class Krom {
	
	static boolean[] used = new boolean[30];
	static boolean[][] matrix = new boolean[30][30];
	static int n = 0;
	static LinkedHashMap<Integer, ArrayList<Integer>> ways = new LinkedHashMap<>();
	
	public static void dfs(int v) {
		used[v] = true;
		for (int i = 0; i < 2 * n; i++) {
			if (!(used[i]) && matrix[v][i]) {
				ArrayList<Integer> list = ways.getOrDefault(v, new ArrayList<>());
				list.add(i);
				ways.put(v, list);
				dfs(i);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String[] buf = br.readLine().split(" ");
			n = Integer.parseInt(buf[0]);
			int m = Integer.parseInt(buf[1]);
			//int[][] matrix = new int[2 * n][2 * n]; //матрица смежности вида [a,b,c,~c,~b,~a][a,b,c,~c,~b,~a]
			for (int i = 0; i < 2 * n; i++)
				for (int j = 0; j < 2 * n; j++)
					matrix[i][j] = false;
			for (int i = 0; i < m; i++) {
				buf = br.readLine().split(" ");
				int i1 = Integer.parseInt(buf[0]);
				int i2 = Integer.parseInt(buf[1]);
				/*if (i1 < 0 && i2 < 0) {
					matrix[Math.abs(i1) - 1][n + Math.abs(i2) - 1] = 1;
					matrix[Math.abs(i2) - 1][n + Math.abs(i1) - 1] = 1;
				}
				if (i1 < 0 && i2 > 0) {
					matrix[Math.abs(i1) - 1][i2 - 1] = 1;
					matrix[n + i2 - 1][n + Math.abs(i1) - 1] = 1;
				}
				if (i1 > 0 && i2 < 0) {
					matrix[n + i1 - 1][n + Math.abs(i2) - 1] = 1;
					matrix[Math.abs(i2) - 1][i1 - 1] = 1;
				}
				if (i1 > 0 && i2 > 0) {
					matrix[n + i1 - 1][i2 - 1] = 1;
					matrix[n + i2 - 1][i1 - 1] = 1;
				}*/
				if (i1 < 0 && i2 < 0) {
					matrix[Math.abs(i1) - 1][2 * n - Math.abs(i2)] = true;
					matrix[Math.abs(i2) - 1][2 * n - Math.abs(i1)] = true;
				}
				if (i1 < 0 && i2 > 0) {
					matrix[Math.abs(i1) - 1][i2 - 1] = true;
					matrix[2 * n - i2][2 * n - Math.abs(i1)] = true;
				}
				if (i1 > 0 && i2 < 0) {
					matrix[2 * n - i1][2 * n - Math.abs(i2)] = true;
					matrix[Math.abs(i2) - 1][i1 - 1] = true;
				}
				if (i1 > 0 && i2 > 0) {
					matrix[2 * n - i1][i2 - 1] = true;
					matrix[2 * n - i2][i1 - 1] = true;
				}
			}
			dfs(0);
			String answer = "NO";
			for (int key : ways.keySet()) {   
				if (ways.get(key).contains(2 * n - key - 1) && ways.get(2 * n - key - 1).contains(key)) {	
					answer = "YES";
					break;
				}
			}
			System.out.println();
			//System.out.println(answer);
			for (Map.Entry entry: ways.entrySet()) {
				System.out.println(entry.getKey() + " " + entry.getValue());
			}
		}catch (IOException e) {
			System.err.println("ERROR");
		}
	}
}