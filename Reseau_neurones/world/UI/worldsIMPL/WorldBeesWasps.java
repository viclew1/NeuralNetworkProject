package UI.worldsIMPL;

import static utils.Constantes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import UI.World;
import collectables.Meat;
import collectables.Vegetable;

public class WorldBeesWasps extends World
{

	private static final long serialVersionUID = 919723644950485137L;

	@Override
	protected void initSelections()
	{
		GENERATION_LENGTH = 80000;
		collectableAmount = FOOD_AMOUNT;
		initSelection(POPULATION_SIZE_BEE, GENERATION_COUNT, TYPE_BEE);
		initSelection(POPULATION_SIZE_WASP, GENERATION_COUNT, TYPE_WASP);
	}

	@Override
	protected void generateCollectables()
	{
		if (meatCount > vegetableCount)
		{
			vegetableCount++;
			collectables.add(new Vegetable(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6)));
		}
		else
		{
			meatCount++;
			collectables.add(new Meat(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6)));
		}
	}

	@Override
	protected void generateDelimitations()
	{
		// TODO Auto-generated method stub
		
	}

}
