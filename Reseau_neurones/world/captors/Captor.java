package captors;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;
import utils.DistanceChecker;
import utils.IntersectionsChecker;

public abstract class Captor
{
	protected final double range,orientation;
	protected Creature creature;

	private double[] ponderatedResults;
	protected double wallResult;

	protected Rectangle2D around;
	protected Shape hitbox;
	private int[][] thingsToSee;
	private int resultCount;


	public Captor(double orientation, double range)
	{
		this.orientation = orientation;
		this.range=range;
	}

	public void setCreature(Creature creature)
	{
		this.creature=creature;
	}

	public abstract void update(double x, double y, double deltaOrientation);

	public abstract void draw(Graphics g);

	public void detect(List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		int cpt = 0;
		this.ponderatedResults = new double[resultCount];

		if (thingsToSee[0].length > 0)
		{
			Result<Creature>[] closestCreaResult = detectCreatures(creatures);

			for (Result<Creature> r : closestCreaResult)
			{
				double resultDist = 1 - r.getValue() / range;
				if (resultDist > 1) resultDist = 1;
				if (resultDist < 0) resultDist = 0;
				ponderatedResults[cpt++] = resultDist;
			}
		}

		if (thingsToSee[1].length > 0)
		{
			Result<Collectable>[] closestCollecResult = detectCollectables(collectables);

			for (Result<Collectable> r : closestCollecResult)
			{
				double resultDist = 1 - r.getValue() / range;
				if (resultDist > 1) resultDist = 1;
				if (resultDist < 0) resultDist = 0;
				ponderatedResults[cpt++] = resultDist;
			}
		}


		if (thingsToSee[2].length > 0)
		{
			Result<Delimitation>[] closestDelimResult = detectDelimitations(delimitations);

			for (Result<Delimitation> r : closestDelimResult)
			{
				double resultDist = 1 - r.getValue() / range;
				if (resultDist > 1) resultDist = 1;
				if (resultDist < 0) resultDist = 0;
				ponderatedResults[cpt++] = resultDist;
			}
		}

		detectWall(box);
		ponderatedResults[cpt++] = wallResult;
	}

	private Result<Creature>[] detectCreatures(List<Creature> creatures)
	{
		@SuppressWarnings("unchecked")
		Result<Creature>[] closest = new Result[thingsToSee[0].length];
		for (int i = 0 ; i < closest.length ; i++)
			closest[i] = new Result<>(null, Integer.MAX_VALUE);
		for (int i=0; i<creatures.size();i++)
		{
			Creature c = creatures.get(i);
			if (c!=creature)
			{
				for (int j = 0 ; j < closest.length ; j++)
				{
					if (thingsToSee[0][j] == c.getType())
						processDetection(c, closest[j]);
				}

			}
		}
		return closest;
	}

	private Result<Collectable>[] detectCollectables(List<Collectable> collectables)
	{
		@SuppressWarnings("unchecked")
		Result<Collectable>[] closest = new Result[thingsToSee[1].length];
		for (int i = 0 ; i < closest.length ; i++)
			closest[i] = new Result<>(null, Integer.MAX_VALUE);
		for (int i=0;i<collectables.size();i++)
		{
			Collectable c = collectables.get(i);
			for (int j = 0 ; j < closest.length ; j++)
			{
				if (thingsToSee[1][j] == c.getType())
					processDetection(c, closest[j]);
			}
		}
		return closest;
	}

	private Result<Delimitation>[] detectDelimitations(List<Delimitation> delimitations)
	{
		@SuppressWarnings("unchecked")
		Result<Delimitation>[] closest = new Result[thingsToSee[2].length];
		for (int i = 0 ; i < closest.length ; i++)
			closest[i] = new Result<>(null, Integer.MAX_VALUE);
		for (int i=0;i<delimitations.size();i++)
		{
			Delimitation d = delimitations.get(i);
			for (int j = 0 ; j < closest.length ; j++)
			{
				if (thingsToSee[2][j] == d.getType())
					processDetection(d, closest[j]);
			}
		}
		return closest;
	}

	protected abstract void detectWall(DelimitationBox box);


	private void processDetection(Creature c, Result<Creature> closest)
	{
		if (!checkIntersection(c))
			return;
		double value = DistanceChecker.distance(creature, c);
		if (closest.getValue() > value)
		{
			closest.setValue(value);
			closest.setSeen(c);
		}
	}

	private void processDetection(Collectable c, Result<Collectable> closest)
	{
		if (!checkIntersection(c))
			return;
		double value = DistanceChecker.distance(creature, c);
		if (closest.getValue() > value)
		{
			closest.setValue(value);
			closest.setSeen(c);
		}
	}

	private void processDetection(Delimitation d, Result<Delimitation> closest)
	{
		if (!checkIntersection(d))
			return;
		double value = DistanceChecker.distance(creature, d);
		if (closest.getValue() > value)
		{
			closest.setValue(value);
			closest.setSeen(d);
		}
	}

	public double[] getResults()
	{
		return ponderatedResults;
	}

	protected boolean checkIntersection(Creature c)
	{
		if (!IntersectionsChecker.contains(around, c)) return false;
		return IntersectionsChecker.intersects(hitbox,c);
	}

	protected boolean checkIntersection(Collectable c)
	{
		if (!IntersectionsChecker.contains(around, c)) return false;
		return IntersectionsChecker.intersects(hitbox,c);
	}

	protected boolean checkIntersection(Delimitation d)
	{
		if (!IntersectionsChecker.contains(around, d)) return false;
		return IntersectionsChecker.intersects(hitbox,d);
	}

	public void setThingsToSee(int[][] thingsToSee)
	{
		this.thingsToSee = thingsToSee;
		resultCount = 0;
		if (thingsToSee[0].length > 0)
		{
			resultCount += 1;
			resultCount += thingsToSee[0].length;
		}
		if (thingsToSee[1].length > 0)
		{
			resultCount += thingsToSee[1].length;
		}
		if (thingsToSee[2].length > 0)
		{
			resultCount += thingsToSee[2].length;
		}
		resultCount ++;
	}

	public int getResultCount()
	{
		return resultCount;
	}

}
