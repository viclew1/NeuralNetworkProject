package captors;

import static utils.Constantes.*;

import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;
import utils.DistanceChecker;
import utils.IntersectionsChecker;
import zones.Zone;

public class EyeCaptor extends Captor
{

	private final double orientation,widthAngle;

	public EyeCaptor(double orientation, double range, double widthAngle)
	{
		super(range);
		this.orientation=orientation;
		this.widthAngle=widthAngle;
	}

	@Override
	public void update(double x, double y, double sz, double deltaOrientation)
	{
		double x1 = x + sz/2;
		double y1 = y + sz/2;
		double x2 = x1 + Math.cos(orientation+widthAngle/2+deltaOrientation)*range;
		double y2 = y1 - Math.sin(orientation+widthAngle/2+deltaOrientation)*range;
		double x3 = x1 + Math.cos(orientation-widthAngle/2+deltaOrientation)*range;
		double y3 = y1 - Math.sin(orientation-widthAngle/2+deltaOrientation)*range;
		this.x = new double[] {x1,x2,x3};
		this.y = new double[] {y1,y2,y3};
		
		hitbox.reset();
		hitbox.moveTo(this.x[0], this.y[0]);
		for(int i = 1; i < this.x.length; ++i)
			hitbox.lineTo(this.x[i], this.y[i]);
		hitbox.closePath();
		
		around = hitbox.getBounds2D();
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
			if (c==null || !around.contains(c.getHitBox().getBounds2D()))
				continue;
			switch (c.getType())
			{
			case BEE:
				if (IntersectionsChecker.intersects(this,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultBees)
						resultBees=dist;
				break;
			case WASP:
				if (IntersectionsChecker.intersects(this,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultWasps)
						resultWasps=dist;
				break;
			case SOLDIER:
				if (IntersectionsChecker.intersects(this,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultSoldier)
						resultSoldier=dist;
				break;
			case TANK:
				if (IntersectionsChecker.intersects(this,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultTank)
						resultTank=dist;
				break;
			case COMPLEXDODGER:
				if (IntersectionsChecker.intersects(this,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultComplexDodger)
						resultComplexDodger=dist;
				break;
			case SIMPLEDODGER:
				if (IntersectionsChecker.intersects(this,c))
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
	protected void detectZones(List<Zone> zones)
	{
		// TODO Auto-generated method stub
		
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
			if (c==null || !around.contains(c.getHitBox().getBounds2D()))
				continue;
			switch (c.getType())
			{
			case VEGETABLE:
				if (IntersectionsChecker.intersects(this,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultVegetable)
						resultVegetable=dist;
				break;
			case MEAT:
				if (IntersectionsChecker.intersects(this,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultMeat)
						resultMeat=dist;
			case FUEL:
				if (IntersectionsChecker.intersects(this,c))
					if ((dist = DistanceChecker.distance(creature, c))<resultFuel)
						resultFuel=dist;
				break;
			case POWERUP:
				if (IntersectionsChecker.intersects(this,c))
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
			if (d==null || !around.contains(d.getHitBox()))
				continue;
			switch (d.getType())
			{
			case PROJECTILE:
				if (IntersectionsChecker.intersects(this,d))
					if ((dist = DistanceChecker.distance(creature, d))<resultProjectile)
						resultProjectile=dist;
				break;
			case FIREBALL:
				if (IntersectionsChecker.intersects(this,d))
					if ((dist = DistanceChecker.distance(creature, d))<resultFireBall)
						resultFireBall=dist;
			case WALL:
				if (IntersectionsChecker.intersects(this,d))
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
