package com.example.filedemo.solver;

import java.util.*;

public class Frontier {
	//We can use a linked list here because it's more efficient than an array
	//and because we only want to add/remove items from the start or end.
	public LinkedList<PuzzleState> Items;
	
	public Frontier()
	{
		Items = new LinkedList<PuzzleState>();
	}
	
	public PuzzleState Pop()
	{	
		return Items.pop();
	}
	
	public void Push(PuzzleState aState)
	{
		Items.push(aState);
	}
	
	public void Push(PuzzleState[] aStates)
	{
		for(int i = 0; i < aStates.length; i++)
		{
			//add each item at the start of the list.
			Items.push(aStates[i]);
		}
	}
	
	public void Enqueue(PuzzleState aState)
	{
		//This can be done using the addToArray method from the nPuzzler class
		Items.offer(aState);
	}
	
	public void Enqueue(PuzzleState[] aStates)
	{
		//This can be done using the addToArray method from the nPuzzler class
		for(int i = 0; i < aStates.length; i++)
		{
			Items.offer(aStates[i]);
		}
	}
	
	public void SortByCostAsc()
	{
		//TODO: Implement SortByCostAsc()
		
	}
	
	public void SortByCostDesc()
	{
		//TODO: Implement SortByCostDesc() 
	}
	
	public void SortByHeuristicAsc()
	{
		//TODO: Implement SortByHeuristicAsc()
	}
	
	public void SortByHeuristicDesc()
	{
		//TODO: Implement SortByHeuristicDesc()
	}
}
