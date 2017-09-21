package utils;

import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;

public class IntersectionsChecker
{

	public static boolean intersects(Creature creature, Collectable collect)
	{
		return new Rectangle2D.Double(creature.getX(), creature.getY(), creature.getSize(), creature.getSize())
				.intersects(new Rectangle2D.Double(collect.getX(), collect.getY(), collect.getSize(), collect.getSize()));
	}

	public static boolean intersects(Creature creature, Delimitation delim)
	{
		return new Rectangle2D.Double(creature.getX(), creature.getY(), creature.getSize(), creature.getSize())
				.intersects(new Rectangle2D.Double(delim.getX(), delim.getY(), delim.getW(), delim.getH()));
	}
	
	public static boolean intersects(Creature creature1, Creature creature2)
	{
		return new Rectangle2D.Double(creature1.getX(), creature1.getY(), creature1.getSize(), creature1.getSize())
				.intersects(new Rectangle2D.Double(creature2.getX(), creature2.getY(), creature2.getSize(), creature2.getSize()));
	}
	
	public static boolean intersects(Line2D captorLine, Creature creature)
	{
		Rectangle2D creatureRect = new Rectangle2D.Double(creature.getX(), creature.getY(), creature.getSize(), creature.getSize());
		return creatureRect.intersectsLine(captorLine);
	}
	
	public static boolean intersects(Line2D captorLine, Collectable collec)
	{
		Rectangle2D collecRect = new Rectangle2D.Double(collec.getX(), collec.getY(), collec.getSize(), collec.getSize());
		return collecRect.intersectsLine(captorLine);
	}
	
	public static boolean intersects(Line2D captorLine, Delimitation delim)
	{
		Rectangle2D delimRect = new Rectangle2D.Double(delim.getX(), delim.getY(), delim.getW(), delim.getH());
		return delimRect.intersectsLine(captorLine);
	}
	
	public static boolean intersects(Line2D captorLine1, Line2D captorLine2, Creature creature)
	{
		Point2D creaturePoint = new Point2D.Double(creature.getX()+creature.getSize()/2, creature.getY()+creature.getSize()/2);
		return intersects(captorLine1,captorLine2,creaturePoint);
	}
	
	public static boolean intersects(Line2D captorLine1, Line2D captorLine2, Collectable collec)
	{
		Point2D collecPoint = new Point2D.Double(collec.getX()+collec.getSize()/2, collec.getY()+collec.getSize()/2);
		return intersects(captorLine1,captorLine2,collecPoint);
	}
	
	public static boolean intersects(Line2D captorLine1, Line2D captorLine2, Delimitation delim)
	{
		Point2D delimPoint = new Point2D.Double(delim.getX()+delim.getW()/2, delim.getY()+delim.getH()/2);
		return intersects(captorLine1,captorLine2,delimPoint);
	}
	
	public static boolean intersects(Line2D captorLine1, Line2D captorLine2, DelimitationBox delim)
	{
		Rectangle2D delimRect = new Rectangle2D.Double(delim.getX(), delim.getY(), delim.getW(), delim.getH());
		if (!delimRect.contains(captorLine1.getX2(),captorLine1.getY2()))
			return true;
		if (!delimRect.contains(captorLine2.getX2(),captorLine2.getY2()))
			return true;
		return false;
			
	}
	
	private static boolean intersects(Line2D line1, Line2D line2, Point2D point)
	{
		double[] xPoints=new double[] {line1.getX1(),line1.getX2(),line2.getX2()};
		double[] yPoints=new double[] {line1.getY1(),line1.getY2(),line2.getY2()};
		Path2D path = new Path2D.Double();

		path.moveTo(xPoints[0], yPoints[0]);
		for(int i = 1; i < xPoints.length; ++i) {
		   path.lineTo(xPoints[i], yPoints[i]);
		}
		path.closePath();
		return path.contains(point);
	}
	
	public static boolean contains(Delimitation delim, Creature creature)
	{
		return new Rectangle2D.Double(delim.getX(), delim.getY(), delim.getW(), delim.getH())
				.contains(new Point2D.Double(creature.getX()+creature.getSize()/2, creature.getY()+creature.getSize()/2));
	}
	
	public static boolean contains(DelimitationBox box, Delimitation delim)
	{
		return new Rectangle2D.Double(box.getX(), box.getY(), box.getW(), box.getH())
				.contains(new Point2D.Double(delim.getX()+delim.getW()/2, delim.getY()+delim.getH()/2));
	}

}
