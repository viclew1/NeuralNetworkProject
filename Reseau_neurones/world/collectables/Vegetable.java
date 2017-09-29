package collectables;

import java.awt.Color;

import static utils.Constantes.*;

public class Vegetable extends Collectable
{

	public Vegetable(double x, double y)
	{
		super(x, y, 0.5, VEGETABLE, Color.GREEN);
	}

	@Override
	public void update()
	{
		
	}

}
