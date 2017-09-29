package limitations;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;

import creatures.Creature;

public class WallDelimitation extends Delimitation
{
	public WallDelimitation(double x, double y)
	{
		super(x, y, 0.5, 0.5, Double.MAX_VALUE, null, WALL);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g)
	{
		Color oldColor = g.getColor();
		g.setColor(Color.RED);
		g.fillRect((int)(x*SIZE+SCROLL_X), (int)(y*SIZE+SCROLL_Y), (int)(w*SIZE), (int)(h*SIZE));
		g.setColor(oldColor);
	}

	@Override
	public void updatePosition()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactWith(Creature o1)
	{
		// TODO Auto-generated method stub
		
	}
	
}
