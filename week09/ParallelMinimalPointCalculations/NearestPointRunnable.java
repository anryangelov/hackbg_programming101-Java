package week09.ParallelMinimalPointCalculations;

import java.util.List;
import java.util.Map;
import week09.MyUtil;

public class NearestPointRunnable implements Runnable{

	private Map<Point, Point> points;
	private List<Point> genList;
	private int indexFrom;
	private int indexTo;
	
	public NearestPointRunnable(List<Point> genList,
			int indexFrom, int indexTo, Map<Point, Point> points) {
		this.points = points;
		this.genList = genList;
		this.indexFrom = indexFrom;
		this.indexTo = indexTo;
	}

	public void run() {
		MyUtil.threadMessage(String.format("starting doCalculations from index %s to index %s", indexFrom, indexTo));
		Point point;
		double lowestDis;
		double currDis;
		for (int i = indexFrom; i < indexTo; i++) {
			point = genList.get(i);
			lowestDis = 100_000_000_000.0; // some big double;
			for (Point currPoint : genList) {
				if (point == currPoint) {
					continue;
				}
				currDis = point.getDistance(currPoint);
				if (currDis < lowestDis) {
					lowestDis = currDis;
					points.put(point, currPoint);
				}
			}
		}
	}

	
	
}
