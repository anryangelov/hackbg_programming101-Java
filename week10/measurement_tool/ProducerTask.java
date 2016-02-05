package week10.measurement_tool;

import java.util.concurrent.atomic.AtomicInteger;

import week09.MyUtil;

public class ProducerTask implements Runnable {

	final int max;
	int currentCount;
	AtomicInteger counter;
	BlockingQueue<Object> queue;

	
	public ProducerTask(BlockingQueue<Object> queue, AtomicInteger counter, int max)  {
		this.queue = queue;
		this.max = max;
		this.counter = counter;
	} 
	
	public void run() {
		while (true) {
			currentCount = counter.getAndIncrement(); // !!! increment MUST BE JUST BEFORE ADDING TO queue;
			queue.put(currentCount);
			if (currentCount >= max) {
				// MyUtil.threadMessage("Producer STOP currCount: " + currentCount);
				break;
			}
		}
	}
	
	

}
