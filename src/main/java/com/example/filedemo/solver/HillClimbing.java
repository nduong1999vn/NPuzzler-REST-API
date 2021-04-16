package com.example.filedemo.solver;

import java.util.*;

public class HillClimbing extends SearchMethod
{

    public HillClimbing()
	{
		code = "HC";
		longName = "Hill Climbing Strategy";
		Frontier = new LinkedList<PuzzleState>();
		Searched = new LinkedList<PuzzleState>();
	}
	
	public boolean addToFrontier(PuzzleState aState)
	{
		//We only want to add the new state to the fringe if it doesn't exist
		// in the fringe or the searched list.
		if(Searched.contains(aState) || Frontier.contains(aState))
		{
			return false;
		}
		else
		{
			Frontier.add(aState);
			return true;
		}
	}
	
	public direction[] Solve(nPuzzle aPuzzle)
	{
		//keep searching the fringe until it's empty.
		//Items are "popped" from the fringe in order of lowest heuristic value.
        
        //Find the heuristic value of the start state
        aPuzzle.StartState.HeuristicValue = HeuristicValue(aPuzzle.StartState, aPuzzle.GoalState);
		aPuzzle.StartState.setEvaluationFunction(aPuzzle.StartState.HeuristicValue);

		//Add the start state to the fringe
        addToFrontier(aPuzzle.StartState);

        PuzzleState parentState = new PuzzleState(new int[][] {});
        parentState.setEvaluationFunction(aPuzzle.StartState.getEvaluationFunction() + 1);

		while(Frontier.size() > 0)
		{
			//get the next State
			PuzzleState thisState = popFrontier();
			
			//is this the goal state?
			if(thisState.equals(aPuzzle.GoalState))
			{
				return thisState.GetPathToState();
            }
            
            //is this the local maximum? Is this the start state?
            if (thisState.getEvaluationFunction() >= parentState.getEvaluationFunction()) {
                System.out.println("Local Maximum reeached. Solution to local maximum:");
                return thisState.GetPathToState();
			}
			
			//not the goal state or local max, explore this node
			ArrayList<PuzzleState> newStates = thisState.explore();
            
            //assign the minimum heuristic value as the first state we analyze
            int min = HeuristicValue(newStates.get(0), aPuzzle.GoalState);

            //analyze and compare the heuristic values of all explored states
			for(int i = 0; i < newStates.size(); i++)
			{      
				//compare this successor's heuristic value to the minimum
				if(HeuristicValue(newStates.get(i), aPuzzle.GoalState) <= min)
				{
                    min = HeuristicValue(newStates.get(i), aPuzzle.GoalState); 
                } 
            }

            //Add successor states that has the lowest Heuristic cost 
            //to the frontier and assign their Evaluation Function
            for(int i = 0; i < newStates.size(); i++)
			{      
				if(HeuristicValue(newStates.get(i), aPuzzle.GoalState) == min)
				{
                    newStates.get(i).setEvaluationFunction(HeuristicValue(newStates.get(i), aPuzzle.GoalState));
                    addToFrontier(newStates.get(i));
                } 
            }

            //make this state the parent state to explore next child states
            parentState = thisState;

            Collections.sort(Frontier, new PuzzleComparator());
		}
		
		//no more nodes and no path found?
		return null;
	}
	
	protected PuzzleState popFrontier()
	{
		//remove a state from the top of the fringe so that it can be searched.
		PuzzleState lState = Frontier.pollFirst();
		
		//add it to the list of searched states so that duplicates are recognised.
		Searched.add(lState);
		
		return lState;
	}
	
	private int HeuristicValue(PuzzleState aState, PuzzleState goalState)
	{
		//find out how many elements in aState match the goalState
		//return the number of elements that don't match
		int heuristic = 0;
		for(int i = 0; i < aState.Puzzle.length; i++)
		{
			for(int j = 0; j < aState.Puzzle[i].length; j++)
			{
				if(aState.Puzzle[i][j] != goalState.Puzzle[i][j])
					heuristic++;
			}
		}
		
		return heuristic;
	}
	
}
