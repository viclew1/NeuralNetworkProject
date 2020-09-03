package creatures.adaptative_creatures;

import static utils.Constantes.ADAPTATIVE;
import static utils.Constantes.LAYERS_SIZES_ADAPTATIVE;
import static utils.Constantes.VEGETABLE;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import captors.LineCaptor;
import collectables.Collectable;
import creatures.Creature;
import fr.lewon.Individual;
import zones.Zone;

public class AdaptativeCreature extends Creature
{


	private final double FULLY_HERBIVORE = 0, FULLY_CARNIVORE = 1;
	private double regimeBalance = 0.5;

	private final double shareRadius = 8;

	public AdaptativeCreature(double x, double y, Individual brain, World world)
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
				brain, ADAPTATIVE, Color.GREEN,
				LAYERS_SIZES_ADAPTATIVE[0], world);
	}
	
	@Override
	public void reset(double x, double y, Individual newBrain)
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
	}


	private void eat()
	{
	}
	
	
	@Override
	public void touch(Creature c)
	{
	}

	
	private void collectMeat()
	{
	}
	
	private void collectVegetables()
	{
	}

	@Override
	protected void addParticularInput(double[] inputs, int currentCount)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void applyDecisions(List<Double> decisions)
	{
	}
	
	private void share()
	{
	}

	@Override
	public void interactWith(Zone z)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void addSpecialFitness()
	{
	}

}
