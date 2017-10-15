package search;

import java.util.ArrayList;
import java.util.Random;

public class R2D2Escape extends SearchProblem {

	public void genGrid() {
		Random r = new Random();
		int m = r.nextInt(5) + 1;
		int n = r.nextInt(5) + 1;
		int r2d2x = r.nextInt(n);
		int r2d2y = r.nextInt(m);
		int nRocks = r.nextInt(m) + 1;
		int nBlocks = r.nextInt(n);
		int portalx = r.nextInt(n);
		int portaly = r.nextInt(m);
		//for testing
//		System.out.println("m=" + m + " n=" + n);
//		System.out.println("r2d2 " + r2d2y + "," + r2d2x);
//		System.out.println("portal" + portaly + "," + portalx);
//		System.out.println("blocks " + nBlocks + " rocks/Ppads " + nRocks);
		int r2d2X, r2d2Y;
		int portalX, portalY;
		r2d2X = r2d2x;
		r2d2Y = r2d2y;
		portalX = portalx;
		portalY = portaly;
		String[][] grid = new String[m][n];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = "_";
			}
		}
		grid[r2d2Y][r2d2X] = "R2D2";
		if (grid[portalY][portalX].contains("R2D2"))
			grid[portalY][portalX] = "T,R2D2";
		else
			grid[portalY][portalX] = "T";
		for (int i = 0; i < nBlocks; i++) {
			int x, y;
			do {
				x = r.nextInt(n);
				y = r.nextInt(m);
			} while (!grid[y][x].equals("_"));
			grid[y][x] = "X";
		}

		for (int i = 0; i < nRocks; i++) {
			int x, y;
			do {
				x = r.nextInt(n);
				y = r.nextInt(m);
			} while (!grid[y][x].equals("_"));
			grid[y][x] = "R";
		}

		for (int i = 0; i < nRocks; i++) {
			int x, y;

			do {
				x = r.nextInt(n);
				y = r.nextInt(m);
			} while ((!grid[y][x].equals("_")) || grid[y][x].contains("R"));

			if (grid[y][x].contains("R")) {
				grid[y][x] = grid[y][x] + ",P";
			} else {
				grid[y][x] = "P";
			}
		}

		State init = new State(grid, r2d2X, r2d2Y, nRocks);
		init.tX = portalX;
		init.tY = portalY;
		this.initialState = init;
	}

	@Override
	public Object transferFunction(Object state, Object action) {
		// TODO Kareem Wahby
		return null;
	}

	@Override
	public ArrayList<Object> computeActions(Object state) {
		// TODO Kareem Wahby
		return null;
	}

	@Override
	public boolean goalTest(Object state) {
		State s = (State) state;
		return ((s.r2d2Y==s.tY)&&(s.r2d2X==s.tX)&&(s.nRocks==0));
	}

	@Override
	public int pathCost(Object[] actions) {
		return 1;
	}

	@Override
	public int heuristic(int hnumber, Node node) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void printState(Object state) {
		State s = (State) state;
		System.out.println("Rocks left "+s.nRocks);
		for (int i = 0; i < s.grid.length; i++) {
			for (int j = 0; j < s.grid[i].length; j++) {
				System.out.print(s.grid[i][j] + " ");
			}
			System.out.println("");
		}
	}

	public static void main(String[] args) {
		R2D2Escape x = new R2D2Escape();
		x.genGrid();
		x.printState(x.initialState);
		
	}
}
