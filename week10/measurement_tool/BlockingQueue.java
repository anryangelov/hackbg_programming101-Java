package week10.measurement_tool;

import java.util.LinkedList;
import java.util.List;

import week09.MyUtil;

public class BlockingQueue<E> {
	/*
	 * blocked poll if no elements in queue and blocked add if maxSize reached
	 */
	List<E> queue;
	Integer maxSize;

	public BlockingQueue(Integer maxSize) {
		queue = new LinkedList<E>();
		this.maxSize = maxSize;
	}

	public synchronized void put(E element) {
		while (queue.size() >= maxSize) {
			try {
				// MyUtil.threadMessage("in wait max size reached");
				wait();
			} catch (InterruptedException e) {
			}
		}
		// MyUtil.threadMessage("adding " + element + " size: "
		//	+ (queue.size() + 1));
		if (queue.size() == 0) {
			// MyUtil.threadMessage("notify allready above 0");
			notifyAll();
		}
		queue.add(element);
	}

	public synchronized E take() {
		while (queue.size() == 0) {
			try {
				// MyUtil.threadMessage("in wait size 0");
				wait();
			} catch (InterruptedException e) {
				System.out.println("Interrupted Exception");
			}
		}
		E res = queue.remove(0);
		// MyUtil.threadMessage("poll " + res + " size: " + queue.size());
		if ((queue.size() >= maxSize - 1)) {
			// MyUtil.threadMessage("notify");
			notifyAll();
		}
		return res;
	}

}
