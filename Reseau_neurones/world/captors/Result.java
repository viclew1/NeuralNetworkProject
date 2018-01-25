package captors;

import creatures.Creature;

public class Result
{

	private double value;
	private Creature creature;
	
	public Result(Creature seenCreature, double value)
	{
		this.value = value;
		this.creature = seenCreature;
	}
	
	public Creature getCreature()
	{
		return creature;
	}
	
	public double getValue()
	{
		return value;
	}
	
}
