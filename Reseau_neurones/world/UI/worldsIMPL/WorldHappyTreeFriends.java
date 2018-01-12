package UI.worldsIMPL;

import static utils.Constantes.*;

import java.awt.Dimension;
import java.util.Random;

import UI.World;
import collectables.Vegetable;

public class WorldHappyTreeFriends extends World{
	
	public WorldHappyTreeFriends(String name)
	{
		super(name);
	}

	public WorldHappyTreeFriends(String name, Dimension dimensions)
	{
		super(name, dimensions);
	}

	public WorldHappyTreeFriends(String name, int w, int h)
	{
		super(name, w, h);
	}

	protected void initSelections() {
		collectableAmount = FOOD_AMOUNT;
		initSelection(POPULATION_SIZE_SLUG, GENERATION_COUNT, TYPE_SLUG);
		initSelection(POPULATION_SIZE_HEDGEHOG, GENERATION_COUNT, TYPE_HEDGEHOG);
		initSelection(POPULATION_SIZE_RHINOCEROS, GENERATION_COUNT, TYPE_RHINOCEROS);
	}

	protected void generateCollectables() {
		collectables.add(new Vegetable(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6)));
	}

	protected void generateDelimitations() {
		
	}

}
