package week09.parallel_copy;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	// 2.4G, 15834 files 1 thread 72 sec, 2 thread 66 sec, 4 thread 67 sec, ( directory are copied single thread) 

	public static void main(String[] args) {

		Path sourcePath = Paths.get("/home/angel/books");
		Path destPath = Paths.get("/home/angel/mytmp/test_parallel");
		
		List<List<Path>> groupedFiles = null;
		try {
			ParallelCopy copier = new ParallelCopy(sourcePath, destPath, 2); 
			copier.copyDirs();
		    groupedFiles = copier.getGroupedFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Thread> threads = new ArrayList<Thread>();
		long start = System.currentTimeMillis();
		for (List<Path> files : groupedFiles) {
			Thread t = new Thread(new ParallelCopyRun(files, sourcePath, destPath));
			t.start();
			threads.add(t);
		}
		
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
		
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		System.out.println("Done");
	}
	
}
