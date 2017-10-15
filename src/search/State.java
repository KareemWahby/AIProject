package search;

public class State {
	public String[][] grid;
	public int r2d2X,r2d2Y;
	public int nRocks;
	public int tX,tY;
	public State(String[][] grid, int r2d2x, int r2d2y, int nRocks) {
		super();
		this.grid = grid;
		r2d2X = r2d2x;
		r2d2Y = r2d2y;
		this.nRocks = nRocks;

	}



}
