package neural_network;

public class Neuron
{

	private double value;
	private double sum = 0;
	
	public void applyActivationFunction()
	{
		value = 1/(1+Math.exp(-sum));
	}
	
	public void addToSum(double value, double weight)
	{
		sum+=value*weight;
	}
	
	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}
	
	public void resetSum()
	{
		sum=0;
	}
	
}
