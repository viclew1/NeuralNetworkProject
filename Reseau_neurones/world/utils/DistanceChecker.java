package utils;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;

public class DistanceChecker
{
	

	private static double distancePtoP(double x1, double y1, double x2, double y2, double d1, double d2)
	{
		double xx = x2 - x1;
		double yy = y2 - y1;
		double squaredLenght = Math.pow(xx, 2) + Math.pow(yy, 2);
		return Math.max(0,Math.sqrt(squaredLenght)-d1/2-d2/2);
	}

	public static double distance(Creature crea, Delimitation d)
	{
		return distancePtoP(crea.getHitBox().getCenterX(),crea.getHitBox().getCenterY(),d.getHitBox().getCenterX(),d.getHitBox().getCenterY(),crea.getHitBox().getWidth(),d.getHitBox().getWidth());
	}

	public static double distance(Creature crea1, Creature crea2)
	{
		return distancePtoP(crea1.getHitBox().getCenterX(),crea1.getHitBox().getCenterY(),crea2.getHitBox().getCenterX(),crea2.getHitBox().getCenterY(),crea1.getHitBox().getWidth(),crea2.getHitBox().getWidth());
	}

	public static double distance(Creature crea, Collectable collec)
	{
		return distancePtoP(crea.getHitBox().getCenterX(),crea.getHitBox().getCenterY(),collec.getHitBox().getCenterX(),collec.getHitBox().getCenterY(),crea.getHitBox().getWidth(),collec.getHitBox().getWidth());
	}

}
