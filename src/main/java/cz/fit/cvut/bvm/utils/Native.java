package cz.fit.cvut.bvm.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Native {

	public static void helloNative (){
		System.out.println("helo Native");
	}

	public static void println (String s) {
		System.out.println(s);
	}
	
	public static char[] intToCharArray(int i){
		return Integer.toString(i).toCharArray();
	}
	
	public static String[] readAllLines(String path) {
		try {
			Path p = Paths.get(path);
			List<String> lines = Files.readAllLines(p, StandardCharsets.UTF_8);
			return lines.toArray(new String[0]);
		} catch (IOException e) {
			throw new RuntimeException("chyba pri cteni ze souboru");
		}
	}
	
	public static void write(String path, String text) {
		try {
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.print(text);
			writer.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("nenalezen soubor " + path);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("nepodporovane kodovani");
		}
	}
}
