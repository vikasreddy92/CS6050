package assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class CF610D {
	static Set<String> points = new HashSet<String>();
	static int x1,y1,x2,y2;
	
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(System.out);
		String[] input;
		try {
			int n = Integer.parseInt(reader.readLine());
			for (int i = 0; i < n; i++) {
				input = reader.readLine().split(" ");
				x1 = Integer.parseInt(input[0]);
				y1 = Integer.parseInt(input[1]);
				x2 = Integer.parseInt(input[2]);
				y2 = Integer.parseInt(input[3]);
				getSquares();
			}
			writer.println(points.size());
			reader.close();
			writer.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void getSquares() {
		int start, end;
		if (x1 == x2) {
			if (y1 < y2) {
				start = y1;
				end = y2;
			} else {
				start = y2;
				end = y1;
			}
			for (int i = start; i <= end; i++) {
					points.add(x1+" "+i);
			}
		}
		else {
			if (x1 < x2) {
				start = x1;
				end = x2;
			} else {
				start = x2;
				end = x1;
			}
			for (int i = start; i <= end; i++) {
				points.add(i+" "+y1);
			}
		}
	}
}
