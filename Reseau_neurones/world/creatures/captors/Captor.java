package creatures.captors;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;

public abstract class Captor
{
	protected Creature creature;
	protected double resultBees,resultWasps,resultVegetable,resultWalls,resultMeat;
	protected final double range;

	public Captor(double range)
	{
		this.range=range;
	}

	public void setCreature(Creature creature)
	{
		this.creature=creature;
	}

	public abstract void update(double x, double y, double sz, double deltaOrientation);

	public abstract void draw(Graphics g);

	public void detect(List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations)
	{
		detectCreatures(creatures);
		detectCollectables(collectables);
		detectDelimitations(delimitations);
	}

	protected abstract void detectCreatures(List<Creature> creatures);
	protected abstract void detectCollectables(List<Collectable> collectables);
	protected abstract void detectDelimitations(List<Delimitation> delimitations);

	public List<Double> getResults()
	{
		List<Double> results = new ArrayList<>();
		results.add(resultBees);
		results.add(resultWasps);
		results.add(resultVegetable);
		results.add(resultMeat);
		results.add(resultWalls);
		return results;
	}

}
