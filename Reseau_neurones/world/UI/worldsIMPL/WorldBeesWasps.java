package UI.worldsIMPL;

import static utils.Constantes.*;

import java.awt.Dimension;
import java.util.Random;

import UI.World;
import collectables.Vegetable;

public class WorldBeesWasps extends World
{


	public WorldBeesWasps(String name)
	{
		super(name);
	}
	
	public WorldBeesWasps(String name, Dimension dimensions)
	{
		super(name, dimensions);
	}
	
	public WorldBeesWasps(String name, int w, int h)
	{
		super(name, w, h);
	}
	
	@Override
	protected void initSelections()
	{
		collectableAmount = FOOD_AMOUNT;
		initSelection(POPULATION_SIZE_BEE, GENERATION_COUNT, TYPE_BEE);
		initSelection(POPULATION_SIZE_WASP, GENERATION_COUNT, TYPE_WASP);
	}

	@Override
	protected void generateCollectables()
	{
		/*if (meatCount > vegetableCount)
		{
			vegetableCount++;*/
			collectables.add(new Vegetable(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6)));
		/*}
		else
		{
			meatCount++;
			collectables.add(new Meat(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6)));
		}*/
	}

	@Override
	protected void generateDelimitations()
	{
		// TODO Auto-generated method stub
		
	}

}
