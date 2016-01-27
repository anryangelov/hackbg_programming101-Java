package week09.ParallelMinimalPointCalculations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class NearestPointsWithThreads {

	
	public static Map<Point, Point> get(List<Point> generatedPoints, int threads) throws InterruptedException {
		long start = System.currentTimeMillis();
		Map <Point,Point> points = new Hashtable<>();
		if (threads <= 0) {
			throw new IllegalArgumentException("Threads must bigger than 0");
		}
		int size = generatedPoints.size();
		int step = size / threads;
		int indexFrom = 0;
		int indexTo = step;
		List<Thread> threadsList = new ArrayList<Thread>();
		for (int i = 0; i < threads; i++) {
			if (i == threads - 1) {
				indexTo = size;
			}
			Thread t = new Thread(new NearestPointRunnable(generatedPoints, indexFrom, indexTo, points));
			threadsList.add(t);
			t.start();
			indexFrom = indexTo;
			indexTo += step;
		}
		for (Thread t : threadsList) {
			t.join();
		}
		System.out.println("Map size: " + points.size());
		long end = System.currentTimeMillis();
		long duration = end - start;
		System.out.println("time: " + duration);
		return points;
	}
	

	public static void main(String[] args) throws InterruptedException {
		Map <Point, Point> points;
		points = NearestPointsWithThreads.get(GeneratePoints.getRandomPoints(60000), 4);

		System.out.println("examples of 10 key-value pairs");
		int count = 0;
		for (Map.Entry<Point, Point> point : points.entrySet()) {
			System.out.println(point);
			count++;
			if (count > 10) {
				break;
			}
		}
	}

}
