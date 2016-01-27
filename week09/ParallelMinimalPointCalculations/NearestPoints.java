package week09.ParallelMinimalPointCalculations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearestPoints {
	
	private static Map<Point, Point> points = new HashMap<Point, Point>();
	
	public static Map<Point, Point> get(List<Point> generatedPoints) {
		double lowestDis;
		double currDis;
		for (Point point : generatedPoints) {
			points.put(point, null);
			lowestDis = 100_000_000_000.0; // some big double;
			for (Point currPoint : generatedPoints) {
				if (point == currPoint) {
					continue;
				}
				currDis = point.getDistance(currPoint);
				if (currDis < lowestDis) {
					lowestDis = currDis;
					points.replace(point, currPoint);
				}
			}
		}
		return points;
	}
	
	
	
	public static void main(String[] args) {
		
		Map<Point,Point> points = get(GeneratePoints.getRandomPoints(1000));
		int count = 0;
		for (Map.Entry<Point,Point> entry : points.entrySet()) {
			count++;
			System.out.println(entry);
			if (count > 10) {
				break;
			}
		}
	}

}
