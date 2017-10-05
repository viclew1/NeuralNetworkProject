package captors;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;
import utils.DistanceChecker;
import utils.IntersectionsChecker;
import zones.Zone;

public abstract class Captor
{
	protected double[] xPoints,yPoints;
	protected final double range;
	protected Creature creature;
	
	private int[] thingsToSee;
	private double[] results;
	
	protected final Path2D hitbox;
	protected Rectangle2D around;

	public Captor(double range, int nbPoints)
	{
		xPoints = new double[nbPoints];
		yPoints = new double[nbPoints];
		this.range=range;
		this.hitbox = new Path2D.Double();
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

	public void draw(Graphics g)
	{
		Color color = g.getColor();
		g.setColor(Color.BLACK);
		int[] xFinal = new int[xPoints.length];
		int[] yFinal = new int[yPoints.length];
		for (int i=0 ; i<xPoints.length ; i++)
		{
			xFinal[i] = (int) (xPoints[i]*SIZE+SCROLL_X);
			yFinal[i] = (int) (yPoints[i]*SIZE+SCROLL_Y);
		}
		g.drawPolygon(xFinal, yFinal, xPoints.length);
		g.setColor(Color.RED);
		g.drawRect((int)(around.getX()*SIZE+SCROLL_X), (int)(around.getY()*SIZE+SCROLL_Y), (int)(around.getWidth()*SIZE), (int)(around.getHeight()*SIZE));
		g.setColor(color);
	}

	public void detect(List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		hitbox.reset();
		hitbox.moveTo(xPoints[0], yPoints[0]);
		for(int i = 1; i < xPoints.length; ++i) {
			hitbox.lineTo(xPoints[i], yPoints[i]);
		}
		hitbox.closePath();
		around = new Rectangle2D.Double(xPoints[0]-range,yPoints[0]-range,range*2,range*2);
		for (int i=0;i<results.length;i++)
			results[i] = Double.MAX_VALUE;
		detectCreatures(creatures);
		detectCollectables(collectables);
		detectDelimitations(delimitations);
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
		if (index==-1 || !IntersectionsChecker.intersects(hitbox,c))
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
		if (index==-1 || !IntersectionsChecker.intersects(hitbox,c))
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
		if (index==-1 || !IntersectionsChecker.intersects(hitbox,d))
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
		if (index==-1 || !IntersectionsChecker.intersects(hitbox,z))
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
}
