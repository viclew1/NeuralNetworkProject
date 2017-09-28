package UI.worldsIMPL;

import static utils.Constantes.*;

import java.util.Random;

import UI.World;
import collectables.expirables.Fuel;

public class WorldOfTanks extends World
{

	private static final long serialVersionUID = -8366677204022090143L;

	@Override
	protected void initSelections()
	{
		GENERATION_LENGTH = 80000;
		collectableAmount = FUEL_AMOUNT;
		//initSelection(POPULATION_SIZE_SOLDIER, GENERATION_COUNT, TYPE_SOLDIER);
		initSelection(POPULATION_SIZE_TANK, GENERATION_COUNT, TYPE_TANK);
	}

	@Override
	protected void generateCollectables()
	{
		collectables.add(new Fuel(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6)));
	}

	@Override
	protected void generateDelimitations()
	{
		// TODO Auto-generated method stub
		
	}

}
