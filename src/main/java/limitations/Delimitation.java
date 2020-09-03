package limitations;

import java.awt.Graphics;

import creatures.Creature;
import creatures.shooters.ShooterCreature;

public abstract class Delimitation
{

	protected double x,y,w,h;
	protected final ShooterCreature sender;
	protected double damages;
	protected double range;
	private boolean expired=false;

	private final int type;

	public Delimitation(double x, double y, double w, double h, double damages, double range, ShooterCreature sender, int type)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.type=type;
		this.damages=damages;
		this.sender=sender;
		this.range = range;
	}

	public abstract void draw(Graphics g);
	
	public abstract void update();

	public abstract void interactWith(Creature o1);
	
	public void expire()
	{
		expired=true;
	}
	
	/**
	 * Getters
	 */
	
	public double getX()
	{
		return x;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}
	
	public void setY(double y)
	{
		this.y = y;
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

	public Creature getSender()
	{
		return sender;
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
