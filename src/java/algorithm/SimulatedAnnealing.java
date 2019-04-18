package algorithm;

import mapObjects.District;
import mapObjects.Precinct;
import mapObjects.State;
import properties.PropertiesManager;

public class SimulatedAnnealing extends Algorithm {

	protected double noImprovement;
	protected static double noImprovementTolerance;
	protected static double noImprovementThreshold;

	public SimulatedAnnealing(State s, ObjectiveFunction of) {
		super();
		this.currentState = s;
		this.objectiveFunction = of;
		SimulatedAnnealing.noImprovementTolerance = Double
				.valueOf(PropertiesManager.getInstance().getValue("NoImprovementTolerance"));
		SimulatedAnnealing.noImprovementThreshold = Double
				.valueOf(PropertiesManager.getInstance().getValue("NoImprovementThreshold"));
	}

	@Override
	public void run() {
		this.currentObjectiveValue = this.objectiveFunction.calculateObjectiveFunctionValue(currentState);
		while (this.checkTerminationConditions() != true) {
			District d = this.selectDistrictToGrow();
			Precinct precinctToMove = d.findMovablePrecinct(currentState, objectiveFunction);
			if (precinctToMove != null) {
				District oldD = currentState.getDistrict(precinctToMove.getDistrictID());
				Move tempMove = new Move(precinctToMove, oldD, d);
				d.addPrecinct(precinctToMove);
				oldD.removePrecinct(precinctToMove);
				double newOFV = this.objectiveFunction.calculateObjectiveFunctionValue(currentState, tempMove);
				if (compareToTolerance(checkImprovement(newOFV))) {
					tempMove.setIsFinalized(true);
					moves.push(tempMove);
					this.currentObjectiveValue = newOFV;
					resetNoImprovement();
					d.resetTestedCandidates();
					continue;
				}
				moves.push(tempMove);
				d.removePrecinct(precinctToMove);
				oldD.addPrecinct(precinctToMove);
				d.addTestedCandidate(precinctToMove);
			}
			incrementNoImprovement();
		}
	}

	@Override
	protected boolean checkTerminationConditions() {
		return noImprovement > SimulatedAnnealing.noImprovementThreshold;
	}

	private District selectDistrictToGrow() {
		double lowestOFV = this.objectiveFunction.calculateObjectiveFunctionValue(this.currentState,
				this.currentState.getDistrict(1));
		District temp = this.currentState.getDistrict(1);
		for (District d : this.currentState.getDistricts()) {
			double tempOFV = this.objectiveFunction.calculateObjectiveFunctionValue(this.currentState, d);
			if (tempOFV < lowestOFV) {
				temp = d;
				lowestOFV = tempOFV;
			}
		}
		return temp;
	}

	private double checkImprovement(double newOFV) {
		return newOFV - this.currentObjectiveValue;
	}

	private boolean compareToTolerance(double improvement) {
		return improvement > SimulatedAnnealing.noImprovementTolerance;
	}

	private void incrementNoImprovement() {
		this.noImprovement++;
	}

	private void resetNoImprovement() {
		this.noImprovement = 0;
	}
}
