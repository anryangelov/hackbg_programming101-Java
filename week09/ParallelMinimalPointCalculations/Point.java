package week09.ParallelMinimalPointCalculations;

public class Point {

	private final double x;
	private final double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Point p2) {
		return x == p2.x && y == p2.y;
	}
	
	public int hashcCode() {
		return Double.valueOf(x).hashCode() + Double.valueOf(y).hashCode();
	}
	
	public String toString() {
		return String.format("(x:%s y:%s)", x, y);
	}
	
	public double getDistance(Point p) {
		double xDis = Math.abs(p.x - x);
		double yDis = Math.abs(p.y - y);
		return Math.sqrt(xDis * xDis + yDis * yDis);
	}
	
	public static void main(String[] args) {
		System.out.println((new Point(1,5)).getDistance(new Point(4,1)));
	}
}
