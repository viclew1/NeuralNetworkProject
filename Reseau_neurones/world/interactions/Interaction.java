package interactions;

public abstract class Interaction
{

	protected Object o1, o2;
	
	public Interaction(Object o1, Object o2)
	{
		this.o1=o1;
		this.o2=o2;
	}
	
	public abstract void process();
}
