package algorithm;

import java.util.Stack;

import mapObjects.State;

public abstract class Algorithm {

	protected double currentObjectiveValue;
	protected ObjectiveFunction objectiveFunction;
	protected State currentState;
	protected Stack<Move> moves;
        protected int sp;
        private boolean complete;

	abstract public void run();

	abstract protected boolean checkTerminationConditions();
        
        public Algorithm() {
            moves = new Stack<>();
        }

    public Stack<Move> getMoves() {
        return moves;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }

    public double getCurrentObjectiveValue() {
        return currentObjectiveValue;
    }
    
    
        
}
