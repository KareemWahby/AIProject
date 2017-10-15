package search;
import java.util.ArrayList;
import java.util.Stack;



public class DepthFirstSearch extends SearchAlgorithm {

	@Override
	public ArrayList<Object> solve(SearchProblem problem) {
		Stack<Node> stack=new Stack<Node>();
		Node initialNode= new Node(problem.initialState, null, null, 0);
		stack.push(initialNode);
		int expandedNodesNumber=0;

		while(!stack.isEmpty()){
			Node currentNode=stack.pop();
			if(problem.goalTest(currentNode.state))
			{
				ArrayList<Object> solution=new ArrayList<Object>();
				 solution.add(0,currentNode);
				 solution.add(1, expandedNodesNumber);
				 return solution;}
			
			Node[] nodes=currentNode.Expand(problem);
			expandedNodesNumber+=nodes.length;
			for (int i = 0; i < nodes.length; i++) {
				nodes[i].printNode();
				problem.printState(nodes[i].state);
			}
			for (int i = 0; i < nodes.length; i++) 
				stack.push(nodes[i]);
			
			
		}
		
		return null;
	}

	/*public Object[] nodePredecessors(Node node){
		Object[] nodes=new Object[node.depth];
		Node currentNode=node;
		for (int i = 0; i < nodes.length; i++) {
			currentNode=currentNode.parent;
			nodes[i]=currentNode;
		}
		
		return nodes;
	}*/
	
}
