package search;
import java.util.ArrayList;


public class Node {
	Node parent;
	Object action;
	Object state;
	int depth;
	int id;
	static int uniqueId = 0;
	
	public Node(Object state,Node parent,Object action,int depth){
		this.parent=parent;
        this.action=action;
        this.state=state;
        this.depth=depth;
        uniqueId++;
        this.id=uniqueId;
	}
	//returns sequence of actions leading to this node
	public Object[] sequenceOfActions(){
		Object[] actions=new Object[depth];
		Node current=this;
		for (int i = 0; i < actions.length; i++) {
			actions[i]=current.action;
			current=current.parent;
		}
		return actions;
	}
	public Node[] Expand(SearchProblem problem){
		
		ArrayList<Object> possibleActions=problem.computeActions(state);
		Node[] expandedNodes=new Node[possibleActions.size()];
		
		for (int i = 0; i < expandedNodes.length; i++) {
			expandedNodes[i]=new Node(problem.transferFunction(state, possibleActions.get(i)), this,  possibleActions.get(i), depth+1);
			//TODO: path cost
		}
		return expandedNodes;
	}
	
	public int pathCost(){
		return depth;
	}

	public void printNode(){
		System.out.println("--ID-"+this.id+"-");
		System.out.println("--Depth-"+this.depth+"-");
	}
}
