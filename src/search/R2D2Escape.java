package search;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

public class R2D2Escape extends SearchProblem {
	public final int N = 1;
	public final int E = 2;
	public final int S = 3;
	public final int W = 4;

	public R2D2Escape() {
		super();
		this.actions = new Integer[] { N, E, S, W };
	}

	public void genGrid() throws IOException {
		ArrayList<String> prolog=new ArrayList<>();
		ArrayList<String> prolog2=new ArrayList<>();
		BufferedWriter writer = new BufferedWriter(new FileWriter("grid.pl"));
		Random r = new Random();
		int m = r.nextInt(5) + 1;
		int n = r.nextInt(5) + 1;
		prolog.add("% this is the generated grid where grid(x,y):- x=>width, y=>height");
		prolog.add("grid("+n+","+m+").");
		int r2d2x = r.nextInt(n);
		int r2d2y = r.nextInt(m);
		int nRocks = r.nextInt(m) + 1;
		int nBlocks = r.nextInt(n);
		int portalx = r.nextInt(n);
		int portaly = r.nextInt(m);
		// for testing
		// System.out.println("m=" + m + " n=" + n);
		// System.out.println("r2d2 " + r2d2y + "," + r2d2x);
		// System.out.println("portal" + portaly + "," + portalx);
		// System.out.println("blocks " + nBlocks + " rocks/Ppads " + nRocks);
		int r2d2X, r2d2Y;
		int portalX, portalY;
		r2d2X = r2d2x;
		r2d2Y = r2d2y;
		prolog2.add("at(r2d2,"+r2d2x+","+r2d2y+").");
		portalX = portalx;
		portalY = portaly;
		prolog2.add("at(portal,"+portalx+","+portaly+").");
		String[][] grid = new String[m][n];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = "_";
			}
		}
		grid[r2d2Y][r2d2X] = "&";
		if (grid[portalY][portalX].contains("&"))
			grid[portalY][portalX] = "T&";
		else
			grid[portalY][portalX] = "T";
		prolog.add("%Blocks");
		for (int i = 0; i < nBlocks; i++) {
			int x, y;
			do {
				x = r.nextInt(n);
				y = r.nextInt(m);
			} while (!grid[y][x].equals("_"));
			grid[y][x] = "X";
			prolog.add("block(b"+i+").");
			prolog2.add("at(b"+i+","+x+","+y+").");
					}
		prolog.add("%Rocks");
		for (int i = 0; i < nRocks; i++) {
			int x, y;
			do {
				x = r.nextInt(n);
				y = r.nextInt(m);
			} while (!grid[y][x].equals("_"));
			grid[y][x] = "R";
			prolog.add("rock(r"+i+").");
			prolog2.add("at(r"+i+","+x+","+y+").");
		}
		prolog.add("%Pressure Pads");
		for (int i = 0; i < nRocks; i++) {
			int x, y;

			do {
				x = r.nextInt(n);
				y = r.nextInt(m);
			} while ((!grid[y][x].equals("_")) || grid[y][x].contains("R"));

			if (grid[y][x].contains("R")) {
				grid[y][x] = grid[y][x] + "P";
				prolog.add("pPad(p"+i+").");
				prolog2.add("at(p"+i+","+x+","+y+").");
			} else {
				grid[y][x] = "P";
				prolog.add("pPad(p"+i+").");
				prolog2.add("at(p"+i+","+x+","+y+").");
			}
		}

		State init = new State(grid, r2d2X, r2d2Y, nRocks);
		init.tX = portalX;
		init.tY = portalY;
		this.initialState = init;
		for (String s : prolog) {
			writer.write(s);
			writer.newLine();
		}
		writer.write("%Locations at(O,X,Y).\n");
		for (String s : prolog2) {
			writer.write(s);
			writer.newLine();
		}
		writer.flush();
	}

	@Override
	public Object transferFunction(Object state, Object action) {
		State s = (State) state;
		int a = (int) action;
		String n0, e0, s0, w0, n1, n2, e1, e2, s1, s2, w1, w2;
		switch (a) {
		case N:
			n0 = s.grid[s.r2d2Y][s.r2d2X];
			if (s.r2d2Y - 1 >= 0)
				n1 = s.grid[s.r2d2Y - 1][s.r2d2X];
			else
				n1 = "OOB";

			if (s.r2d2Y - 2 >= 0)
				n2 = s.grid[s.r2d2Y - 2][s.r2d2X];
			else
				n2 = "OOB";

			if (!n1.equals("OOB")) {
				if (n1.equals("_")){
					n0 = n0.replace("&", "");
				n0 = (n0.equals("")) ? "_" : n0;
					n1 = "&";
				}else if (n1.contains("T") || (n1.contains("P") && !n1.contains("R"))) {
					n0 = n0.replace("&", "");
					n0 = (n0.equals("")) ? "_" : n0;
					n1 += "&";
				} else {
					if (n1.contains("R")) {
						if (!n2.equals("OOB"))
						if (n2.equals("_")){
							n1 = n1.replace("R", "");
							n2 = "R";
						}else if (n2.contains("T") || (n2.contains("P") && !n2.contains("R"))) {
							n1 = n1.replace("R", "");
							n1 += "&";
							n2 += "R";
							if(n2.contains("P")&&n2.contains("R")){
								s.nRocks-=1;
							}
						}
					}
				}
			}
			s.grid[s.r2d2Y][s.r2d2X] = n0;
			if (!n1.equals("OOB"))
				s.grid[s.r2d2Y - 1][s.r2d2X] = n1;
			if (!n2.equals("OOB"))
				s.grid[s.r2d2Y - 2][s.r2d2X] = n2;
			s.r2d2Y-=1;
			return s;
		case E:
			
			break;
		case S:
			break;
		case W:
			w0 = s.grid[s.r2d2Y][s.r2d2X];
			if (s.r2d2Y + 1 <s.grid.length)
				w1 = s.grid[s.r2d2Y + 1][s.r2d2X];
			else
				w1 = "OOB";

			if (s.r2d2Y +2 <s.grid.length)
				w2 = s.grid[s.r2d2Y + 2][s.r2d2X];
			else
				w2 = "OOB";

			if (!w1.equals("OOB")) {
				if (w1.equals("_")){
					w0 = w0.replace("&", "");
				w0 = (w0.equals("")) ? "_" : w0;
					w1 = "&";
				}else if (w1.contains("T") || (w1.contains("P") && !w1.contains("R"))) {
					w0 = w0.replace("&", "");
					w0 = (w0.equals("")) ? "_" : w0;
					w1 += "&";
				} else {
					if (w1.contains("R")) {
						if (!w2.equals("OOB"))
						if (w2.equals("_")){
							w1 = w1.replace("R", "");
							w2 = "R";
						}else if (w2.contains("T") || (w2.contains("P") && !w2.contains("R"))) {
							w1 = w1.replace("R", "");
							w1 += "&";
							w2 += "R";
							if(w2.contains("P")&&w2.contains("R")){
								s.nRocks-=1;
							}
						}
					}
				}
			}
			s.grid[s.r2d2Y][s.r2d2X] = w0;
			if (!w1.equals("OOB"))
				s.grid[s.r2d2Y + 1][s.r2d2X] = w1;
			if (!w2.equals("OOB"))
				s.grid[s.r2d2Y + 2][s.r2d2X] = w2;
			s.r2d2Y+=1;
			return s;
		default:
			break;
		}
		return null;
	}

	@Override
	public ArrayList<Object> computeActions(Object state) {
		State s = (State) state;
		ArrayList<Object> ac = new ArrayList<>();
		String n1, n2, e1, e2, s1, s2, w1, w2;
		if (s.r2d2Y - 1 >= 0)
			n1 = s.grid[s.r2d2Y - 1][s.r2d2X];
		else
			n1 = "OOB";

		if (s.r2d2Y - 2 >= 0)
			n2 = s.grid[s.r2d2Y - 2][s.r2d2X];
		else
			n2 = "OOB";

		if (s.r2d2X + 1 < s.grid[s.r2d2X].length)
			e1 = s.grid[s.r2d2Y][s.r2d2X + 1];
		else
			e1 = "OOB";

		if (s.r2d2X + 2 < s.grid[s.r2d2X].length)
			e2 = s.grid[s.r2d2Y][s.r2d2X + 2];
		else
			e2 = "OOB";

		if (s.r2d2Y + 1 < s.grid.length)
			s1 = s.grid[s.r2d2Y + 1][s.r2d2X];
		else
			s1 = "OOB";

		if (s.r2d2Y + 2 < s.grid.length)
			s2 = s.grid[s.r2d2Y + 2][s.r2d2X];
		else
			s2 = "OOB";

		if (s.r2d2X - 1 >= 0)
			w1 = s.grid[s.r2d2Y][s.r2d2X - 1];
		else
			w1 = "OOB";

		if (s.r2d2X - 2 >= 0)
			w2 = s.grid[s.r2d2Y][s.r2d2X - 2];
		else
			w2 = "OOB";
		if (!n1.equals("OOB"))
			if (n1.contains("_") || n1.contains("T") || (n1.contains("P") && !n1.contains("R"))) {
				ac.add(N);
			} else {
				if (n1.contains("R")) {
					if (!n2.equals("OOB"))
						if (n2.contains("_") || n2.contains("T") || (n2.contains("P") && !n2.contains("R"))) {
							ac.add(N);
						}
				}
			}
		if (!e1.equals("OOB"))
			if (e1.contains("_") || e1.contains("T") || (e1.contains("P") && !e1.contains("R"))) {
				ac.add(E);
			} else {
				if (e1.contains("R")) {
					if (!e2.equals("OOB"))
						if (e2.contains("_") || e2.contains("T") || (e2.contains("P") && !e2.contains("R"))) {
							ac.add(E);
						}
				}
			}
		if (!s1.equals("OOB"))
			if (s1.contains("_") || s1.contains("T") || (s1.contains("P") && !s1.contains("R"))) {
				ac.add(S);
			} else {
				if (s1.contains("R")) {
					if (!s2.equals("OOB"))
						if (s2.contains("_") || s2.contains("T") || (s2.contains("P") && !s2.contains("R"))) {
							ac.add(S);
						}
				}
			}
		if (!w1.equals("OOB"))
			if (w1.contains("_") || w1.contains("T") || (w1.contains("P") && !w1.contains("R"))) {
				ac.add(W);
			} else {
				if (w1.contains("R")) {
					if (!w2.equals("OOB"))
						if (w2.contains("_") || w2.contains("T") || (w2.contains("P") && !w2.contains("R"))) {
							ac.add(W);
						}
				}
			}

		return ac;
	}

	@Override
	public boolean goalTest(Object state) {
		State s = (State) state;
		return ((s.r2d2Y == s.tY) && (s.r2d2X == s.tX) && (s.nRocks == 0));
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
		System.out.println("Rocks left " + s.nRocks);
		System.out.println("R2 Loc" + s.r2d2Y+","+s.r2d2X);
		for (int i = 0; i < s.grid.length; i++) {
			for (int j = 0; j < s.grid[i].length; j++) {
				System.out.print(s.grid[i][j] + " ");
			}
			System.out.println("");
		}
	}

	public static void main(String[] args) throws IOException {
		R2D2Escape x = new R2D2Escape();
		x.genGrid();
	x.printState(x.initialState);
		// String g[][] = { { "_", "&", "_", "_" }, { "_", "R", "_", "_" }, {"_", "_", "_", "_" },{ "X", "X", "X", "X" } };
		// State s = new State(g, 0, 1, 1);
//		 x.printState(s);
//		 x.printState(x.transferFunction(s, 4));
		// System.out.println(x.computeActions(s));
	}
}
