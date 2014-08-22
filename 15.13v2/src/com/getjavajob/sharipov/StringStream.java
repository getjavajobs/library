package com.getjavajob.sharipov;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StringStream {
	public void deleteString() {
		
		
		try (BufferedReader reader = new BufferedReader(new FileReader("text.txt"));
			BufferedWriter writer = new BufferedWriter(new FileWriter("text2.txt"))){
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.contains("?") == true) {
					continue;
				}
				writer.write(line);
				writer.newLine();
			}
			reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
}
