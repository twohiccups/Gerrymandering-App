package mapObjects;

public class Point {

	public final double x;
	public final double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point subtract(Point p) {
		return new Point(x - p.x, y - p.y);
	}

	public double distance(Point p) {
		return Math.hypot(x - p.x, y - p.y);
	}

	// Signed area / determinant thing
	public double cross(Point p) {
		return x * p.y - y * p.x;
	}

	@Override
	public String toString() {
		return String.format("Point(%g, %g)", x, y);
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			if (x == ((Point) obj).getX() && y == ((Point) obj).getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		// http://stackoverflow.com/questions/22826326/good-hashcode-function-for-2d-coordinates
		// http://www.cs.upc.edu/~alvarez/calculabilitat/enumerabilitat.pdf
		int tmp = (int) (y + ((x + 1) / 2));
		return Math.abs((int) (x + (tmp * tmp)));
	}

}
