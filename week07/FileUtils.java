//Write comments :)

package week07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

	private static FileUtils instance;

	private FileUtils() {
	}

	
	public static FileUtils getInstance() {
		if (instance == null) {
			instance = new FileUtils();
		}
		return instance;
	}

	public String readFrom(Path path) throws IOException {
		return readFrom(path.toFile());
	}

	public String readFromNIO(File file) throws IOException {
		String res = "";
		BufferedReader reader = Files.newBufferedReader(file.toPath());
		String line;
		try {
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				res += line;
			}
		} finally {
			reader.close();
		}
		return res;
	}

	public String readFrom(File file) throws IOException {
		String res = "";
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));
		String line;
		try {
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				res += line;
			}
		} finally {
			reader.close();
		}
		return res;
	}

	public static void main(String[] Args) {
		FileUtils f = FileUtils.getInstance();
		try {
			System.out.println(f.readFrom(Paths.get("/home/angel/mytmp/note.txt")));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
