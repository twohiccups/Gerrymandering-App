package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import dataTypes.Party;
import mapObjects.District;
import mapObjects.Point;
import mapObjects.Precinct;
import mapObjects.State;

public class ObjectiveFunction {

	public Map<Metric, Double> metrics;

	public ObjectiveFunction(Map<Metric, Double> metrics) {
		this.metrics = metrics;
		normalizeMetrics();
	}

	public void normalizeMetrics() {
		Map<Metric, Double> metrics = new HashMap<Metric, Double>();
		Map<Metric, Double> normalizedMetrics = new HashMap<Metric, Double>();
		Map<Metric, Double> compactnessMetrics = new HashMap<Metric, Double>();

		metrics.put(Metric.COMPACTNESS, this.metrics.get(Metric.COMPACTNESS));
		metrics.put(Metric.EFFICIENCYGAP, this.metrics.get(Metric.EFFICIENCYGAP));
		metrics.put(Metric.POPOULATIONEQUALITY, this.metrics.get(Metric.POPOULATIONEQUALITY));
		compactnessMetrics.put(Metric.POLTSBY_POPPER, this.metrics.get(Metric.POLTSBY_POPPER));
		compactnessMetrics.put(Metric.SCHWARTZBERG, this.metrics.get(Metric.SCHWARTZBERG));
		compactnessMetrics.put(Metric.REOCK, this.metrics.get(Metric.REOCK));

		double total = 0;
		Iterator<Entry<Metric, Double>> it = metrics.entrySet().iterator();
		while (it.hasNext()) {
			total += it.next().getValue();
		}
		it = metrics.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Metric, Double> pair = it.next();
			normalizedMetrics.put(pair.getKey(), pair.getValue() / total);
		}

