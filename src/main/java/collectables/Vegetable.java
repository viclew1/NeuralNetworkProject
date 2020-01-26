package collectables;

import java.awt.Color;
import java.awt.Graphics;

import static utils.Constantes.*;

public class Vegetable extends Collectable
{

	public Vegetable(double x, double y)
	{
		super(x, y, 0.5, VEGETABLE);
	}

	@Override
	public void draw(Graphics g)
	{
		Color oldColor = g.getColor();
		g.setColor(Color.GREEN);
		g.fillOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(Color.BLACK);
		g.drawOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(oldColor);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
