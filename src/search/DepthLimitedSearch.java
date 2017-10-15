package search;
import java.util.ArrayList;
import java.util.Stack;


public class DepthLimitedSearch extends SearchAlgorithm {

	int cutoff;
	public ArrayList<Object> solve(SearchProblem problem,int c) {
		cutoff=c;
		
		return solve(problem);
	}
	
	@Override
	public ArrayList<Object> solve(SearchProblem problem) {
		Stack<Node> stack=new Stack<Node>();
		Node initialNode= new Node(problem.initialState, null, null, 0);
		stack.push(initialNode);
		int expandedNodesNumber=0;

		while (!stack.isEmpty()) {
			
			Node currentNode=stack.pop();
			if(problem.goalTest(currentNode.state))
			{
				ArrayList<Object> solution=new ArrayList<Object>();
				 solution.add(0,currentNode);
				 solution.add(1, expandedNodesNumber);
				 return solution;}
			
			Node[] nodes=currentNode.Expand(problem);
			expandedNodesNumber+=nodes.length;
			for (int ii = 0; ii < nodes.length; ii++) {
				if(nodes[ii].depth<cutoff)
				{nodes[ii].printNode();
				problem.printState(nodes[ii].state);}
			}
			for (int j = 0; j < nodes.length; j++) 
				if(nodes[j].depth<cutoff)
					stack.push(nodes[j]);
			
		}
		return null;
	}

}
