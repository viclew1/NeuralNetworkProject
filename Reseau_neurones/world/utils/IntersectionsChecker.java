package utils;

import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;
import zones.Zone;

public class IntersectionsChecker
{
	public static boolean intersects(Creature creature, Collectable collect)
	{
		return new Rectangle2D.Double(creature.getX(), creature.getY(), creature.getSize(), creature.getSize())
				.intersects(new Rectangle2D.Double(collect.getX(), collect.getY(), collect.getSize(), collect.getSize()));
	}
	
	public static boolean intersects(Creature creature, Zone zone)
	{
		return zone.getHitBox().contains(creature.getX()+creature.getSize()/2, creature.getY()+creature.getSize()/2);
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
	
	public static boolean intersects(Path2D captorHitbox, Creature creature)
	{
		Rectangle2D creatureRect = new Rectangle2D.Double(creature.getX(), creature.getY(), creature.getSize(), creature.getSize());
		return intersects(captorHitbox,creatureRect);
	}
	
	public static boolean intersects(Path2D captorHitbox, Collectable collec)
	{
		Rectangle2D collecRect = new Rectangle2D.Double(collec.getX(), collec.getY(), collec.getSize(), collec.getSize());
		return intersects(captorHitbox,collecRect);
	}
	
	public static boolean intersects(Path2D captorHitbox, Delimitation delim)
	{
		Rectangle2D delimRect = new Rectangle2D.Double(delim.getX(), delim.getY(), delim.getW(), delim.getH());
		return intersects(captorHitbox,delimRect);
	}
	
	public static boolean intersects(Path2D captorHitbox, Zone zone)
	{
		return intersects(captorHitbox,zone.getHitBox().getBounds2D());
	}
	
	private static boolean intersects(Path2D captorHitbox, Rectangle2D rect)
	{
		return captorHitbox.intersects(rect);
	}
	
	public static boolean intersects(Line2D line1, Line2D line2, DelimitationBox delim)
	{
		Point2D p1 = new Point2D.Double(line1.getX2(),line1.getY2());
		Point2D p2 = new Point2D.Double(line2.getX2(),line2.getY2());
		if (!delim.contains(p1))
			return true;
		if (!delim.contains(p2))
			return true;
		return false;
	}
	
	public static boolean contains(DelimitationBox delim, Creature creature)
	{
		return new Rectangle2D.Double(delim.getX(), delim.getY(), delim.getWidth(), delim.getHeight())
				.contains(new Point2D.Double(creature.getX()+creature.getSize()/2, creature.getY()+creature.getSize()/2));
	}
	
	public static boolean contains(DelimitationBox box, Delimitation delim)
	{
		return new Rectangle2D.Double(box.getX(), box.getY(), box.getWidth(), box.getHeight())
				.contains(new Point2D.Double(delim.getX()+delim.getW()/2, delim.getY()+delim.getH()/2));
	}
	
	public static boolean contains(DelimitationBox box, Collectable collec)
	{
		return new Rectangle2D.Double(box.getX(), box.getY(), box.getWidth(), box.getHeight())
				.contains(new Point2D.Double(collec.getX()+collec.getSize()/2, collec.getY()+collec.getSize()/2));
	}
	
	public static boolean contains(DelimitationBox box, Zone z)
	{
		return new Rectangle2D.Double(box.getX(), box.getY(), box.getWidth(), box.getHeight())
				.contains(z.getHitBox().getBounds2D());
	}

	public static boolean preciseIntersects(Creature creature1, Collectable collect)
	{
		return DistanceChecker.distance(creature1, collect) == 0;
	}
	
	public static boolean preciseIntersects(Creature creature1, Creature creature2)
	{
		return DistanceChecker.distance(creature1, creature2) == 0;
	}
	
	public static boolean preciseIntersects(Creature creature1, Delimitation delim)
	{
		return DistanceChecker.distance(creature1, delim) == 0;
	}
}
