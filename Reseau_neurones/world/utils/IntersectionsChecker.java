package utils;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import captors.Captor;
import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;
import zones.Zone;

public class IntersectionsChecker
{
	
	private static boolean intersects(Rectangle2D rect, Creature creature)
	{
		return rect.contains(creature.getHitBox().getCenterX(),creature.getHitBox().getCenterY());
	}
	
	private static boolean intersects(Zone zone, Creature r)
	{
		return zone.getHitBox().contains(new Point2D.Double(r.getHitBox().getCenterX(), r.getHitBox().getCenterY()));
	}
	
	
	
	
	public static boolean intersects(Creature creature, Collectable collect)
	{
		return intersects(collect.getHitBox(),creature);
	}

	public static boolean intersects(Creature creature, Delimitation delim)
	{
		return intersects(delim.getHitBox(),creature);
	}
	
	public static boolean intersects(Creature creature, Zone zone)
	{
		return intersects(zone,creature);
	}
	
	public static boolean intersects(Creature creature1, Creature creature2)
	{
		return creature1.getHitBox().intersects(creature2.getHitBox().getBounds2D()) && creature2.getHitBox().intersects(creature1.getHitBox().getBounds2D());
	}
	
	
	
	
	public static boolean intersects(Captor captor, Creature creature)
	{
		return intersects(captor,creature.getHitBox());
	}
	
	public static boolean intersects(Captor captor, Collectable collec)
	{
		return intersects(captor,collec.getHitBox());
	}
	
	public static boolean intersects(Captor captor, Delimitation delim)
	{
		return intersects(captor,delim.getHitBox());
	}
	
	public static boolean intersects(Captor captor, Zone zone)
	{
		return intersects(captor,zone.getHitBox());
	}
	
	private static boolean intersects(Captor captor, Shape s)
	{
		return captor.getHitBox().intersects(s.getBounds2D());
	}
	
	
	
	
	public static boolean contains(DelimitationBox box, Creature creature)
	{
		return box.contains(creature.getHitBox().getCenterX(),creature.getHitBox().getCenterY());
	}
	
	public static boolean contains(DelimitationBox box, Delimitation delim)
	{
		return box.contains(delim.getHitBox().getCenterX(),delim.getHitBox().getCenterY());
	}
	
	public static boolean contains(DelimitationBox box, Collectable collec)
	{
		return box.contains(collec.getHitBox().getCenterX(),collec.getHitBox().getCenterY());
	}
	
	public static boolean contains(DelimitationBox box, Zone zone)
	{
		return box.contains(zone.getHitBox().getBounds2D());
	}
}
