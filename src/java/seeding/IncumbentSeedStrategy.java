package seeding;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import algorithm.Move;
import mapObjects.District;
import mapObjects.Precinct;
import mapObjects.State;

public class IncumbentSeedStrategy implements SeedStrategy {

	@Override
	public Stack<Move> seed(State s) {
		Stack<Move> tempMoves = new Stack<Move>();
		District unassigned = s.getUnassignedDistrict();
		int numDistricts = s.getNumDistricts();
		getSeedsByRep(unassigned, numDistricts);
		for (Precinct seed : unassigned.getSeeds()) {
			District d = new District();
			s.addDistrict(d);
			d.addPrecinct(seed);
			unassigned.removePrecinct(seed);
			tempMoves.add(new Move(seed, unassigned, d));
		}
		return tempMoves;
	}

	public void getSeedsByRep(District unassigned, int numSeeds) {
		Set<Precinct> seeds = new HashSet<Precinct>();
		// for 4
		int[] reps;
		switch (numSeeds) {
		case 4:
			reps = new int[] { 492, 226, 1061, 874 };
			break;
		case 5:
			reps = new int[] { 335, 441, 703, 994, 937 };
			break;
		case 6:
			reps = new int[] { 856, 282, 1155, 1042, 993, 54 };
			break;
		case 7:
			reps = new int[] { 637, 758, 631, 1206, 285, 1050, 874 };
			break;
		default:
			reps = new int[numSeeds];
		}
		for (int i = 0; i < numSeeds;) {
			if (addSeed(unassigned.getPrecinctById(reps[i]), seeds))
				i++;
		}
		unassigned.setSeeds(seeds);
	}

	public boolean addSeed(Precinct seed, Set<Precinct> seeds) {
		if (seeds.contains(seed)) {
			return false;
		}
		seeds.add(seed);
		return true;
	}
}
