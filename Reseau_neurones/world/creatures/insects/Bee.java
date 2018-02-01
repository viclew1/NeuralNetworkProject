package creatures.insects;

import static utils.Constantes.*;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;

public class Bee extends InsectCreature
{

	public Bee(double x, double y, Individu brain, Selection selec, World world)
	{
		super(x, y, 1, 400, 1, 5, 1,
				new Captor[]{
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI,6,Math.PI/4),
				},
				brain, selec,	BEE, Color.YELLOW, LAYERS_SIZES_BEE[0],
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
			if (!isInvincible())
				die();
			break;
		default:
			break;
		}
	}

	@Override
	protected void applySeenFitness(List<Integer> seenThings)
	{
		for (int type : seenThings)
		{
			switch (type)
			{
			case VEGETABLE:
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void updateScore()
	{
		brain.addScore(0.01);
	}
}
