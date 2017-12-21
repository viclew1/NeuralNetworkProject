package neural_network;

import java.util.Random;

public class Connection
{
	
	private double weight;
	
	public Connection()
	{
		randomizeWeight();
	}
	
	private Connection(Connection c)
	{
		weight = c.weight;
	}
	
	public void randomizeWeight()
	{
		weight = WeightValues.weightRandomizer();
	}
	
	public void mutate()
	{
		weight += WeightValues.DELTA_WEIGHT*new Random().nextDouble();
		if (weight > WeightValues.MAX_WEIGHT)
			weight = WeightValues.MAX_WEIGHT;
		if (weight < WeightValues.MIN_WEIGHT)
			weight = WeightValues.MIN_WEIGHT;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	public Connection deepCopy()
	{
		return new Connection(this);
	}
	
}
