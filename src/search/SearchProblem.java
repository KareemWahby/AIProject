package search;

import java.util.ArrayList;

public abstract class SearchProblem {
	Object initialState;
	Object[] actions;

	public abstract Object transferFunction(Object state, Object action);

	public abstract ArrayList<Object> computeActions(Object state);

	public abstract boolean goalTest(Object state);

	public abstract int pathCost(Object[] actions);

	public abstract int heuristic(int hnumber, Node node);

	public abstract void printState(Object state);

}
