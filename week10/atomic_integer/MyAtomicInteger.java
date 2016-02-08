package week10.atomic_integer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyAtomicInteger {
	
	
	private Integer integer;
	Lock lock;
	
	public MyAtomicInteger(Integer integer) {
		this.integer = integer;
		lock = new ReentrantLock();
	} 

	public void increment() throws InterruptedException {
		lock.lock();
		try {
			integer++;
		} finally {
			lock.unlock();
		}
	}
	
	public Integer get() {
		return integer;
	}
	
	public Integer ingrementAndGet() throws InterruptedException {
		lock.lock();
		try {
			increment();
			return integer;
		} finally {
			lock.unlock();
		}
	}
}

