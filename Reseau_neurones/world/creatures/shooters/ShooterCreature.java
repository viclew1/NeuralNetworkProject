package creatures.shooters;

import java.awt.Color;
import UI.World;
import captors.Captor;
import creatures.Creature;
import genetics.Individu;
import limitations.throwables.Projectile;
import zones.Zone;

import static utils.Constantes.*;

public abstract class ShooterCreature extends Creature
{

	private double projSpeed,projSize,projDamages;
	protected int cdShoot = 0;
	private Color projColor;

	public ShooterCreature(double x, double y, double size, double hpMax, double speed, double hpLostPerInstant,
			double projSpeed, double projSize, double projDamages, Color projColor,
			Captor[] captors, Individu brain, int type, Color color, int nbInput, World world)
	{
		super(x, y, size, hpMax, speed, hpLostPerInstant, captors,
				new int[] {SOLDIER,TANK,PROJECTILE,FUEL,POWERUP},
				brain, type, color, nbInput, world);
		
		this.projDamages=projDamages;
		this.projSize=projSize;
		this.projSpeed=projSpeed;
		this.projColor=projColor;
	}

	public abstract void targetReport(int targetType);
	
	protected void shoot()
	{
		if (cdShoot<=0)
		{
			delimitations.add(new Projectile(x, y, projSize, projSpeed, orientation, projDamages, this, projColor));
			cdShoot=150;
		}
	}
	
	@Override
	public void interactWith(Zone z)
	{
		
	}
}
