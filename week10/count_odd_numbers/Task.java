package week10.count_odd_numbers;

import java.util.List;

class Task implements Runnable {

	private List<Integer> numbers;
	private int start;
	private int end;
	private int [] result;
	private int possion;
	
	public Task(List<Integer> numbers, int start, int end, int [] result, int possion) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
		this.possion = possion;
		this.result = result;
	}

	public void run() {
		for (int i = start; i < end; i++) {
			if ((numbers.get(i) % 2) == 0) {
				result[possion]++;
			}
		}
		// System.out.println(start + " " + end + " " + possion + " " + result[possion]);
	}
	
}