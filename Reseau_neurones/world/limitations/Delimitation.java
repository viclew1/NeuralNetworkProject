package limitations;

import java.awt.Graphics;

public abstract class Delimitation
{

	protected double x,y,w,h;
	private boolean emptyEnside;

	public Delimitation(double x, double y, double w, double h, boolean emptyInside)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.emptyEnside=emptyInside;
	}

	public abstract void draw(Graphics g);
	
	public abstract void update();

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getW()
	{
		return w;
	}

	public double getH()
	{
		return h;
	}
	
	public boolean isEmptyInside()
	{
		return emptyEnside;
	}


}
