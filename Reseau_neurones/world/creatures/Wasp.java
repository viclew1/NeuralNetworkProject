package creatures;

import static utils.Constantes.*;

import java.awt.Color;
import java.util.List;

import collectables.Collectable;
import creatures.captors.Captor;
import creatures.captors.EyeCaptor;
import genetics.Individu;
import limitations.Delimitation;
import limitations.DelimitationBox;

public class Wasp extends InsectCreature
{

	public Wasp(double x, double y, Individu brain, List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		super(x, y, 2, 400, 0.35,1,
				new Captor[]{
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI,6,Math.PI/4),
				},
				brain, WASP, Color.ORANGE,
				INPUT_COUNT_WASP,
				creatures,collectables,delimitations, box);
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
}