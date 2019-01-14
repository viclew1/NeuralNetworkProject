package creatures.shooters;

import java.awt.Color;

import UI.World;
import captors.Captor;
import creatures.Creature;
import fr.lewon.Individual;
import limitations.throwables.Projectile;
import zones.Zone;

public abstract class ShooterCreature extends Creature
{

	protected double projSpeed,projSize,projDamages,projRange;
	protected int cdShoot = 0;
	protected Color projColor;
	protected int cooldown;

	public ShooterCreature(double x, double y, double radius, double hpMax, double speed, double hpLostPerInstant,
			double projSpeed, double projSize, double projDamages,double projRange, int cooldown, Color projColor,
			Captor[] captors, int[][] thingsToSee, Individual brain, int type, Color color, int nbInput, World world)
	{
		super(x, y, radius, hpMax, speed, 3, hpLostPerInstant, captors,
				thingsToSee,
				brain, type, color, nbInput, world);
		
		this.projDamages=projDamages;
		this.projSize=projSize;
		this.projSpeed=projSpeed;
		this.projColor=projColor;
		this.projRange = projRange;
		this.cooldown=cooldown;
	}

	
	public boolean canShoot()
	{
		return cdShoot<=0;
	}

	public abstract void targetReport(int targetType);
	
	protected void shoot()
	{
		if (canShoot())
		{
			delimitations.add(new Projectile(x, y, projSize, projSpeed, orientation, projDamages, projRange, this, projColor));
			cdShoot=cooldown;
		}
	}
	
	@Override
	public void interactWith(Zone z)
	{
		
	}
	
	@Override
	protected void addParticularInput(double[] inputs, int currentCount)
	{
		inputs[currentCount++] = canShoot()?1:0;
	}
	
	@Override
	protected void addSpecialFitness()
	{
		// TODO Auto-generated method stub
		
	}
}
