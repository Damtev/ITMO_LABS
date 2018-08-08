import java.util.*;
import java.io.*;

public class Horn {
	
	static int n = 0;
	
	
	public static String zero(int k, LinkedHashMap<Integer, ArrayList<Integer>> map) {
		int count = 0; //Счетчик решенных скобок
		ArrayList<Integer> alone = new ArrayList<>();//Содержит все одинокие переменные
		for (int i = 0; i < k; i++) { //Ищем одиночные переменные
			//if (map.get(i).size() == 0) return "YES"; //
			//System.out.println(map.get(i));
			if (map.get(i).contains(101)) count++; //Ищем решенные скобки
			if (map.get(i).size() == 1 && map.get(i).get(0) != 101) {
				alone.add(map.get(i).get(0));//Добавляем одиночную переменную
				map.get(i).remove(0);//Чистим одиночную переменную и ...
				map.get(i).add(101);//...пишем невозможное значение
			}
		}
		for (int i = 0; i < n; i++)
			if (alone.contains(i + 1) && alone.contains(-i - 1))
				return "YES";	
		if (count == k || alone.size() == 0) return "NO"; //Все скобки решены, формула решаема
		for (int i = 0; i < k; i++) {	
			if (map.get(i).size() > 1 ) {
				for (int j = 0; j < alone.size(); j++) {
					if (map.get(i).contains(alone.get(j))) {
						map.get(i).clear();
						map.get(i).add(101);
					}
					else if (map.get(i).contains(-alone.get(j)))
						map.get(i).remove(-alone.get(j));
				}
			}
		}
		zero(k, map);
		return "YES";
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String[] buf_first = br.readLine().split(" ");
			n = Integer.parseInt(buf_first[0]);
			int k = Integer.parseInt(buf_first[1]);
			LinkedHashMap<Integer, ArrayList<Integer>> map = new LinkedHashMap<>();
			for (int i = 0; i < k; i++) {
				String[] buf = br.readLine().split(" ");
				ArrayList<Integer> list = new ArrayList<>();
				for (int j = 0; j < n; j++) {
					if (Integer.parseInt(buf[j]) == 0)
						list.add(-j - 1);
					else if (Integer.parseInt(buf[j]) == 1)
						list.add(j + 1);
				}
				map.put(i, list);
			}
			System.out.println(zero(k, map));
		}catch (IOException e) {
			System.err.println("ERROR");
		}
	}
}