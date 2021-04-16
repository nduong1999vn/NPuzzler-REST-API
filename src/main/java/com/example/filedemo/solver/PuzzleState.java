package com.example.filedemo.solver;

import java.util.*;

/**
 * @author COS30019
 *
 */
public class PuzzleState implements Comparable<PuzzleState>
{
	public int[][] Puzzle;
	public PuzzleState Parent;
	public ArrayList<PuzzleState> Children;
	public int Cost;
	public int HeuristicValue;
	private int EvaluationFunction;
	public direction PathFromParent;
	
	public PuzzleState(PuzzleState aParent, direction aFromParent, int[][] aPuzzle)
	{
		Parent = aParent;
		PathFromParent = aFromParent;
		Puzzle = aPuzzle;
		Cost = Parent.Cost + 1;
		EvaluationFunction = 0;
		HeuristicValue = 0;
	}
	
	public PuzzleState(int[][] aPuzzle)
	{
		Parent = null;
		PathFromParent = null;
		Cost = 0;
		Puzzle = aPuzzle;
		EvaluationFunction = 0;
		HeuristicValue = 0;
	}
	
	public int getEvaluationFunction()
	{
		return EvaluationFunction;
	}
	
	public void setEvaluationFunction(int value)
	{
		EvaluationFunction = value;
	}
	
	public direction[] getPossibleActions()
	{
		//find where the blank cell is and store the directions.
		direction[] result;
		int[] blankLocation = {0, 0};	//dummy value to avoid errors.
		
		try
		{
			blankLocation = findBlankCell();
		}
		catch(InvalidPuzzleException e)
		{
			System.out.println("There was an error in processing! Aborting...");
			System.exit(1);
		}
		result = new direction[countMovements(blankLocation)];
		int thisIndex = 0;
		if(blankLocation[0] == 0)
		{
			//the blank cell is already as far left as it will go, it can move right
			result[thisIndex++] = direction.Right;
		}
		else if(blankLocation[0] == (Puzzle.length - 1))
		{
			result[thisIndex++] = direction.Left;
		}
		else
		{
			result[thisIndex++] = direction.Left;
			result[thisIndex++] = direction.Right;
		}
		
		if(blankLocation[1] == 0)
		{
			//the blank cell is already as far up as it will go, it can move down
			result[thisIndex++] = direction.Down;
		}

		//Puzzle.length refers to the width and Puzzle[0].length refers to the height 
		//else if(blankLocation[1] == (Puzzle.length - 1))
		else if(blankLocation[1] == (Puzzle[0].length - 1))
		{
			result[thisIndex++] = direction.Up;
		}
		else
		{
			result[thisIndex++] = direction.Up;
			result[thisIndex++] = direction.Down;
		}
		return result;
	}
	
	private int countMovements(int[] blankLocation)
	{
		int result = 2;
		try
		{
			blankLocation = findBlankCell();

			/*for(int i = 0; i <= 1; i++)
			{
				if(blankLocation[i] == 0
					|| blankLocation[i] == (Puzzle.length - 1))
				{
					//do nothing
				}
				else
				{
					result++;
				}
			}*/
		
			if(blankLocation[0] == 0 || blankLocation[0] == (Puzzle.length - 1)) {
					//do nothing
			} 
			
			else {
				result++;
			}

			//cannot use a for loop since the width and height is now different. We need to implement each one individually
			if(blankLocation[1] == 0 || blankLocation[1] == (Puzzle[0].length - 1)) {
				//do nothing
			}

			else {
				result++;
			}
		}
		catch (InvalidPuzzleException e)
		{
			//do something
		}
		return result;
	}
	
	private int[] findBlankCell() throws InvalidPuzzleException
	{
		for(int i = 0; i < Puzzle.length; i++)
		{
			for(int j = 0; j < Puzzle[i].length; j++)
			{
				if(Puzzle[i][j] == 0)
				{
					int[] result = {i, j};
					return result;
				}
			}
		}
		//No blank cell found?
		throw new InvalidPuzzleException(this);
	}
	
