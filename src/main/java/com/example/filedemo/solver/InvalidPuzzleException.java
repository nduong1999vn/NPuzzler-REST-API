package com.example.filedemo.solver;

public class InvalidPuzzleException extends Exception {
	public PuzzleState theState;
	static final long serialVersionUID = 1988122501;
	
	
	public InvalidPuzzleException(PuzzleState aState)
	{
		//This puzzle is invalid for some reason
		theState = aState;
	}

}
