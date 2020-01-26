package captors;

public class Result<T>
{

	private T seen;
	private double value;
	
	public Result(T seen, double value)
	{
		this.seen = seen;
		this.value = value;
	}

	public T getSeen()
	{
		return seen;
	}

	public void setSeen(T seen)
	{
		this.seen = seen;
	}

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}
	
	
	
}
