package week09;

import java.util.LinkedList;
import java.util.List;

public class MaxSizeBlockinQueue<E> {
    /*
     * blocked poll if no elements in queue and blocked add if maxSize reached
     */
	List<E> queue;
	Integer maxSize;
	
	public MaxSizeBlockinQueue (Integer maxSize) {
		queue = new LinkedList<E>();
		this.maxSize = maxSize;
	}
	
	public synchronized void add(E element) {
		while ( queue.size() >= maxSize) {
			try {
				MyUtil.threadMessage("in wait max size reached");
				wait(); 
			} catch (InterruptedException e) {} 
		}
		if (queue.size() == 0) {
			MyUtil.threadMessage("notify allready above 0");
			notifyAll();
		}
		MyUtil.threadMessage("adding " + element);
		Thread.yield();
		queue.add(element);
	}
	
	public synchronized E poll() {
		while (queue.size() == 0 ) {
			try {
				MyUtil.threadMessage("in wait size 0");
				wait();
			} catch (InterruptedException e) {}
		} 
		E res = queue.remove(0);
		if ((queue.size() >= (maxSize - 1))) {
			MyUtil.threadMessage("notify");
			notifyAll();
		}
		MyUtil.threadMessage("poll " + res);
		return res;
	}
	
	public static void main(String[] args) {
		
		MaxSizeBlockinQueue<Integer> queue = new MaxSizeBlockinQueue<>(4);
		
		Thread t0 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				queue.add(1);
				queue.add(2);
				queue.add(3);
				queue.add(4);
				queue.add(5);
				queue.add(6);
				}
		});
			
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				queue.add(7);
				queue.add(8);
				queue.add(9);
				queue.add(10);
				queue.add(11);
				queue.add(12);
				queue.add(13);
			}
		});
		
		Thread t2 = new Thread (new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					queue.poll();
					queue.poll();
					queue.poll();
					queue.poll();
					//Thread.sleep(1000);
					//Thread.yield();
					queue.poll();
					//Thread.yield();
					queue.poll();
					//Thread.yield();
					queue.poll();
					queue.poll();
					queue.poll();
				} catch (InterruptedException e) {}
			}
		});
		
		Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
				queue.poll();
				Thread.sleep(1900);
				queue.poll();
				Thread.sleep(2100);
				queue.poll();
				} catch (InterruptedException e) {}
			}
		});
		
		t0.start();
		t1.start();
		t2.start();
		t3.start();
	}
}
