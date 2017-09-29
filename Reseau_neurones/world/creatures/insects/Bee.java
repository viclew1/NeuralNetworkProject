package creatures.insects;

import static utils.Constantes.*;

import java.awt.Color;
import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;

public class Bee extends InsectCreature
{

	public Bee(double x, double y, Individu brain, World world)
	{
		super(x, y, 1, 400, 0.7,1,
				new Captor[]{
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI,6,Math.PI/4),
				},
				brain,	BEE, Color.YELLOW, LAYERS_SIZES_BEE[0],
				world);
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
}
