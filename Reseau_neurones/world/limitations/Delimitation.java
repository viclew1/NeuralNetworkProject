package limitations;

import java.awt.Graphics;

public abstract class Delimitation
{

	protected int x,y,w,h;
	private boolean emptyEnside;

	public Delimitation(int x, int y, int w, int h, boolean emptyInside)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.emptyEnside=emptyInside;
	}

	public abstract void draw(Graphics g);
	
	public abstract void update();
	
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getW()
	{
		return w;
	}

	public int getH()
	{
		return h;
	}
	
	public boolean isEmptyInside()
	{
		return emptyEnside;
	}


}
