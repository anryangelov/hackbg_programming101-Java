package week09.thumbnail_generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CheckDIrs implements Runnable{
	
	private String dir;
	private boolean recursivly;
	private Integer with;
	private Integer high;
	
	public CheckDIrs(String dir, boolean recursivly, Integer with, Integer high) {
		this.dir = dir;
		this.recursivly = recursivly;
		this.with = with;
		this.high = high;
	}

	private void check(String dir, boolean recursivly) throws IOException {
		Path path = Paths.get(dir);
		Path thumbnailsDir = Paths.get(dir, "thumbnails");
		Files.createDirectories(thumbnailsDir);

		for (Path name : Files.newDirectoryStream(path)) {
			if (Files.isRegularFile(name)
					&& name.toString().toLowerCase().endsWith("jpg")) {
				Path newPath = Paths.get(thumbnailsDir.toString(), name
						.getFileName().toString());
				ThumbnailGenerator.gen(name.toString(), newPath.toString(),
						with, high);
			} else if (Files.isDirectory(name) && recursivly
					&& !name.getFileName().toString().equals("thumbnails")
					&& !Files.isSymbolicLink(name)) {
				check(name.toString(), recursivly);
			}
		}
	}


	@Override
	public void run() {
		try {
			check(dir, recursivly);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) throws IOException {
		CheckDIrs maker = new CheckDIrs("/home/angel/images", true, 150, 150);
		maker.run();
	}

}
