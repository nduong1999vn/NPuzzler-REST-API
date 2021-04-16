package com.example.filedemo.solver;

public class nPuzzle {
	public PuzzleState StartState;
	public PuzzleState GoalState;
	
	public nPuzzle(PuzzleState aStartState, PuzzleState aGoalState)
	{
		//initialise the start and end states.
		StartState = aStartState;
		GoalState = aGoalState;
	}
	
	public nPuzzle(int[][] startState, int[][]goalState)
	{
		//initialise the start and end states using only the puzzle arrays
		StartState = new PuzzleState(startState);
		GoalState = new PuzzleState(goalState);
	}
}
