package week10.atomic_integer;

import java.util.concurrent.atomic.AtomicInteger;


public class Main {

	private static class Task implements Runnable {

		//public static Integer c = 0;
		// public static AtomicInteger c = new AtomicInteger(0); 
		public static MyAtomicInteger c = new MyAtomicInteger(0);
		
		public void run() {
			for (int i = 0; i < 2_000_000; i++) {
				try {
					c.increment();
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
		}
		
	}
	
	
	public static void main(String[] args) throws InterruptedException {

		
		Task task = new Task(); 
		long start = System.currentTimeMillis();
		Thread t1 = new Thread(task);
		Thread t2 = new Thread(task);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
		long end = System.currentTimeMillis();
		
		System.out.println(end - start);
		
		System.out.println(task.c.get());
	}

}
