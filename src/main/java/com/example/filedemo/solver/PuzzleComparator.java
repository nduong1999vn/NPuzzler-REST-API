package com.example.filedemo.solver;

import java.util.Comparator;

public class PuzzleComparator implements Comparator<PuzzleState>
{
	
	@Override
	public int compare(PuzzleState state1, PuzzleState state2) 
	{
		return state1.getEvaluationFunction() - state2.getEvaluationFunction();
	}

}
