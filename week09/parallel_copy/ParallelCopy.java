package week09.parallel_copy;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ParallelCopy {

	private class FileInfo implements Comparable<FileInfo> {
		public Path path;
		public Long size;
		public int thread;

		public FileInfo(Path path, Long size) {
			this.path = path;
			this.size = size;
			this.thread = 0;
		}

		public int compareTo(FileInfo p) {
			return p.size.compareTo(size);
		}

	}

	private Path sourceDir;
	private Path destDir;
	private List<FileInfo> allFiles;
	public List<Path> allDirs;
	private int numThreads;

	public ParallelCopy(Path sourceDir, Path destDir, int numThreads) throws IOException {
		this.sourceDir = sourceDir;
		this.destDir = destDir;
		this.numThreads = numThreads;
		allFiles = new ArrayList<>();
		allDirs = new ArrayList<>();
		getAllFilesAndDirs();
		prepareParallelization();
	}

	private void getAllFilesAndDirs() throws IOException {
		Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
			public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
				if (attr.isRegularFile()) {
					allFiles.add(new FileInfo(file, attr.size()));
				}
				return FileVisitResult.CONTINUE;
			}
			public FileVisitResult postVisitDirectory(Path dir,
                    IOException exc) {
				if (!dir.equals(sourceDir)) {
					allDirs.add(dir);
				}
				return FileVisitResult.CONTINUE;
			}
		});
		Collections.sort(allFiles);
	}

	private void sortDirs() {
		allDirs.sort(new Comparator<Path>() {
			@Override
			public int compare(Path o1, Path o2) {
				if ( o1.getNameCount() == o2.getNameCount()) 
					return 0;
				else if (o1.getNameCount() > o2.getNameCount()) {
					return 1; 
				}
				return -1;
			}
		});
	}
	
	public void copyDirs() throws IOException {
		sortDirs();
		//System.out.println(allDirs);
		for (Path path : allDirs) {
			//System.out.println(path);
			Path destPath = Paths.get(path.toString().replaceFirst(sourceDir.toString(), destDir.toString()));
			//System.out.println(path + " " + destPath);
			Files.copy(path, destPath);
		}
	}

	private void prepareParallelization() throws IOException{
		long[] Sizes = new long[numThreads];
		long smallestSize;
		int smallestPotion;
		for (FileInfo p : allFiles) {
			smallestSize = Sizes[0];
			smallestPotion = 0;
			for (int i = 0; i < Sizes.length; i++) {
				if (Sizes[i] < smallestSize) {
					smallestSize = Sizes[i];
					smallestPotion = i;
				}
			}
			Sizes[smallestPotion] += p.size;
			p.thread = smallestPotion;
		}
		//for (int i = 0; i < Sizes.length; i++) {
		//	System.out.println(i + " " + Sizes[i]);
		//}
	}
		
	public List<List<Path>> getGroupedFiles() throws IOException {
			List<List<Path>> files = new ArrayList<List<Path>>();
			for (int i = 0; i < numThreads; i++) {
				files.add(new ArrayList<Path>());
			}
			for (FileInfo info : allFiles) {
				files.get(info.thread).add(info.path);
			}
			return files;
	}
	

	public static void main(String[] args) throws IOException {
		ParallelCopy maker = new ParallelCopy(Paths.get("/home/angel/images"),
				Paths.get("/home/angel/mytmp"), 2);
		for (List<Path> p : maker.getGroupedFiles()) {
			System.out.println(p);
		}
	}

}
