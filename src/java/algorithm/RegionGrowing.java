package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

import mapObjects.District;
import mapObjects.Precinct;
import mapObjects.State;
import properties.PropertiesManager;
import seeding.SeedStrategy;

public class RegionGrowing extends Algorithm {

	private SeedStrategy seedStrategy;
	private static double RegionGrowingThreshold;
	private boolean completedPrimaryRun = false;
	public static boolean complete;

	public RegionGrowing(State s, ObjectiveFunction of, SeedStrategy seedStrategy) {
		super();
		this.currentState = s;
		this.objectiveFunction = of;
		this.seedStrategy = seedStrategy;
		RegionGrowingThreshold = Double.parseDouble(PropertiesManager.getInstance().getValue("RegionGrowingThreshold"));
	}

	@Override
	public void run() {
		District unassigned = this.currentState.getUnassignedDistrict();
		Stack<Move> tempMoves = primaryRun();
		while (!this.completedPrimaryRun) {
			this.currentState.reset();
			unassigned = this.currentState.getUnassignedDistrict();
			tempMoves = primaryRun();
		}
		moves.addAll(tempMoves);
		System.out.printf("Primary run complete. Moved %d precincts.\n", tempMoves.size());
		while (this.checkTerminationConditions() != true) {
			District d = this.selectDistrictToGrow(false);
			Precinct precinctToMove = d.findMovablePrecinct(currentState, objectiveFunction);
			Move tempMove = new Move(precinctToMove, unassigned, d);
			tempMove.setIsFinalized(true);
			moves.push(tempMove);
			d.addPrecinct(precinctToMove);
			unassigned.removePrecinct(precinctToMove);
		}
		complete = true;
	}

	private Stack<Move> primaryRun() {
		District unassigned = this.currentState.getUnassignedDistrict();
		Stack<Move> tempMoves = new Stack<Move>();
		tempMoves.addAll(seedStrategy.seed(currentState));
		while (unassigned.getPrecincts().size() > currentState.getNumPrecincts() * RegionGrowingThreshold) {
			District d = this.selectDistrictToGrow(true);
			if (d == null)
				return null;
			Precinct precinctToMove = d.getSmallestPopulationCandidate(true);
			Move tempMove = new Move(precinctToMove, unassigned, d);
			tempMove.setIsFinalized(true);
			tempMoves.push(tempMove);
			d.addPrecinct(precinctToMove);
			unassigned.removePrecinct(precinctToMove);
		}
		this.completedPrimaryRun = true;
		return tempMoves;
	}

	@Override
	protected boolean checkTerminationConditions() {
		District unassigned = this.currentState.getUnassignedDistrict();
		return unassigned.getPrecincts().isEmpty();
	}

	private District selectDistrictToGrow(boolean check) {
		if (check && checkTrappedDistrict())
			return null;
		ArrayList<District> districts = new ArrayList<District>(this.currentState.getDistricts());
		District d = getLowestPopulationDistrict(districts);
		while (!hasUnassignedCandidates(d)) {
			if (districts.isEmpty())
				return null;
			districts.remove(d);
			d = getLowestPopulationDistrict(districts);
		}
		return d;
	}

	public District getLowestPopulationDistrict(ArrayList<District> districts) {
		return Collections.min(districts, Comparator.comparingInt(District::getPopulation));
	}

	private boolean hasUnassignedCandidates(District d) {
		for (Precinct p : d.getCandidates()) {
			if (p.getDistrictID() == 0) {
				return true;
			}
		}
		return false;
	}

	private boolean checkTrappedDistrict() {
		for (District d : this.currentState.getDistricts()) {
			if (!hasUnassignedCandidates(d)) {
				return true;
			}
		}
		return false;
	}

	public void setSeedStrategy(SeedStrategy seedStrategy) {
		this.seedStrategy = seedStrategy;
	}

	public boolean isComplete() {
		return complete;
	}

}
