package creatures;

import static utils.Constantes.*;

import java.awt.Color;

import collectables.Collectable;
import genetics.Individu;
import limitations.Delimitation;

public class Bee extends Creature
{

	public Bee(double x, double y, Individu brain)
	{
		super(x, y, 1, 400, 0.7,brain,	BEE, Color.YELLOW);
	}

	@Override
	public void interactWith(Collectable c)
	{
		if (c.isConsumed())
			return;
		switch (c.getType())
		{
		case VEGETABLE:
			brain.addScore(50);
			hp+=50;
			if (hp>hpMax)
				hp=hpMax;
			c.consume();
			break;
		default:
			break;
		}
	}

	@Override
	public void interactWith(Creature c)
	{
		switch (c.getType())
		{
		case WASP:
			brain.addScore(-50);
			alive=false;
			break;
		default:
			break;
		}
	}

	@Override
	public void interactWith(Delimitation d)
	{
		alive=false;
	}
}
