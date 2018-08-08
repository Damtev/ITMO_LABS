import java.io.*;
import java.util.*;

public class Queue {
	
	static int heapsize = 0;
	static ArrayList<Integer> a = new ArrayList<>();
	
	public static void push(int x) {
		heapsize++;
		a.add(x);
		if (heapsize != 1)
			siftUp(heapsize - 1);
	}
	
	public static void siftDown(int i) {
		while (2 * i + 1 < heapsize) {
			int left = 2 * i + 1;
			int right = 2 * i + 2;
			int min = left;
			if (right < heapsize && a.get(right) < a.get(left))
				min = right;
			if (a.get(i) <= a.get(min))
				break;
			swap(i, min);
			i = min;
		}
	}
	
	public static void siftUp(int i) {
		while (a.get(i) < a.get((i - 1) / 2)) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}
	
	public static int extract_min() {
		int min = a.get(0);
		if (heapsize != 1) {
			a.set(0, a.get(heapsize - 1));
			a.remove(heapsize - 1);
			heapsize--;
			siftDown(0);
		}
		else {
			a.clear();
			heapsize--;
		}
		return min;
	}
	
	public static void swap(int i, int j) {
		int temp = a.get(i);
		a.set(i, a.get(j));
		a.set(j, temp);
	}
	
	public static void main(String[] args) throws IOException {
		Map<Integer, Integer> values = new HashMap<>(); 
		Map<Integer, Integer> indexes = new HashMap<>(); 
		int num = 1;
		try (BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("priorityqueue.in")));
		BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("priorityqueue.out")))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] commands = line.split(" ");
				switch (commands[0]) {
					case "push" : push(Integer.parseInt(commands[1]));
								  values.put(num, Integer.parseInt(commands[1]));
								  indexes.put(Integer.parseInt(commands[1]), num);
								  break;
					case "extract-min" : if (heapsize > 0) {
											int min = extract_min();
											wr.write(String.valueOf(min));
											wr.newLine();
											values.remove(indexes.get(min));
										 }
										 else {
											wr.write("*");
											wr.newLine();
										 }
										 break;
					case "decrease-key"	: if (values.containsKey(Integer.parseInt(commands[1]))) {
											  int value = values.get(Integer.parseInt(commands[1]));
											  int index = a.indexOf(value);
											  if (index >= 0) {
												  a.set(index, Integer.parseInt(commands[2]));
												  siftUp(index);
												  values.put(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
											  }
										  }
										  break;
				}
				num++;
			}
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}	
	}	
}	