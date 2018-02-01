package adaptative_creatures;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import zones.Zone;

import static utils.Constantes.*;

public class AdaptativeCreature extends Creature
{

	
	
	public AdaptativeCreature(double x, double y, Individu brain, Selection selec, World world)
	{
		super(x, y, 2, 400, 1.5, 5, 0.2, new Captor[] {
				
		}, new int[] {
				ADAPTATIVE, VEGETABLE
		}, brain, selec, ADAPTATIVE, Color.GREEN,
				LAYERS_SIZES_ADAPTATIVE[0], world);
	}

	@Override
	protected void updateScore()
	{
		brain.addScore(0.01);
	}

	@Override
	public void interactWith(Collectable c)
	{
		if (c.isConsumed())
			return;
		switch (c.getType())
		{
		case VEGETABLE:
			brain.addScore(500);
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
	protected void addParticularInput(double[] inputs, int currentCount)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void applyDecisions(double[] decisions)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactWith(Zone z)
	{
		// TODO Auto-generated method stub
		
	}

}
