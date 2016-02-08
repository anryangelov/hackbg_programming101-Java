package week10.advanced_file_search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileSearch {

	private final Path dir;
	private final String searchedString;
	private ExecutorService exec;

	private final class FileResult {
		public final String file;
		public final Future<Integer> result;

		public FileResult(String file, Future<Integer> result) {
			this.file = file;
			this.result = result;
		}
	}

	public final class FileLineNumber {
		public final String file;
		public final int lineNumber;

		public FileLineNumber(String file, int lineNumber) {
			this.file = file;
			this.lineNumber = lineNumber;
		}
	}

	private final List<FileResult> results;
	private final List<FileLineNumber> founded;
	private int nThreads;

	public FileSearch(String dir, String searchedString, int nThreads) {
		this.nThreads = nThreads;
		this.dir = Paths.get(dir);
		this.searchedString = searchedString;
		if (this.nThreads > 1) {
			this.exec = Executors.newFixedThreadPool(nThreads - 1);
		}
		results = new ArrayList<>();
		founded = new ArrayList<>();

	}

	private void traverseRecur(Path dir) throws IOException {
		for (Path path : Files.newDirectoryStream(dir)) {
			if (Files.isRegularFile(path)
					&& path.toString().toLowerCase().endsWith("")) {
				StringFinder task = new StringFinder(path, searchedString);
				if (nThreads == 1) {
					Integer res = task.call();
					if (res != null) {
						founded.add(new FileLineNumber(path.toString(), res));
					}
				} else {
					Future<Integer> result = exec.submit(task);
					results.add(new FileResult(path.toString(), result));
				}
			} else if (Files.isDirectory(path)
					&& (path.toString() != dir.toString())) {
				// System.out.println(path);
				traverseRecur(path);
			}
		}
	}

	private void traverse() throws IOException {
		traverseRecur(dir);
		if (nThreads > 1) {
			exec.shutdown();
		}
	}

	private List<FileLineNumber> getThreadedResults()
			throws ExecutionException, InterruptedException, IOException {
		for (FileResult fileResult : results) {
			String file = fileResult.file;
			Integer lineNumber = fileResult.result.get();
			if (lineNumber != null) {
				founded.add(new FileLineNumber(file, lineNumber));
			}
		}
		return founded;
	}

	public List<FileLineNumber> getResult() throws ExecutionException,
			InterruptedException, IOException {
		traverse();
		if (nThreads > 1) {
			return getThreadedResults();
		}
		return founded;
	}
}
