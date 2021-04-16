package com.example.filedemo.solver;

public class CantMoveThatWayException extends Exception {
	static final long serialVersionUID = 1988122502;
	
	public PuzzleState Source;
	public direction Direction;
	
	public CantMoveThatWayException(PuzzleState source, direction aDirection)
	{
		//The puzzle in Source tried to move in the direction aDirection.
		//This is an illegal move (It put a tile off the edge of the puzzle!)
		Source = source;
		Direction = aDirection;
	}

}

