package creatures.insects;

import static utils.Constantes.*;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import captors.LineCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;

public class Wasp extends InsectCreature
{

	public Wasp(double x, double y, Individu brain, Selection selec, World world)
	{
		super(x, y, 2, 400, 1.15, 2, 1,
				new Captor[]{
						new LineCaptor(0, 17),
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI,6,Math.PI/4),
		},
				brain, selec, WASP, Color.ORANGE, LAYERS_SIZES_WASP[0],
				world);
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
			if (!c.isInvincible())
			{
				hp+=50;
				if (hp>hpMax)
					hp=hpMax;
			}
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
			case BEE:
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