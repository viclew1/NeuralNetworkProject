package creatures.adaptative_creatures;

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
import zones.Zone;

import static utils.Constantes.*;

public class AdaptativeCreature extends Creature
{


	private final double FULLY_HERBIVORE = 0, FULLY_CARNIVORE = 1;
	private double regimeBalance = 0.5;

	private final double shareRadius = 8;

	public AdaptativeCreature(double x, double y, Individu brain, Selection selec, World world)
	{
		super(x, y, 2, 150, 1.5, 5, 0.2, new Captor[] {
				new LineCaptor(0, 12),
				new EyeCaptor(Math.PI/7,10,Math.PI/3),
				new EyeCaptor(-Math.PI/7,10,Math.PI/3),
				new EyeCaptor(-Math.PI,6,Math.PI/4),
		}, new int[][] {
			{
				ADAPTATIVE,
			},
			{
				VEGETABLE,
			},
			{
			}},
				brain, selec, ADAPTATIVE, Color.GREEN,
				LAYERS_SIZES_ADAPTATIVE[0], world);
	}
	
	@Override
	public void reset(double x, double y, Individu newBrain)
	{
		super.reset(x, y, newBrain);
		regimeBalance = 0.5;
	}

	@Override
	public void interactWith(Collectable c)
	{
		if (c.isConsumed())
			return;
		switch (c.getType())
		{
		case VEGETABLE:
			collectVegetables();
			c.consume();
			break;
		default:
			break;
		}
	}

	@Override
	public void touchedBy(Creature c)
	{
		if (c.getSelection() != getSelection())
		{
			if (!isInvincible() && (getSelection() == null || getSelection() != c.getSelection()))
			{
				loseHp(25);
				if (!isAlive())
					((AdaptativeCreature)c).eat();
			}
		}
	}


	private void eat()
	{
		heal(50 * regimeBalance / FULLY_CARNIVORE);
		brain.addScore(2 * regimeBalance / FULLY_CARNIVORE);
		for (int i = 0 ; i < creatures.size() ; i++)
		{
			Creature c = creatures.get(i);
			if (c.getSelection() == getSelection() && DistanceChecker.distance(c, this) < shareRadius)
			{
				c.heal(15 * ((AdaptativeCreature)c).regimeBalance / FULLY_CARNIVORE);
				c.getBrain().addScore(0.2 * ((AdaptativeCreature)c).regimeBalance / FULLY_CARNIVORE);
			}
		}
	}
	
	
	@Override
	public void touch(Creature c)
	{
		if (c.getSelection() == getSelection())
			reproduceWith(c);
		else
			collectMeat();
	}

	
	private void collectMeat()
	{
		regimeBalance += 0.01;
		regimeBalance = Math.min(FULLY_CARNIVORE, regimeBalance);
		brain.addScore(1 * regimeBalance / FULLY_CARNIVORE);
	}
	
	private void collectVegetables()
	{
		heal(30 * (1 - regimeBalance) / (1 - FULLY_HERBIVORE));
		regimeBalance -= 0.01;
		regimeBalance = Math.max(FULLY_HERBIVORE, regimeBalance);
		brain.addScore(1 * (1 - regimeBalance) / (1 - FULLY_HERBIVORE));
	}

	@Override
	protected void addParticularInput(double[] inputs, int currentCount)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void applyDecisions(double[] decisions)
	{
		moveFront(0.5 + 0.5*decisions[0]);
		turn(2*(0.5-decisions[1]));
		if (decisions[2] > 0.7)
			share();
	}
	
	private void share()
	{
		for (int i = 0 ; i < creatures.size() ; i++)
		{
			Creature c = creatures.get(i);
			if (c.getSelection() == getSelection() && DistanceChecker.distance(c, this) < shareRadius)
			{
				if (hp > c.getHp())
				{
					double hpSum = hp + c.getHp();
					setHp(hpSum/2);
					c.setHp(hpSum/2);
				}
			}
		}
	}

	@Override
	public void interactWith(Zone z)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void addSpecialFitness()
	{
		for (int i = 0 ; i < creatures.size() ; i++)
			if (creatures.get(i).getSelection() == getSelection())
				if (DistanceChecker.distance(this, creatures.get(i)) < shareRadius)
				{
					brain.addScore(0.01);
					return;
				}
	}

}