	private int[][] cloneArray(int[][] cloneMe)
	{
		int[][] result = new int[cloneMe.length][cloneMe[0].length];
		for(int i = 0; i < cloneMe.length; i++)
		{
			for(int j = 0; j < cloneMe[i].length; j++)
			{
				result[i][j] = cloneMe[i][j];
			}
		}
		return result;
	}
	
	public PuzzleState move(direction aDirection) throws CantMoveThatWayException
	{
		//Moving up moves the empty cell up (and the cell above it down)
		//first, create the new one (the one to return)
		PuzzleState result = new PuzzleState(this, aDirection, cloneArray(this.Puzzle));
		
		//now, execute the changes: move the blank cell aDirection
		//find the blankCell
		int[] blankCell = {0, 0};
		try
		{
			blankCell = findBlankCell();
		}
		catch(InvalidPuzzleException e)
		{
			System.out.println("There was an error in processing! Aborting...");
			System.exit(1);
		}
		try
		{
			//move the blank cell in the new child puzzle
			
			if(aDirection == direction.Up)
			{
				result.Puzzle[blankCell[0]][blankCell[1]] = result.Puzzle[blankCell[0]][blankCell[1] - 1];
				result.Puzzle[blankCell[0]][blankCell[1] - 1] = 0;
			}
			else if(aDirection == direction.Down)
			{
				result.Puzzle[blankCell[0]][blankCell[1]] = result.Puzzle[blankCell[0]][blankCell[1] + 1];
				result.Puzzle[blankCell[0]][blankCell[1] + 1] = 0;
			}
			else if(aDirection == direction.Left)
			{
				result.Puzzle[blankCell[0]][blankCell[1]] = result.Puzzle[blankCell[0] - 1][blankCell[1]];
				result.Puzzle[blankCell[0] - 1][blankCell[1]] = 0;
			}
			else	//aDirection == Right;
			{
				result.Puzzle[blankCell[0]][blankCell[1]] = result.Puzzle[blankCell[0] + 1][blankCell[1]];
				result.Puzzle[blankCell[0] + 1][blankCell[1]] = 0;
			}
			return result;
		}
		catch(IndexOutOfBoundsException ex)
		{
			throw new CantMoveThatWayException(this, aDirection);
		}
	}
	
	@Override
	public boolean equals(Object aObject) throws ClassCastException
	{
		PuzzleState aState = (PuzzleState)aObject;
		//evaluate if these states are the same (does this.Puzzle == aState.Puzzle)?
		for(int i = 0; i < Puzzle.length; i++)
		{
			for(int j = 0; j < Puzzle[i].length; j++)
			{
				if(this.Puzzle[i][j] != aState.Puzzle[i][j])
					return false;		//stop checking as soon as we find an 
										// element that doesn't match
			}
		}
		return true;	//All elements matched? Return true;
	}

	//this is to allow the TreeSet to sort it.
	public int compareTo(PuzzleState aState)
	{
		return EvaluationFunction - aState.getEvaluationFunction();
	}
	
	public ArrayList<PuzzleState> explore()
	{
		//populate children
		direction[] possibleMoves = getPossibleActions();
		Children = new ArrayList<PuzzleState>();
		for(int i = 0; i < possibleMoves.length; i++)
		{
			try
			{
				Children.add(move(possibleMoves[i]));
			}
			catch (CantMoveThatWayException e)
			{
				System.out.println("There was an error in processing! Aborting...");
				System.exit(1);
			}
		}	
		return Children;
	}
	
	public direction[] GetPathToState()
	{
		direction result[];
		
		if(Parent == null)	//If this is the root node, there is no path!
		{
			result = new direction[0];
			return result;
		} else				//Other wise, path to here is the path to parent
							// plus parent to here
		{
			direction[] pathToParent = Parent.GetPathToState();
			result = new direction[pathToParent.length + 1];
			for(int i = 0; i < pathToParent.length; i++)
			{
				result[i] = pathToParent[i];
			}
			result[result.length - 1] = this.PathFromParent;
			return result;
		}
	}
}
