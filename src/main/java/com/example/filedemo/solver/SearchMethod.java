package com.example.filedemo.solver;

import java.util.*;

public abstract class SearchMethod {
	public String code;				//the code used to identify the method at the command line
	public String longName;			//the actual name of the method.
	//public List<PuzzleState> nodeList;	//this is for catching repeated states and counting total nodes.
	public abstract direction[] Solve(nPuzzle aPuzzle);
	
	//The fringe needs to be a Queue and a Stack.
	//LinkedList implements both interfaces.
	//LinkedList also implements List, which allows it to be sorted easily.
	public LinkedList<PuzzleState> Frontier;
	
	//the searched list simply needs to be able to store nodes for the purpose of checking
	//Fast addition and removal is crucial here.
	//HashSet provides constant time for add, contains and size.
	public LinkedList<PuzzleState> Searched;
	
	abstract public boolean addToFrontier(PuzzleState aState);
	abstract protected PuzzleState popFrontier();
	
	public void reset()
	{
		this.Frontier.clear();
		this.Searched.clear();
	}
}