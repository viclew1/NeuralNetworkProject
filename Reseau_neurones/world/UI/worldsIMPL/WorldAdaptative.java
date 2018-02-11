package UI.worldsIMPL;

import static utils.Constantes.*;

import java.awt.Dimension;
import java.util.Random;

import UI.World;
import collectables.Vegetable;

public class WorldAdaptative extends World
{

	public WorldAdaptative()
	{
		super("World adaptative");
	}

	public WorldAdaptative(Dimension dimensions)
	{
		super("World adaptative", dimensions);
	}

	public WorldAdaptative(int w, int h)
	{
		super("World adaptative", w, h);
	}

	@Override
	protected void initSelections()
	{
		collectableAmount = FOOD_AMOUNT;
		initSelection(POPULATION_SIZE_ADAPTATIVE, GENERATION_COUNT, TYPE_ADAPTATIVE);
		initSelection(POPULATION_SIZE_ADAPTATIVE, GENERATION_COUNT, TYPE_ADAPTATIVE);
		initSelection(POPULATION_SIZE_ADAPTATIVE, GENERATION_COUNT, TYPE_ADAPTATIVE);
		initSelection(POPULATION_SIZE_ADAPTATIVE, GENERATION_COUNT, TYPE_ADAPTATIVE);
	}

	@Override
	protected void generateCollectables()
	{
		collectables.add(new Vegetable(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6)));
	}

	@Override
	protected void generateDelimitations()
	{
		// TODO Auto-generated method stub

	}

}
