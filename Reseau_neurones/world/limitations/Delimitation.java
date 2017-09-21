package limitations;

import java.awt.Graphics;

public abstract class Delimitation
{

	protected double x,y,w,h;
	private boolean expired=false;
	private double damages;
	private int type;

	public Delimitation(double x, double y, double w, double h, double damages, int type)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.type=type;
		this.damages=damages;
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

	public double getDamages()
	{
		return damages;
	}
	
	public void expire()
	{
		expired=true;
	}
	
	public boolean isExpired()
	{
		return expired;
	}
	
	public int getType()
	{
		return type;
	}


}
