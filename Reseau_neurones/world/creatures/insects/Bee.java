package creatures.insects;

import static utils.Constantes.*;

import java.awt.Color;
import java.util.List;

import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import limitations.Delimitation;
import limitations.DelimitationBox;

public class Bee extends InsectCreature
{

	public Bee(double x, double y, Individu brain, List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		super(x, y, 1, 400, 0.7, 5, 1,
				new Captor[]{
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI,6,Math.PI/4),
				},
				brain,	BEE, Color.YELLOW, LAYERS_SIZES_BEE[0],
				creatures,collectables,delimitations,box);
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
			alive=false;
			break;
		default:
			break;
		}
	}
}
