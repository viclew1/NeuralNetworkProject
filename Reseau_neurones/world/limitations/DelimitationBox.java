package limitations;

import static utils.Constantes.SCROLL_X;
import static utils.Constantes.SCROLL_Y;
import static utils.Constantes.SIZE;

import java.awt.Color;
import java.awt.Graphics;

public class DelimitationBox extends Delimitation
{

	public DelimitationBox(int x, int y, int w, int h)
	{
		super(x,y,w,h,true);
	}
	
	public void draw(Graphics g)
	{
		Color oldColor = g.getColor();
		g.setColor(Color.RED);
		g.drawRect((int)(x*SIZE+SCROLL_X), (int)(y*SIZE+SCROLL_Y), (int)(w*SIZE), (int)(h*SIZE));
		g.setColor(oldColor);
	}

	@Override
	public void update()
	{
		
	}
	
	
}
