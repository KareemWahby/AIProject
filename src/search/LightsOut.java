package search;

import java.util.ArrayList;
import java.util.Random;

public class LightsOut extends SearchProblem {

	public LightsOut() {
		super();
		this.initialState = GenBoard();
	}

	public boolean[][] GenBoard() {
		Random ran = new Random();
		boolean[][] b = new boolean[5][5];
		int numberOn = ran.nextInt(25) + 1;
		for (int i = 0; i < numberOn; i++) {
			int x = ran.nextInt(25) + 1;
			int r, c;
			if (x >= 1 && x <= 5) {
				r = 0;
				c = 4 - (5 - x);
			} else if (x >= 6 && x <= 10) {
				r = 1;
				c = 4 - (10 - x);
			} else if (x >= 11 && x <= 15) {
				r = 2;
				c = 4 - (15 - x);
			} else if (x >= 16 && x <= 20) {
				r = 3;
				c = 4 - (20 - x);
			} else {
				r = 4;
				c = 4 - (25 - x);
			}
			b[r][c] = true;
		}
		return b;
	}

	@Override
	public Object transferFunction(Object state, Object action) {
		boolean[][] b = (boolean[][]) state;
		int x = (int) action;
		int r, c;
		if (x >= 1 && x <= 5) {
			r = 0;
			c = 4 - (5 - x);
		} else if (x >= 6 && x <= 10) {
			r = 1;
			c = 4 - (10 - x);
		} else if (x >= 11 && x <= 15) {
			r = 2;
			c = 4 - (15 - x);
		} else if (x >= 16 && x <= 20) {
			r = 3;
			c = 4 - (20 - x);
		} else {
			r = 4;
			c = 4 - (25 - x);
		}
		b[r][c] = !b[r][c];
		if (r != 0)
			b[r - 1][c] = !b[r - 1][c];// up
		if (r != 4)
			b[r + 1][c] = !b[r + 1][c];// down
		if (c != 4)
			b[r][c + 1] = !b[r][c + 1];// right
		if (c != 0)
			b[r][c - 1] = !b[r][c - 1];// left

		return b;
	}

	@Override
	public boolean goalTest(Object state) {
		boolean[][] b = (boolean[][]) state;
		for (boolean[] cs : b) {
			for (boolean c : cs) {
				if (c)
					return false;
			}
		}
		return true;
	}

	@Override
	public int pathCost(Object[] actions) {
		return 1;
	}

	@Override
	public int heuristic(int hnumber, Node node) {
		boolean[][] b = (boolean[][]) node.state;
		if(hnumber==1){
			int on=0;
			for (boolean[] cs : b) {
				for (boolean c : cs) {
					if(c)
						on++;
				}
			}
			return on;
		}else if(hnumber==2){
			int off=0;
			for (boolean[] cs : b) {
				for (boolean c : cs) {
					if(c)
						off++;
				}
			}
			return off;
		}else if(hnumber==3){
			
		}
		return 0;
	}

	@Override
	public void printState(Object state) {
		boolean[][] b = (boolean[][]) state;
		System.out.println("-------------------------------------");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(b[i][j] + "  |  ");
			}
			System.out.println("");
			System.out.println("-------------------------------------");
		}
	}

	@Override
	public ArrayList<Object> computeActions(Object state) {
		ArrayList<Object> actions=new ArrayList<>();
		for (int i = 1; i < 26; i++) {
			actions.add(new Integer(i));
		}
		return actions;
	}
	public static void main(String[] args) {
		boolean[][] b = new boolean[5][5];
		LightsOut l= new LightsOut();
		b[1][1]=true;
		b[2][0]=true;
		b[2][2]=true;
		b[3][1]=true;
		l.initialState=b;
		BreadthFirstSearch bfs=new BreadthFirstSearch();
		bfs.solve(l);
	}
}
