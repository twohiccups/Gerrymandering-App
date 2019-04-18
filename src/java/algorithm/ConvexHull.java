package algorithm;

import java.util.ArrayList;

import mapObjects.Point;

public class ConvexHull {
	public static ArrayList<Point> convexHull(ArrayList<Point> points) {
		// There must be at least 3 points
		int size = points.size();
		if (size < 3)
			return null;

		// Initialize Result
		ArrayList<Point> hull = new ArrayList<Point>();

		// Find the leftmost point
		int l = 0;
		for (int i = 0; i < size; i++) {
			if (points.get(i).getX() < points.get(l).getX())
				l = i;
		}

		// Start from leftmost point, keep moving
		// counterclockwise until reach the start point
		// again. This loop runs O(h) times where h is
		// number of points in result or output.
		int p = l, q;
		do {
			// Add current point to result
			hull.add(points.get(p));

			// Search for a point 'q' such that
			// orientation(p, x, q) is counterclockwise
			// for all points 'x'. The idea is to keep
			// track of last visited most counterclock-
			// wise point in q. If any point 'i' is more
			// counterclock-wise than q, then update q.
			q = (p + 1) % size;

			for (int i = 0; i < size; i++) {
				// If i is more counterclockwise than
				// current q, then update q
				if (orientation(points.get(p), points.get(i), points.get(q)) == 2)
					q = i;
			}

			// Now q is the most counterclockwise with
			// respect to p. Set p as q for next iteration,
			// so that q is added to result 'hull'
			p = q;

		} while (p != l); // While we don't come to first
							// point
		return hull;
	}

	public static int orientation(Point p, Point q, Point r) {
		double val = (q.getY() - p.getY()) * (r.x - q.x) - (q.getX() - p.getX()) * (r.getY() - q.getY());

		if (val == 0)
			return 0; // collinear
		return (val > 0) ? 1 : 2; // clock or counterclock wise
	}
}
