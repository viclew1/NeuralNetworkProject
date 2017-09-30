package captors;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;

public abstract class Captor
{
	protected double[] x,y;
	protected final Path2D hitbox;
	protected Creature creature;
	protected double resultBees,resultWasps,resultVegetable,resultMeat,
	resultSoldier,resultTank,resultProjectile,resultFuel,resultPowerUp, 
	resultFireBall,	resultComplexDodger, resultSimpleDodger, resultWall;
	protected final double range;
	private int[] thingsToSee;
	protected Rectangle2D around;

	public Captor(double range)
	{
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
	}

	public abstract void update(double x, double y, double sz, double deltaOrientation);

	public void draw(Graphics g)
	{
		Color color = g.getColor();
		g.setColor(Color.BLACK);
		int[] xFinal = new int[x.length];
		int[] yFinal = new int[y.length];
		for (int i=0 ; i<x.length ; i++)
		{
			xFinal[i] = (int) (x[i]*SIZE+SCROLL_X);
			yFinal[i] = (int) (y[i]*SIZE+SCROLL_Y);
		}
		g.drawPolygon(xFinal, yFinal, x.length);
		g.setColor(Color.RED);
		g.drawRect((int)(around.getX()*SIZE+SCROLL_X), (int)(around.getY()*SIZE+SCROLL_Y), (int)(around.getWidth()*SIZE), (int)(around.getHeight()*SIZE));
		g.setColor(color);
	}

	public void detect(List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		detectCreatures(creatures);
		detectCollectables(collectables);
		detectDelimitations(delimitations, box);
	}

	protected abstract void detectCreatures(List<Creature> creatures);
	protected abstract void detectCollectables(List<Collectable> collectables);
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

}
