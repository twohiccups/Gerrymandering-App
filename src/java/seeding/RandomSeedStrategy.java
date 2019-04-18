package seeding;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

import algorithm.Move;
import mapObjects.District;
import mapObjects.Precinct;
import mapObjects.State;

public class RandomSeedStrategy implements SeedStrategy {

	@Override
	public Stack<Move> seed(State s) {
		Stack<Move> tempMoves = new Stack<Move>();
		District unassigned = s.getUnassignedDistrict();
		int numDistricts = s.getNumDistricts();
		getSeedsRandomly(unassigned, numDistricts);
		for (Precinct seed : unassigned.getSeeds()) {
			District d = new District();
			s.addDistrict(d);
			d.addPrecinct(seed);
			unassigned.removePrecinct(seed);
			tempMoves.add(new Move(seed, unassigned, d));
		}
		return tempMoves;
	}

	private Precinct pickRandomSeed(District unassigned) {
		int rand = ThreadLocalRandom.current().nextInt(unassigned.getNumPrecincts());
		return unassigned.getPrecinctById(rand);
	}

	private void getSeedsRandomly(District unassigned, int numSeeds) {
		Set<Precinct> seeds = new HashSet<Precinct>();
		for (int i = 0; i < numSeeds;) {
			Precinct toAdd = this.pickRandomSeed(unassigned);
			if (this.addSeed(toAdd, seeds)) {
				i++;
			}
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
