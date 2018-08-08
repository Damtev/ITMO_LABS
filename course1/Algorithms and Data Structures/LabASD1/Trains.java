import java.io.*;
import java.util.*;

public class Trains {
	
	
	
	public static void main(String[] args) throws IOException {
		try (BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("trains.in")));
		BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("trains.out")))) {
			
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}	
	}	
}	