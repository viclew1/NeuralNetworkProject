package captors;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;
import zones.Zone;

public abstract class Captor
{
	protected Creature creature;
	protected double resultBees,resultWasps,resultVegetable,resultMeat,
	resultSoldier,resultTank,resultProjectile,resultFuel,resultPowerUp, 
	resultFireBall,	resultComplexDodger, resultSimpleDodger, resultWall;
	protected final double range;
	private int[] thingsToSee;
	protected Rectangle2D around;
	protected Path2D hitbox;
	protected double[] x,y;
	
	
	public Captor(double range)
	{
		this.range=range;
		hitbox = new Path2D.Double();
	}

	public void setCreature(Creature creature)
	{
		this.creature=creature;
	}

	public void setThingsToSee(int[] thingsToSee)
	{
		this.thingsToSee=thingsToSee;
	}

	public abstract void update(double x, double y, double sz, double deltaOrientation);

	public void draw(Graphics g)
	{
		int[] xPoints = new int[x.length];
		int[] yPoints = new int[x.length];
		for (int i=0;i<x.length;i++)
		{
			xPoints[i] = (int)(x[i]*SIZE+SCROLL_X);
			yPoints[i] = (int)(y[i]*SIZE+SCROLL_Y);
		}
		Color color = g.getColor();
		g.setColor(Color.BLACK);
		Path2D hitboxOnScreen = new Path2D.Double();
		hitboxOnScreen.moveTo(xPoints[0], yPoints[0]);
		for(int i = 1; i < xPoints.length; ++i)
			hitboxOnScreen.lineTo(xPoints[i], yPoints[i]);
		hitboxOnScreen.closePath();
		((Graphics2D)g).draw(hitboxOnScreen);
		g.setColor(color);
	}

	public void detect(List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, List<Zone> zones, DelimitationBox box)
	{
		detectCreatures(creatures);
		detectCollectables(collectables);
		detectZones(zones);
		detectDelimitations(delimitations, box);
	}

	protected abstract void detectCreatures(List<Creature> creatures);
	protected abstract void detectCollectables(List<Collectable> collectables);
	protected abstract void detectZones(List<Zone> zones);
	protected abstract void detectDelimitations(List<Delimitation> delimitations, DelimitationBox box);

	public List<Double> getResults()
	{
		List<Double> results = new ArrayList<>();
		if (sees(BEE))
			results.add(resultBees);
		if (sees(WASP))
			results.add(resultWasps);
		if (sees(VEGETABLE))
			results.add(resultVegetable);
		if (sees(MEAT))
			results.add(resultMeat);
		if (sees(SOLDIER))
			results.add(resultSoldier);
		if (sees(TANK))
			results.add(resultTank);
		if (sees(PROJECTILE))
			results.add(resultProjectile);
		if (sees(FUEL))
			results.add(resultFuel);
		if (sees(POWERUP))
			results.add(resultPowerUp);
		if (sees(FIREBALL))
			results.add(resultFireBall);
		if (sees(COMPLEXDODGER))
			results.add(resultComplexDodger);
		if (sees(SIMPLEDODGER))
			results.add(resultSimpleDodger);

		results.add(resultWall);
		
		return results;
	}

	private boolean sees(int type)
	{
		for (int i=0 ; i<thingsToSee.length ; i++)
			if (thingsToSee[i]==type)
				return true;
		return false;
	}

	public Path2D getHitBox()
	{
		return hitbox;
	}
	
}
