package week10.count_odd_numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	
	
	public static void main(String[] args) throws InterruptedException{
		
		Random random = new Random();
		
		List<Integer> numbers = new ArrayList<>();
		for (int i = 0; i < 50_000_001; i++) {
			numbers.add(random.nextInt());
		}
		int count = 0;
		for (Integer integer : numbers) {
			if ((integer % 2) == 0) {
				count++;
			}
		}
		System.out.println("singleThreadedResult: " + count);

		long start = System.currentTimeMillis(); 
		
		int res = CountOddNumbers.count(numbers);
		
		long end = System.currentTimeMillis();
		
		System.out.println(res);

		System.out.println(end - start);

	}
}
