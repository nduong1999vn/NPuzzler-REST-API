package com.example.filedemo.solver;
import java.util.*;

public class AStarStrategy extends SearchMethod {

    public AStarStrategy() 
    {
		code = "ASS";
		longName = "A Star Search";
		Frontier = new LinkedList<PuzzleState>();
		Searched = new LinkedList<PuzzleState>();
	}

	public direction[] Solve(nPuzzle puzz) 
	{
		// keep searching the fringe until it's empty.
		// Items are "popped" from the fringe in order of lowest heuristic value.

		// Add the start state to the fringe
		addToFrontier(puzz.StartState);
		while (Frontier.size() > 0) 
		{
			// get the next State
			PuzzleState thisState = popFrontier();

			// is this the goal state?
			if (thisState.equals(puzz.GoalState)) {				
				return thisState.GetPathToState();
			}

			// not the goal state, explore this node
			ArrayList<PuzzleState> newStates = thisState.explore();

			for (int i = 0; i < newStates.size(); i++) {
				PuzzleState newChild = newStates.get(i);
				// if you can add these new states to the fringe
				if (addToFrontier(newChild)) {
					// then, work out it's heuristic value and cost
					newChild.HeuristicValue = HeuristicValue(newStates.get(i), puzz.GoalState);
					//The evaluation function for A Star is different: Cost + Heuristic value
					newChild.setEvaluationFunction(newChild.HeuristicValue + newChild.Cost);
				}

			}

			Collections.sort(Frontier, new PuzzleComparator());
		}

		// no more nodes and no path found?
		return null;
	}

	protected PuzzleState popFrontier() {
		// remove a state from the top of the fringe so that it can be searched.
		PuzzleState lState = Frontier.pollFirst();

		// add it to the list of searched states so that duplicates are recognised.
		Searched.add(lState);
		
		return lState;
	}

	public boolean addToFrontier(PuzzleState aState) {
		// We only want to add the new state to the fringe if it doesn't exist
		// in the fringe or the searched list.
		if (Searched.contains(aState) || Frontier.contains(aState)) {
			return false;
		} else {
			Frontier.add(aState);
			return true;
		}
	}

	private int HeuristicValue(PuzzleState aState, PuzzleState goalState) {
		// find out how many elements in aState match the goalState
		// return the number of elements that don't match
		int heuristic = 0;

		for (int i = 0; i < aState.Puzzle.length; i++) {
			for (int j = 0; j < aState.Puzzle[i].length; j++) {
				if (aState.Puzzle[i][j] != goalState.Puzzle[i][j])
					heuristic++;
			}
		}

		return heuristic;
	}

}