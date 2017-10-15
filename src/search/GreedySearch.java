package search;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class GreedySearch extends SearchAlgorithm {

	private int heuristic;

	@Override
	public ArrayList<Object> solve(SearchProblem problem) {
		PriorityQueue<Node> queue = new PriorityQueue<Node>(50, new NodeComparator(problem, heuristic, false));
		Node initialNode = new Node(problem.initialState, null, null, 0);
		queue.add(initialNode);
		int expandedNodesNumber = 0;

		while (!queue.isEmpty()) {
			Node currentNode = queue.poll();
			if (problem.goalTest(currentNode.state)) {
				ArrayList<Object> solution = new ArrayList<Object>();
				solution.add(0, currentNode);
				solution.add(1, expandedNodesNumber);
				return solution;
			}

			Node[] nodes = currentNode.Expand(problem);
			expandedNodesNumber += nodes.length;

			for (int i = 0; i < nodes.length; i++) {
				nodes[i].printNode();
				problem.printState(nodes[i].state);
			}
			for (int i = 0; i < nodes.length; i++)
				queue.add(nodes[i]);

		}

		return null;
	}

	public void setHeuristic(int h) {
		heuristic = h;
	}

}
