package creatures.insects;

import static utils.Constantes.*;

import java.awt.Color;
import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import captors.LineCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import utils.DistanceChecker;

public class Wasp extends InsectCreature
{

	public Wasp(double x, double y, Individu brain, Selection selec, World world)
	{
		super(x, y, 2, 400, 0.9, 2, 2,
				new Captor[]{
						new LineCaptor(0, 17),
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
		},
				new int[][] {
			{
				WASP,
				BEE,
			},
			{
			},
			{
			}},
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
			brain.addScore(1);
			heal(25);
			c.consume();
			break;
		default:
			break;
		}
	}

	@Override
	public void touchedBy(Creature c)
	{
		// TODO Auto-generated method stub

	}

	private void eat()
	{
		heal(50);
		brain.addScore(1);
		for (int i = 0 ; i < creatures.size() ; i++)
		{
			Creature c = creatures.get(i);
			if (c.getSelection() == getSelection() && DistanceChecker.distance(c, this) < shareRadius)
			{
				c.heal(15);
				c.getBrain().addScore(0.2);
			}
		}
	}

	@Override
	public void touch(Creature c)
	{
		switch (c.getType())
		{
		case WASP:
			reproduceWith(c);
			break;
		case BEE:
			if (!c.isInvincible())
				eat();
			break;
		default:
			break;
		}
	}

	@Override
	protected void addSpecialFitness()
	{
		for (int i = 0 ; i < creatures.size() ; i++)
			if (creatures.get(i).getSelection() == getSelection())
				if (DistanceChecker.distance(this, creatures.get(i)) < shareRadius)
					brain.addScore(0.01);

	}

}