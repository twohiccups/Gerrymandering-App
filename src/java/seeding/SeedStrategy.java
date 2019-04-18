package seeding;

import java.util.Stack;

import algorithm.Move;
import mapObjects.State;

public interface SeedStrategy {

	public Stack<Move> seed(State s);
}
