package UI.worldsIMPL;

import static utils.Constantes.*;

import java.awt.Dimension;
import java.util.Random;

import UI.World;
import collectables.expirables.Fuel;

public class WorldOfTanks extends World
{


	public WorldOfTanks()
	{
		super();
	}
	
	public WorldOfTanks(Dimension dimensions)
	{
		super(dimensions);
	}
	
	public WorldOfTanks(int w, int h)
	{
		super(w, h);
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
