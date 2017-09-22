package collectables;

import static utils.Constantes.SCROLL_X;
import static utils.Constantes.SCROLL_Y;
import static utils.Constantes.SIZE;

import java.awt.Graphics;

public abstract class Collectable
{

	protected double x,y;
	protected boolean consumed;
	private final int type;
	private double size;
	
	public Collectable(double x, double y, double size, int type)
	{
		this.x=x;
		this.y=y;
		this.size=size;
		this.consumed=false;
		this.type=type;
	}
	
	public abstract void update();
	public abstract void draw(Graphics g);
	public void consume()
	{
		consumed=true;
	}
	
	// CALCULS DE LA POSITION SUR L'ECRAN
	
	protected int xFinal()
	{
		return (int)(x*SIZE+SCROLL_X);
	}

	protected int yFinal()
	{
		return (int)(y*SIZE+SCROLL_Y);
	}

	protected int sizeFinal()
	{
		return (int) (size*SIZE);
	}
	
	//GETTERS
	
	public boolean isConsumed()
	{
		return consumed;
	}
	
	public int getType()
	{
		return type;
	}
	
	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}
	
	public double getSize()
	{
		return size;
	}
}
