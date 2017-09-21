package limitations;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;

import creatures.Creature;

public class DelimitationBox extends Delimitation
{

	public DelimitationBox(int x, int y, int w, int h)
	{
		super(x,y,w,h,Double.MAX_VALUE,null,DELIMITATION_BOX);
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

	@Override
	public void interactWith(Creature o1)
	{
		// TODO Auto-generated method stub
		
	}
	
	
}
