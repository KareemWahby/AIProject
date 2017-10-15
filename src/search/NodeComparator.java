package search;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

	private SearchProblem problem;
	private int heuristic;
	private boolean astar;

	public NodeComparator(SearchProblem problem, int heuristic, boolean astar) {
		this.problem = problem;
		this.heuristic = heuristic;
	}

	@Override
	public int compare(Node n1, Node n2) {

		int value1 = problem.heuristic(heuristic, n1);
		int value2 = problem.heuristic(heuristic, n2);

		if (astar) {
			value1 += n1.pathCost();
			value2 += n2.pathCost();
		}
		return value1 < value2 ? -1 : value1 > value2 ? 1 : 0;

	}

}
