package creatures.captors;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import utils.DistanceChecker;
import utils.IntersectionsChecker;

public class EyeCaptor extends Captor
{

	private final double orientation,widthAngle;
	private Line2D line1,line2;

	public EyeCaptor(double orientation, double range, double widthAngle)
	{
		super(range);
		this.orientation=orientation;
		this.widthAngle=widthAngle;
	}

	@Override
	public void update(double x, double y, double sz, double deltaOrientation)
	{
		double xCenter = x + sz/2;
		double yCenter = y + sz/2;
		Point2D p1 = new Point2D.Double(xCenter, yCenter);
		double x1Front = xCenter + Math.cos(orientation+widthAngle/2+deltaOrientation)*range;
		double y1Front = yCenter - Math.sin(orientation+widthAngle/2+deltaOrientation)*range;
		double x2Front = xCenter + Math.cos(orientation-widthAngle/2+deltaOrientation)*range;
		double y2Front = yCenter - Math.sin(orientation-widthAngle/2+deltaOrientation)*range;
		Point2D p2 = new Point2D.Double(x1Front, y1Front);
		Point2D p3 = new Point2D.Double(x2Front, y2Front);
		line1 = new Line2D.Double(p1, p2);
		line2 = new Line2D.Double(p1, p3);
	}

	@Override
	public void draw(Graphics g)
	{
		Color color = g.getColor();
		if (resultBees!=0)
			g.setColor(Color.YELLOW);
		else if (resultWasps!=0)
			g.setColor(Color.ORANGE);
		else if (resultVegetable!=0)
			g.setColor(Color.GREEN);
		else if (resultMeat!=0)
			g.setColor(Color.RED);
		else if (resultWalls!=0)
			g.setColor(Color.DARK_GRAY);
		g.drawLine((int)(line1.getX1()*SIZE+SCROLL_X),
				(int)(line1.getY1()*SIZE+SCROLL_Y), 
				(int)(line1.getX2()*SIZE+SCROLL_X), 
				(int)(line1.getY2()*SIZE+SCROLL_Y));
		g.drawLine((int)(line2.getX1()*SIZE+SCROLL_X),
				(int)(line2.getY1()*SIZE+SCROLL_Y), 
				(int)(line2.getX2()*SIZE+SCROLL_X), 
				(int)(line2.getY2()*SIZE+SCROLL_Y));
		g.setColor(color);
	}

	@Override
	protected void detectCreatures(List<Creature> creatures)
	{
		resultBees = Double.MAX_VALUE;
		resultWasps = Double.MAX_VALUE;
		double dist;
		for (int i=0; i<creatures.size();i++)
		{
			Creature c = creatures.get(i);
			if (c!=null && c!=creature)
				if (c.getType()==BEE)
				{
					if (IntersectionsChecker.intersects(line1,line2,c))
						if ((dist = DistanceChecker.distance(creature, c))<resultBees)
							resultBees=dist;
				}
				else if (c.getType()==WASP)
				{
					if (IntersectionsChecker.intersects(line1,line2,c))
						if ((dist = DistanceChecker.distance(creature, c))<resultWasps)
							resultWasps=dist;
				}
		}
		resultBees/=range;
		if (resultBees > 1) resultBees = 0;
		else resultBees = 1 - resultBees;
		resultWasps/=range;
		if (resultWasps > 1) resultWasps = 0;
		else resultWasps = 1 - resultWasps;
	}

	@Override
	protected void detectCollectables(List<Collectable> collectables)
	{
		resultVegetable = Double.MAX_VALUE;
		resultMeat = Double.MAX_VALUE;
		double dist;
		for (int i=0;i<collectables.size();i++)
		{
			Collectable c = collectables.get(i);
			if (c.getType()==VEGETABLE)
			{
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultVegetable)
						resultVegetable=dist;
			}
			else if (c.getType()==MEAT)
			{
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultMeat)
						resultMeat=dist;
			}
		}
		resultVegetable/=range;
		if (resultVegetable > 1) resultVegetable = 0;
		else resultVegetable = 1 - resultVegetable;
		resultMeat/=range;
		if (resultMeat > 1) resultMeat = 0;
		else resultMeat = 1 - resultMeat;
	}

	@Override
	protected void detectDelimitations(List<Delimitation> delimitations)
	{
		for (int i=0;i<delimitations.size();i++)
		{
			Delimitation d = delimitations.get(i);
			if (IntersectionsChecker.intersects(line1,line2,d))
			{
				resultWalls=1;
				return;
			}
		}
		resultWalls = 0;
	}

}
