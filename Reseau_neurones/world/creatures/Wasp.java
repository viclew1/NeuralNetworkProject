package creatures;

import static utils.Constantes.*;

import java.awt.Color;

import collectables.Collectable;
import genetics.Individu;
import limitations.Delimitation;

public class Wasp extends Creature
{

	public Wasp(double x, double y, Individu brain)
	{
		super(x, y, 2, 400, 0.35, brain, WASP, Color.ORANGE);
	}

	@Override
	public void interactWith(Collectable c)
	{
		if (c.isConsumed())
			return;
		switch (c.getType())
		{
		case MEAT:
			brain.addScore(20);
			hp+=25;
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

			break;
		case BEE:
			brain.addScore(50);
			hp+=50;
			if (hp>hpMax)
				hp=hpMax;
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