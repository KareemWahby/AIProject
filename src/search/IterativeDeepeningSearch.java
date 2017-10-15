package search;

import java.util.ArrayList;

public class IterativeDeepeningSearch extends SearchAlgorithm {

	public ArrayList<Object> solve(SearchProblem problem) {
		int i = 1;
		while (true) {
			DepthLimitedSearch dls = new DepthLimitedSearch();
			ArrayList<Object> result = dls.solve(problem, i++);
			if (result == null)
				continue;
			else
				return result;

		}

	}

}
