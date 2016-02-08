package week10.advanced_file_search;

public class Main {
	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		FileSearch searcher = new FileSearch("/home/angel", "lssss", 1);
		for (FileSearch.FileLineNumber res : searcher.getResult()) {
			System.out.println(res.file + " - " + res.lineNumber);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
