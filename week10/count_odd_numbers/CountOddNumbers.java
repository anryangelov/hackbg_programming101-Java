package week10.count_odd_numbers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountOddNumbers {
	
	
	public static int count(List<Integer> numbers) throws InterruptedException {
		int cores = Runtime.getRuntime().availableProcessors();
		int [] res = new int[cores];
		ExecutorService exec = Executors.newFixedThreadPool(cores);
		int step = numbers.size() / cores;	
		int start = 0;
		int end;
		for (int i = 0; i < cores - 1; i++) {
			end = start + step; 
			res[i] = 0;
			exec.submit(new Task(numbers, start, end, res, i));
			start = end;
		}
		res[cores - 1] = 0;
		exec.submit(new Task(numbers, start, numbers.size(), res, cores - 1));
		exec.shutdown();
		exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		int result = 0;
		for (int i = 0; i < res.length; i++) {
			result += res[i];
		}
		return result;
	}

}
