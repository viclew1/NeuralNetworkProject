package collectables;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;

public class Meat extends Collectable
{

	public Meat(double x, double y)
	{
		super(x, y, 0.5, MEAT);
	}

	@Override
	public void draw(Graphics g)
	{
		Color oldColor = g.getColor();
		g.setColor(Color.RED);
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