		total = 0;
		it = compactnessMetrics.entrySet().iterator();
		while (it.hasNext()) {
			total += it.next().getValue();
		}
		it = compactnessMetrics.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Metric, Double> pair = it.next();
			normalizedMetrics.put(pair.getKey(), pair.getValue() / total);
		}

		this.metrics = normalizedMetrics;
	}

	public void setWeight(Metric metric, double val) {
		this.metrics.put(metric, val);
	}

	public void setMetrics(HashMap<Metric, Double> metrics) {
		this.metrics = metrics;
		normalizeMetrics();
	}

	public double calculateObjectiveFunctionValue(State state, Move move) {
		if (state.getUnassignedDistrict().getID() == move.getSourceDistrict().getID()) {
			// Move is from Region Growing algorithm
			double compactness = 0;
			double popEquality = 0;
			double efficiencyGap = 0;
			if (this.metrics.get(Metric.COMPACTNESS) != 0)
				compactness = this.calcCompactnessWeighted(move.getDestinationDistrict());
			if (this.metrics.get(Metric.POPOULATIONEQUALITY) != 0)
				popEquality = this.calcPopulationEquality(state);
			if (this.metrics.get(Metric.EFFICIENCYGAP) != 0)
				efficiencyGap = this.calcEfficiencyGap(state);
			return compactness + popEquality + efficiencyGap;
		} else {
			// Move is from Simulated Annealing algorithm
			return calculateObjectiveFunctionValue(state);
		}
	}

	public double calculateObjectiveFunctionValue(State state) {
		double compactness = 0;
		double popEquality = 0;
		double efficiencyGap = 0;
		if (this.metrics.get(Metric.COMPACTNESS) != 0)
			compactness = this.calcCompactness(state);
		if (this.metrics.get(Metric.POPOULATIONEQUALITY) != 0)
			popEquality = this.calcPopulationEquality(state);
		if (this.metrics.get(Metric.EFFICIENCYGAP) != 0)
			efficiencyGap = this.calcEfficiencyGap(state);
		return compactness + popEquality + efficiencyGap;
	}

	public double calculateObjectiveFunctionValue(State state, District d) {
		return this.calcCompactnessWeighted(d) + this.calcPopulationEquality(state) + this.calcEfficiencyGap(state);
	}

	// District level
	public double calcCompactness(State state) {
		int counter = 0;
		double[] totals = { 0, 0, 0 };
		for (District d : state.getDistricts()) {
			double[] arr = calcCompactness(d);
			totals[0] += arr[0];
			totals[1] += arr[1];
			totals[2] += arr[2];
			counter++;
		}
		return this.metrics.get(Metric.COMPACTNESS) * (this.metrics.get(Metric.POLTSBY_POPPER) * totals[0] / counter
				+ this.metrics.get(Metric.SCHWARTZBERG) * totals[1] / counter
				+ this.metrics.get(Metric.REOCK) * totals[2] / counter);
	}

	public double[] calcCompactness(District d) {
		double pp = 0;
		double schwartz = 0;
		double reock = 0;
		if (this.metrics.get(Metric.POLTSBY_POPPER) != 0)
			pp = this.calcCompPP(d);
		if (this.metrics.get(Metric.SCHWARTZBERG) != 0)
			schwartz = this.calcCompSchwartz(d);
		if (this.metrics.get(Metric.REOCK) != 0)
			reock = this.calcCompReock(d);
		return new double[] { pp, schwartz, reock };
	}

	public double calcCompactnessWeighted(District d) {
		double[] temp = this.calcCompactness(d);
		return this.metrics.get(Metric.COMPACTNESS) * (this.metrics.get(Metric.POLTSBY_POPPER) * temp[0]
				+ this.metrics.get(Metric.SCHWARTZBERG) * temp[1] + this.metrics.get(Metric.REOCK) * temp[2]);
	}

	public class PointsAndArea {

		ArrayList<Point> points;
		double area;

		public PointsAndArea(ArrayList<Point> points, int area) {
			this.points = points;
			this.area = area * 0.0001;
		}
	}

	public PointsAndArea calcPointsAndArea(District d) {
		int totalArea = 0;
		ArrayList<Point> allPoints = new ArrayList<Point>();
		Iterator<Entry<Integer, Precinct>> it = d.getPrecincts().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Precinct> pair = it.next();
			totalArea += pair.getValue().getArea();
			allPoints.addAll(new ArrayList<Point>(pair.getValue().getPoints()));
		}
		return new PointsAndArea(allPoints, totalArea);
	}

	public double calcPerimeter(ArrayList<Point> points) {
		ConcaveHull concaveHull = new ConcaveHull();
		ArrayList<Point> hull = ConvexHull.convexHull(points);
		// ArrayList<Point> hull = concaveHull.calculateConcaveHull(points, 3);
		double perimeter = concaveHull.euclideanDistance(hull.get(0), hull.get(hull.size() - 1));
		for (int i = 0; i < hull.size() - 1; i++) {
			double distance = concaveHull.euclideanDistance(hull.get(i), hull.get(i + 1));
			perimeter += distance;
		}
		return perimeter;
	}

	public double calcCompPP(District d) {
		PointsAndArea pa = calcPointsAndArea(d);
		double perimeter = calcPerimeter(pa.points);
		return (4 * Math.PI * pa.area) / Math.pow(perimeter, 2);
	}

	public double calcCompSchwartz(District d) {
		PointsAndArea pa = calcPointsAndArea(d);
		double C = 2 * Math.PI * Math.sqrt(pa.area / Math.PI);
		return 1 / (calcPerimeter(pa.points) / C);
	}

	public double calcCompReock(District d) {
		PointsAndArea pa = calcPointsAndArea(d);
		return pa.area / SmallestEnclosingCircle.makeCircle(pa.points).getArea();
	}

	// State level
	public double calcPopulationEquality(State state) {
		double totalPop = 0;
		double[] normalized = new double[state.getNumDistricts() + 1];
		for (int i = 1; i <= state.getNumDistricts(); i++) {
			totalPop += state.getDistrict(i).getPopulation();
			normalized[i] = state.getDistrict(i).getPopulation();
		}
		double maxDeviation = (int) ((totalPop / state.getNumDistricts()) * 1.005);
		double perfectPop = totalPop / state.getNumDistricts();
		for (int i = 1; i < normalized.length; i++) {
			double value = Math.abs(normalized[i] - perfectPop) / (maxDeviation - perfectPop);
			normalized[i] = value > 1 ? 1 : value;
		}
		double total = 0;
		for (double d : normalized) {
			total += d;
		}
		return this.metrics.get(Metric.POPOULATIONEQUALITY) * total / state.getDistricts().size();
	}

	// State level
	public double calcEfficiencyGap(State state) {
		double[] efficiencyGaps = new double[state.getNumDistricts() + 1];
		for (int i = 1; i <= state.getNumDistricts(); i++) {
			int totalDem = 0;
			int totalRep = 0;
			Map<Integer, Precinct> temp = new HashMap<Integer, Precinct>(state.getDistrict(i).getPrecincts());
			for (Precinct p : temp.values()) {
				double tempDem = p.getVotingData().getPartResults(Party.DEMOCRATIC) / 100;
				double tempRep = p.getVotingData().getPartResults(Party.REPUBLICAN) / 100;
				if (tempDem + tempRep > 1) {
					double overage = (tempDem + tempRep) - 1;
					tempDem = tempDem - overage;
					tempRep = tempRep - overage;
				}
				totalDem += (int) (p.getPopulation() * 0.55) * tempDem;
				totalRep += (int) (p.getPopulation() * 0.55) * tempRep;
			}
			// Negative means Rep win, Positive means Dem win
			int difference = totalDem - totalRep;
			int wastedDem = 0;
			int wastedRep = 0;
			if (difference < 0) {
				wastedDem = totalDem;
				wastedRep = totalRep - totalDem;
			} else {
				wastedDem = totalRep;
				wastedRep = totalDem - totalRep;
			}
			efficiencyGaps[i] = Math.abs((wastedDem - wastedRep) / (totalDem + totalRep) - 1);
		}
		double[] normalized = new double[efficiencyGaps.length];
		for (int i = 1; i < normalized.length; i++) {
			normalized[i] = (efficiencyGaps[i] - Arrays.stream(efficiencyGaps).min().getAsDouble())
					/ (Arrays.stream(efficiencyGaps).max().getAsDouble()
							- Arrays.stream(efficiencyGaps).min().getAsDouble());
		}
		return this.metrics.get(Metric.EFFICIENCYGAP) * Arrays.stream(normalized).average().getAsDouble();
	}

}
