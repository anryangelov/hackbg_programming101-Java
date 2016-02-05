package week10.measurement_tool;

import java.util.concurrent.atomic.AtomicInteger;

import week09.MyUtil;

public class ConsumerTask implements Runnable{

	BlockingQueue<Object> queue;
	Object stopSignal;

	public ConsumerTask(BlockingQueue<Object> queue, Object stopSignal) {
		this.queue = queue;
		this.stopSignal = stopSignal;
	}
	
	public void run() {
		Object element;
		while (true) {
			element = queue.take();
			if (element == stopSignal) {
				queue.put(stopSignal);
				break;
			}
		}
	}
}