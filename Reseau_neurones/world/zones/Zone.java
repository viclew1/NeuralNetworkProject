package zones;

import static utils.Constantes.SCROLL_X;
import static utils.Constantes.SCROLL_Y;
import static utils.Constantes.SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Path2D;

public abstract class Zone
{
	protected double[] x,y;
	protected final int timeToLive;
	protected int timeSpent = 0;
	protected boolean consumed;
	private final int type;
	private Color color;
	
	private Path2D hitbox;
	
	public Zone(double[] x, double[] y, int timeToLive, int type, Color color)
	{
		this.x=x;
		this.y=y;
		this.consumed=false;
		this.type=type;
		this.color=color;
		this.timeToLive = timeToLive;
		hitbox = new Path2D.Double();

		hitbox.moveTo(x[0], y[0]);
		for(int i = 1; i < x.length; ++i) {
		   hitbox.lineTo(x[i], y[i]);
		}
		hitbox.closePath();
	}
	
	public void update()
	{
		timeSpent++;
		if (timeSpent==Integer.MAX_VALUE-10)
			timeSpent=0;
		if (timeSpent>=timeToLive)
			expire();
	}
	
	public void draw(Graphics g)
	{
		Color oldColor = g.getColor();
		g.setColor(color);
		g.fillPolygon(xFinal(), yFinal(), x.length);
		g.setColor(Color.BLACK);
		g.drawPolygon(xFinal(), yFinal(), x.length);
		
		g.setColor(oldColor);
	}
	
	public void expire()
	{
		consumed=true;
	}
	
	// CALCULS DE LA POSITION SUR L'ECRAN
	
	protected int[] xFinal()
	{
		int[] xFinal = new int[x.length];
		for (int i = 0 ; i < x.length ; i++)
		{
			xFinal[i] = (int)(x[i]*SIZE+SCROLL_X);
		}
		return xFinal;
	}

	protected int[] yFinal()
	{
		int[] yFinal = new int[y.length];
		for (int i = 0 ; i < y.length ; i++)
		{
			yFinal[i] = (int)(y[i]*SIZE+SCROLL_Y);
		}
		return yFinal;
	}

	//GETTERS
	
	public boolean isExpired()
	{
		return consumed;
	}
	
	public int getType()
	{
		return type;
	}
	
	public double[] getXPoints()
	{
		return x;
	}
	
	public double[] getYPoints()
	{
		return y;
	}
	
	public Path2D getHitBox()
	{
		return this.hitbox;
	}
}
