package utils;

import collectables.Collectable;
import creatures.Creature;

public class DistanceChecker
{

	private static double distancePtoP(double x1, double y1, double x2, double y2, double d1, double d2)
	{
		double xx = x2 - x1;
		double yy = y2 - y1;
		double squaredLenght = Math.pow(xx, 2) + Math.pow(yy, 2);
		return Math.max(0,Math.sqrt(squaredLenght)-d1-d2);
	}
	
	public static double distance(Creature crea1, Creature crea2)
	{
		double x1 = crea1.getX()+crea1.getSize()/2;
		double y1 = crea1.getY()+crea1.getSize()/2;
		
		double x2 = crea2.getX()+crea2.getSize()/2;
		double y2 = crea2.getY()+crea2.getSize()/2;
		return distancePtoP(x1, y1, x2, y2, crea1.getSize()/2, crea2.getSize()/2);
	}
	
	public static double distance(Creature crea, Collectable collec)
	{
		double x1 = crea.getX()+crea.getSize()/2;
		double y1 = crea.getY()+crea.getSize()/2;
		
		double x2 = collec.getX()+collec.getSize()/2;
		double y2 = collec.getY()+collec.getSize()/2;
		return distancePtoP(x1, y1, x2, y2, crea.getSize()/2, collec.getSize()/2);
	}
	
}
