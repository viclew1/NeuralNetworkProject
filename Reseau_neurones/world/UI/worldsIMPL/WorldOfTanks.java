package UI.worldsIMPL;

import static utils.Constantes.*;

import java.awt.Dimension;
import java.util.Random;

import UI.World;
import collectables.expirables.Fuel;

public class WorldOfTanks extends World
{


	public WorldOfTanks(String name)
	{
		super(name);
	}
	
	public WorldOfTanks(String name, Dimension dimensions)
	{
		super(name, dimensions);
	}
	
	public WorldOfTanks(String name, int w, int h)
	{
		super(name, w, h);
	}
	
	@Override
	protected void initSelections()
	{
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
