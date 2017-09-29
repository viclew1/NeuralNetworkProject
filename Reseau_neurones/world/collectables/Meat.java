package collectables;

import static utils.Constantes.MEAT;

import java.awt.Color;

public class Meat extends Collectable
{

	public Meat(double x, double y)
	{
		super(x, y, 0.5, MEAT, Color.RED);
	}

	@Override
	public void update() 
	{
		
	}

}
