package week10.measurement_tool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

/*
 
 int ProducedElements = 50_000;
 int maxProducers = 10; 
 int maxConsumers = 10;
 int maxQueueSize = 120; int step = 5;
 
 Best reults
queueSize:81 prod:1 cons:1 time:4
queueSize:56 prod:1 cons:1 time:5
queueSize:26 prod:1 cons:1 time:6
queueSize:96 prod:2 cons:1 time:7
queueSize:101 prod:2 cons:1 time:7
queueSize:31 prod:1 cons:1 time:8
queueSize:36 prod:1 cons:1 time:8
queueSize:41 prod:1 cons:1 time:8
queueSize:41 prod:1 cons:2 time:8
queueSize:46 prod:1 cons:1 time:8
queueSize:46 prod:2 cons:1 time:8
queueSize:56 prod:1 cons:2 time:8
queueSize:56 prod:2 cons:1 time:8
queueSize:61 prod:1 cons:1 time:8
queueSize:61 prod:2 cons:1 time:8
queueSize:66 prod:1 cons:1 time:8
queueSize:66 prod:1 cons:2 time:8
queueSize:66 prod:2 cons:1 time:8
queueSize:71 prod:1 cons:1 time:8
queueSize:71 prod:1 cons:2 time:8
worst results
queueSize:1 prod:9 cons:1 time:1095
queueSize:1 prod:3 cons:10 time:902
queueSize:1 prod:2 cons:6 time:892
queueSize:1 prod:8 cons:1 time:891
queueSize:1 prod:7 cons:1 time:888
queueSize:1 prod:2 cons:10 time:828
queueSize:1 prod:10 cons:1 time:825
queueSize:1 prod:1 cons:7 time:812
queueSize:1 prod:5 cons:2 time:788
 */

public class Main {

	public static long doMeasurement(int queueSize, int elementsProduced,
			int producers, int consumers) throws InterruptedException {
		ArrayList<Thread> allProduceTreads = new ArrayList<Thread>();
		ArrayList<Thread> allConsumerTreads = new ArrayList<Thread>();
		BlockingQueue<Object> queue = new BlockingQueue<>(queueSize);
		Object signalStop = new Object();

		AtomicInteger counterProducer = new AtomicInteger(0);
		
		ProducerTask producerTask = new ProducerTask(queue, counterProducer, elementsProduced);
		ConsumerTask consumerTask = new ConsumerTask(queue, signalStop);
		
		
		long start = System.currentTimeMillis();

		for (int i = 0; i < producers; i++) {
			Thread thread = new Thread(producerTask);
			thread.start();
			allProduceTreads.add(thread);
		}

		for (int j = 0; j < consumers; j++) {
			Thread thread = new Thread(consumerTask);
			thread.start();
			allConsumerTreads.add(thread);
		}

		for (Thread t : allProduceTreads) {
			t.join();
		}
		
		queue.put(signalStop);
		
		for (Thread t : allConsumerTreads) {
			t.join();
		}

		long end = System.currentTimeMillis();
		long time = end - start;

		return time;
	}

	public static void main(String[] args) throws InterruptedException {

		int ProducedElements = 50_000;
		int maxProducers = 10;
		int maxConsumers = 10;
		int maxQueueSize = 120; int step = 5;

		long time;

		ArrayList<int[]> measurements = new ArrayList<int[]>();

		for (int i = 10; i <= maxQueueSize; i += step) {
			for (int j = 1; j <= maxProducers; j++) {
				for (int j2 = 1; j2 <= maxConsumers; j2++) {
					time = doMeasurement(i, ProducedElements, j, j2);
					System.out.println(String.format(
							"queueSize:%s prod:%s cons:%s time:%s", i, j, j2, time));
					measurements.add(new int[] { i, j, j2, (int) time });
				}
			}
		}

		measurements.sort(new Comparator<int[]>() {
			@Override
			public int compare(int[] arr1, int[] arr2) {
				if (arr1[3] > arr2[3]) {
					return 1;
				} else if (arr1[3] == arr2[3]) {
					return 0;
				}
				return -1;
			}
		});

		System.out.println("Best reults");
		int count = 0;
		for (int[] arr : measurements) {
			System.out.println(String.format(
					"queueSize:%s prod:%s cons:%s time:%s", arr[0], arr[1], arr[2], arr[3]));
			count++;
			if (count == 20) {
				break;
			}
		}
		System.out.println("worst results");
		for (int i = measurements.size() - 1; i > measurements.size() - 10; i--) {
			int [] arr = measurements.get(i);
			System.out.println(String.format(
					"queueSize:%s prod:%s cons:%s time:%s", arr[0], arr[1], arr[2], arr[3]));	
		}

	}

}
