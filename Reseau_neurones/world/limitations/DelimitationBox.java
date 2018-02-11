package limitations;

import static utils.Constantes.SCROLL_X;
import static utils.Constantes.SCROLL_Y;
import static utils.Constantes.SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class DelimitationBox extends Rectangle2D.Double
{

	private final Color color = Color.RED;
	
	public DelimitationBox(int x, int y, int w, int h)
	{
		super(x,y,w,h);
	}
	
	public double getCenterX()
	{
		return x + width/2;
	}
	
	public double getCenterY()
	{
		return y + height/2;
	}

	public void draw(Graphics g)
	{
		Color oldColor = g.getColor();
		g.setColor(color);
		g.drawRect((int)(x*SIZE+SCROLL_X), (int)(y*SIZE+SCROLL_Y), (int)(width*SIZE), (int)(height*SIZE));
		g.setColor(oldColor);
	}
	

}
