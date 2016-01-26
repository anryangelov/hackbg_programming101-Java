package week09;

import java.util.LinkedList;
import java.util.List;


public class SimpleBlockingQueue<E> {

	private List<E> queue;
	
	public SimpleBlockingQueue() {
		queue = new LinkedList<E>();
	}
	
	
	public synchronized void add(E element) {
		queue.add(element);
		threadMessage("adding " + element);
		if (queue.size() == 1) {
			threadMessage("notify");
			notifyAll();
		}
		
	}
	
	public synchronized E poll() {
		while (queue.size() == 0) {
			try {
				threadMessage("in wait");
				wait();
			} catch (InterruptedException e) {}
		}
		E res = queue.remove(0);
		threadMessage("getting element " + res);
		return res;
	}
	
	static void threadMessage(String message) {
        String threadName =
            Thread.currentThread().getName();
        System.out.format("%s: %s%n",
                          threadName,
                          message);
	}
	
	public static void main(String[] args) {
		
		SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				queue.add(1);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {};
				queue.add(2);

			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Integer res;
				res = queue.poll();
				res = queue.poll();
			}
		});
		
		Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
				queue.add(3);
				Thread.sleep(1000);
				queue.poll();
				queue.add(4);
				} catch (InterruptedException e) {};
			}
		});
		
		t1.start();
		t2.start();
		t3.start();
	}
	
	
}
