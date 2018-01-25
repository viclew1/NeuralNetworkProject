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

	public void attack()
	{
		
	}
	
	@Override
	protected void updateScore()
	{
		brain.addScore(0.01);
	}

	@Override
	protected void addParticularInput(double[] inputs, int currentCount)
	{
		
	}

	@Override
	protected void applySeenFitness(List<Integer> seenThings)
	{
		
	}

	@Override
	protected void applyDecisions(double[] decisions)
	{
		
	}

	@Override
	public void interactWith(Collectable c)
	{
		
	}

	@Override
	public void interactWith(Creature c)
	{
		
	}

	@Override
	public void interactWith(Zone z)
	{
		
	}

}
