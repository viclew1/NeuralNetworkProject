package captors;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;
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
		around = new Rectangle2D.Double(line1.getX1()-range,line1.getY1()-range,range*2,range*2);
	}

	@Override
	public void draw(Graphics g)
	{
		Color color = g.getColor();
		g.setColor(Color.BLACK);
		g.drawLine((int)(line1.getX1()*SIZE+SCROLL_X),
				(int)(line1.getY1()*SIZE+SCROLL_Y), 
				(int)(line1.getX2()*SIZE+SCROLL_X), 
				(int)(line1.getY2()*SIZE+SCROLL_Y));
		g.drawLine((int)(line2.getX1()*SIZE+SCROLL_X),
				(int)(line2.getY1()*SIZE+SCROLL_Y), 
				(int)(line2.getX2()*SIZE+SCROLL_X), 
				(int)(line2.getY2()*SIZE+SCROLL_Y));
		g.setColor(Color.RED);
		g.drawRect((int)(around.getX()*SIZE+SCROLL_X), (int)(around.getY()*SIZE+SCROLL_Y), (int)(around.getWidth()*SIZE), (int)(around.getHeight()*SIZE));
		g.setColor(color);
	}

	@Override
	protected void detectCreatures(List<Creature> creatures)
	{
		resultBees = Double.MAX_VALUE;
		resultWasps = Double.MAX_VALUE;
		resultSoldier = Double.MAX_VALUE;
		resultTank = Double.MAX_VALUE;
		resultComplexDodger = Double.MAX_VALUE;
		resultSimpleDodger = Double.MAX_VALUE;
		double dist;
		for (int i=0; i<creatures.size();i++)
		{
			Creature c = creatures.get(i);
			if (c==null || !around.contains(c.getX()+c.getSize()/2,c.getSize()+c.getSize()/2))
				continue;
			switch (c.getType())
			{
			case BEE:
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultBees)
						resultBees=dist;
				break;
			case WASP:
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultWasps)
						resultWasps=dist;
				break;
			case SOLDIER:
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultSoldier)
						resultSoldier=dist;
				break;
			case TANK:
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultTank)
						resultTank=dist;
				break;
			case COMPLEXDODGER:
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultComplexDodger)
						resultComplexDodger=dist;
				break;
			case SIMPLEDODGER:
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultSimpleDodger)
						resultSimpleDodger=dist;
				break;
			default:
				System.out.println("EyeCaptor.detectCreatures - Creature inconnue");
				System.exit(0);
				break;
			}
		}
		resultBees/=range;
		if (resultBees > 1) resultBees = 0;
		else resultBees = 1 - resultBees;
		resultWasps/=range;
		if (resultWasps > 1) resultWasps = 0;
		else resultWasps = 1 - resultWasps;
		resultSoldier/=range;
		if (resultSoldier > 1) resultSoldier = 0;
		else resultSoldier = 1 - resultSoldier;
		resultTank/=range;
		if (resultTank > 1) resultTank = 0;
		else resultTank = 1 - resultTank;
		resultComplexDodger/=range;
		if (resultComplexDodger > 1) resultComplexDodger = 0;
		else resultComplexDodger = 1 - resultComplexDodger;
		resultSimpleDodger/=range;
		if (resultSimpleDodger > 1) resultSimpleDodger = 0;
		else resultSimpleDodger = 1 - resultSimpleDodger;
	}

	@Override
	protected void detectCollectables(List<Collectable> collectables)
	{
		resultVegetable = Double.MAX_VALUE;
		resultMeat = Double.MAX_VALUE;
		resultFuel = Double.MAX_VALUE;
		resultPowerUp = Double.MAX_VALUE;
		double dist;
		for (int i=0;i<collectables.size();i++)
		{
			Collectable c = collectables.get(i);
			if (c==null || !around.contains(c.getX()+c.getSize()/2,c.getSize()+c.getSize()/2))
				continue;
			switch (c.getType())
			{
			case VEGETABLE:
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultVegetable)
						resultVegetable=dist;
				break;
			case MEAT:
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultMeat)
						resultMeat=dist;
			case FUEL:
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultFuel)
						resultFuel=dist;
				break;
			case POWERUP:
				if (IntersectionsChecker.intersects(line1,line2,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultPowerUp)
						resultPowerUp=dist;
				break;
			default:
				System.out.println("EyeCaptor.detectCollectables - Collectable inconnu");
				System.exit(0);
				break;
			}
		}
		resultVegetable/=range;
		if (resultVegetable > 1) resultVegetable = 0;
		else resultVegetable = 1 - resultVegetable;
		resultMeat/=range;
		if (resultMeat > 1) resultMeat = 0;
		else resultMeat = 1 - resultMeat;
		resultFuel/=range;
		if (resultFuel > 1) resultFuel = 0;
		else resultFuel = 1 - resultFuel;
		resultPowerUp/=range;
		if (resultPowerUp > 1) resultPowerUp = 0;
		else resultPowerUp = 1 - resultPowerUp;
	}

	@Override
	protected void detectDelimitations(List<Delimitation> delimitations, DelimitationBox box)
	{
		resultWall = Double.MAX_VALUE;
		resultProjectile = Double.MAX_VALUE;
		resultFireBall = Double.MAX_VALUE;
		double dist;
		for (int i=0;i<delimitations.size();i++)
		{
			Delimitation d = delimitations.get(i);
			if (d==null || !around.contains(d.getX()+d.getW()/2,d.getY()+d.getH()/2))
				continue;
			switch (d.getType())
			{
			case PROJECTILE:
				if (IntersectionsChecker.intersects(line1,line2,d))
					if ((dist = DistanceChecker.distance(creature, d))<resultProjectile)
						resultProjectile=dist;
				break;
			case FIREBALL:
				if (IntersectionsChecker.intersects(line1,line2,d))
					if ((dist = DistanceChecker.distance(creature, d))<resultFireBall)
						resultFireBall=dist;
			case WALL:
				if (IntersectionsChecker.intersects(line1, line2, box))
					if (IntersectionsChecker.intersects(line1,line2,d))
						if ((dist = DistanceChecker.distance(creature, d))<resultWall)
							resultWall=dist;
				break;
			default:
				System.out.println("EyeCaptor.detectDelimitations - Delimitation inconnue");
				System.exit(0);
				break;
			}
		}

		resultProjectile/=range;
		if (resultProjectile > 1) resultProjectile = 0;
		else resultProjectile = 1 - resultProjectile;
		resultFireBall/=range;
		if (resultFireBall > 1) resultFireBall = 0;
		else resultFireBall = 1 - resultFireBall;
		resultWall/=range;
		if (resultWall > 1) resultWall = 0;
		else resultWall = 1 - resultWall;
	}

}
