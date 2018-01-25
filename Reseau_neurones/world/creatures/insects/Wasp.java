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

public class Wasp extends InsectCreature
{

	public Wasp(double x, double y, Individu brain, Selection selec, World world)
	{
		super(x, y, 2, 400, 0.8, 2, 1,
				new Captor[]{
						new EyeCaptor(0,45,0.001),
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
			if (hp/hpMax < 0.5 && c.getHp()/hpMax > 0.5)
			{
				c.loseHp(3);
				this.hp += 3;
				brain.addScore(5);
				c.getBrain().addScore(5);
			}
			break;
		case BEE:
			if (!c.isInvincible())
			{
				brain.addScore(50);
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
		// TODO Auto-generated method stub

	}
}