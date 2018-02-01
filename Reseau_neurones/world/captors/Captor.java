package captors;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
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

	private int[] thingsToSee;
	private double[] results;
	protected double wallResult;

	protected Rectangle2D around;
	protected Shape hitbox;

	
	public Captor(double orientation, double range)
	{
		this.orientation = orientation;
		this.range=range;
	}

	public void setCreature(Creature creature)
	{
		this.creature=creature;
	}

	public void setThingsToSee(int[] thingsToSee)
	{
		this.thingsToSee=thingsToSee;
		this.results = new double[thingsToSee.length];
	}

	public abstract void update(double x, double y, double deltaOrientation);

	public abstract void draw(Graphics g);

	public void detect(List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		for (int i=0;i<results.length;i++)
			results[i] = Double.MAX_VALUE;
		detectCreatures(creatures);
		detectCollectables(collectables);
		detectDelimitations(delimitations);
		detectWall(box);
	}

	private void detectCreatures(List<Creature> creatures)
	{
		for (int i=0; i<creatures.size();i++)
		{
			Creature c = creatures.get(i);
			if (c!=null && c!=creature && around.contains(c.getX()+c.getSize()/2,c.getY()+c.getSize()/2))
				processDetection(c);
		}
	}

	private void detectCollectables(List<Collectable> collectables)
	{
		for (int i=0;i<collectables.size();i++)
		{
			Collectable c = collectables.get(i);
			if (c!=null && around.contains(c.getX()+c.getSize()/2,c.getY()+c.getSize()/2))
				processDetection(c);
		}
	}

	private void detectDelimitations(List<Delimitation> delimitations)
	{
		for (int i=0;i<delimitations.size();i++)
		{
			Delimitation d = delimitations.get(i);
			if (d!=null && around.contains(d.getX()+d.getW()/2,d.getY()+d.getH()/2))
				processDetection(d);
		}
	}

	protected abstract void detectWall(DelimitationBox box);


	private void processDetection(Creature c)
	{
		int type = c.getType();
		int index = -1;
		for (int i=0 ; i<thingsToSee.length ; i++)
		{
			if (thingsToSee[i]==type)
			{
				index=i;
				break;
			}
		}
		if (index==-1 || !checkIntersection(c))
			return;
		double value = DistanceChecker.distance(creature, c);
		if (results[index] > value)
			results[index] = value;
	}

	private void processDetection(Collectable c)
	{
		int type = c.getType();
		int index = -1;
		for (int i=0 ; i<thingsToSee.length ; i++)
		{
			if (thingsToSee[i]==type)
			{
				index=i;
				break;
			}
		}
		if (index==-1 || !checkIntersection(c))
			return;
		double value = DistanceChecker.distance(creature, c);
		if (results[index] > value)
			results[index] = value;
	}

	private void processDetection(Delimitation d)
	{
		int type = d.getType();
		int index = -1;
		for (int i=0 ; i<thingsToSee.length ; i++)
		{
			if (thingsToSee[i]==type)
			{
				index=i;
				break;
			}
		}
		if (index==-1 || !checkIntersection(d))
			return;
		double value = DistanceChecker.distance(creature, d);
		if (results[index] > value)
			results[index] = value;
	}

	public double[] getResults()
	{
		double[] ponderatedResults = new double[results.length + 1];
		for (int i=0 ; i<results.length ; i++)
		{
			results[i]/=range;
			if (results[i] > 1) results[i] = 0;
			else results[i] = 1 - results[i];
			ponderatedResults[i] = results[i];
		}
		ponderatedResults[results.length] = wallResult;
		return ponderatedResults;
	}
	
	protected boolean checkIntersection(Creature c)
	{
		return IntersectionsChecker.intersects(hitbox,c);
	}

	protected boolean checkIntersection(Collectable c)
	{
		return IntersectionsChecker.intersects(hitbox,c);
	}

	protected boolean checkIntersection(Delimitation d)
	{
		return IntersectionsChecker.intersects(hitbox,d);
	}

	public List<Integer> getThingsInSight()
	{
		List<Integer> seen = new ArrayList<>();
		for (int i = 0 ; i < results.length ; i++)
		{
			if (results[i] != 0)
				seen.add(thingsToSee[i]);
		}
		return seen;
	}
}
