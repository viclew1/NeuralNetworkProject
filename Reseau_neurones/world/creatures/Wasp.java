package creatures;

import static utils.Constantes.*;

import java.awt.Color;

import collectables.Collectable;
import creatures.captors.Captor;
import creatures.captors.EyeCaptor;
import genetics.Individu;
import limitations.Delimitation;

public class Wasp extends Creature
{

	public Wasp(double x, double y, Individu brain)
	{
		super(x, y, 2, 400, 0.35,
				new Captor[]{
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI,6,Math.PI/4),
				},
				brain, WASP, Color.ORANGE);
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
	protected void updatePosition()
	{
		hp--;
		if (hp<=0)
			alive=false;
		double[] inputs = new double[INPUT_COUNT_WASP];
		int cpt=0;
		for (int i=0;i<captors.length;i++)
		{
			double[] results = captors[i].getResults();
			for (int j=0;j<results.length;j++)
			{
				inputs[cpt] = results[j];
				cpt++;
			}
		}
		inputs[cpt]=hp/hpMax;
		double[] decisions = brain.getOutputs(inputs);
		turn(2*(0.5-decisions[0]));
		forward(1);
	}

}