package captors;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;
import utils.DistanceChecker;
import zones.Zone;

public abstract class Captor
{
	protected final double range;
	protected Creature creature;
	
	private int[] thingsToSee;
	private double[] results;

	protected Rectangle2D around;
	
	public Captor(double range)
	{
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
		updateHitbox();
		for (int i=0;i<results.length;i++)
			results[i] = Double.MAX_VALUE;
		detectCreatures(creatures);
		detectCollectables(collectables);
		detectDelimitations(delimitations);
	}
	
	protected abstract void updateHitbox();

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

	private void detectZones(List<Zone> zones)
	{
		for (int i=0;i<zones.size();i++)
		{
			Zone z = zones.get(i);
			if (z==null || !around.contains(z.getHitBox().getBounds2D()))
				continue;
			processDetection(z);
		}
	}

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

	private void processDetection(Zone z)
	{
		int type = z.getType();
		int index = -1;
		for (int i=0 ; i<thingsToSee.length ; i++)
		{
			if (thingsToSee[i]==type)
			{
				index=i;
				break;
			}
		}
		if (index==-1 || !checkIntersection(z))
			return;
		double value = DistanceChecker.distance(creature, z);
		if (results[index] > value)
			results[index] = value;
	}

	public double[] getResults()
	{
		double[] ponderatedResults = new double[results.length];
		for (int i=0 ; i<results.length ; i++)
		{
			double result = results[i];
			result/=range;
			if (result > 1) result = 0;
			else result = 1 - result;
			ponderatedResults[i] = result;
		}

		return ponderatedResults;
	}
	
	protected abstract boolean checkIntersection(Creature c);
	protected abstract boolean checkIntersection(Collectable c);
	protected abstract boolean checkIntersection(Delimitation d);
	protected abstract boolean checkIntersection(Zone z);
	
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
