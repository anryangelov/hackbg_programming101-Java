package week10.advanced_file_search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

public class StringFinder implements Callable<Integer>{

	private final Path path;
	private final String searchedString;
	
	public StringFinder(Path path, String searchedString) {
		this.path = path;
		this.searchedString = searchedString;
	}

	public Integer call() {
		//System.out.println(path);
		int count = 0;
		try {
		for(String line : Files.readAllLines(path)) {
			count++;
			if (line.contains(searchedString)) {
				return count;
			}
		}
		} catch (java.nio.charset.MalformedInputException e) {
			// System.out.println(path + " IS NOT UTF-8 ENCODING");
		} catch ( IOException e) {
			System.out.println(path);
			e.printStackTrace();
		}
		return null;
	}
}
