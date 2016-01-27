package week09.ParallelMinimalPointCalculations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneratePoints {
	
	private static Random random = new Random();
	private static List<Point> points = new ArrayList<>(); 
	
	public static List<Point> getRandomPoints(int numPoint) {
		for (int i = 0; i < numPoint; i++) {
			int x = random.nextInt(10_100);
			int y = random.nextInt(10_100);
			points.add(new Point(x, y));
		}
		return points;
	}

	public static void main(String[] args) {
		List<Point> points = GeneratePoints.getRandomPoints(100_000);
		System.out.println(points.size());
		System.out.println(points.get(100_000 - 1));
	}
}
