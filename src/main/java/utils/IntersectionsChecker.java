package utils;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import zones.Zone;

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
	
	public static boolean intersects(Shape captorHitbox, Creature creature)
	{
		Rectangle2D creatureRect = new Rectangle2D.Double(creature.getX(), creature.getY(), creature.getSize(), creature.getSize());
		return intersects(captorHitbox,creatureRect);
	}
	
	public static boolean intersects(Shape captorHitbox, Collectable collec)
	{
		Rectangle2D collecRect = new Rectangle2D.Double(collec.getX(), collec.getY(), collec.getSize(), collec.getSize());
		return intersects(captorHitbox,collecRect);
	}
	
	public static boolean intersects(Shape captorHitbox, Delimitation delim)
	{
		Rectangle2D delimRect = new Rectangle2D.Double(delim.getX(), delim.getY(), delim.getW(), delim.getH());
		return intersects(captorHitbox,delimRect);
	}
	
	private static boolean intersects(Shape captorHitbox, Rectangle2D rect)
	{
		return captorHitbox.intersects(rect);
	}
	
	public static boolean contains(Rectangle2D box, Creature creature)
	{
		return box.contains(new Point2D.Double(creature.getX()+creature.getSize()/2, creature.getY()+creature.getSize()/2));
	}
	
	public static boolean contains(Rectangle2D box, Delimitation delim)
	{
		return box.contains(new Point2D.Double(delim.getX()+delim.getW()/2, delim.getY()+delim.getH()/2));
	}
	
	public static boolean contains(Rectangle2D box, Collectable collec)
	{
		return box.contains(new Point2D.Double(collec.getX()+collec.getSize()/2, collec.getY()+collec.getSize()/2));
	}
	
	public static boolean preciseIntersects(Creature creature1, Collectable collect)
	{
		if (!intersects(creature1, collect)) return false;
		return DistanceChecker.distance(creature1, collect) == 0;
	}
	
	public static boolean preciseIntersects(Creature creature1, Creature creature2)
	{
		if (!intersects(creature1, creature2)) return false;
		return DistanceChecker.distance(creature1, creature2) == 0;
	}
	
	public static boolean preciseIntersects(Creature creature1, Delimitation delim)
	{
		if (!intersects(creature1, delim)) return false;
		return DistanceChecker.distance(creature1, delim) == 0;
	}

}
