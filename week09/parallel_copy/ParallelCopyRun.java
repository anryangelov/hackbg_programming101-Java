package week09.parallel_copy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ParallelCopyRun implements Runnable {

	private List<Path> files;
	private Path destDir;
	private Path sourceDir;
	
	public ParallelCopyRun(List<Path> files, Path sourceDir, Path destDir) {
		this.files = files;
		this.destDir = destDir;
		this.sourceDir = sourceDir;
		
	}

	public void run() {
		for (Path path : files) {
			try {
				Path destPath = Paths.get(path.toString().replaceFirst(sourceDir.toString(), destDir.toString()));
				// System.out.println(path + " " + destPath);
				Files.copy(path, destPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
