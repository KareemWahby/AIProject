package search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends SearchAlgorithm {

	@Override
	public ArrayList<Object> solve(SearchProblem problem) {
		Queue<Node> queue = new LinkedList<Node>();
		Node initialNode = new Node(problem.initialState, null, null, 0);
		queue.add(initialNode);
		int expandedNodesNumber = 0;

		while (!queue.isEmpty()) {
			Node currentNode = queue.remove();
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

}
