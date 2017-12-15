package creatures.shooters;

import java.awt.Color;
import java.util.List;

import captors.Captor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import limitations.Delimitation;
import limitations.DelimitationBox;
import limitations.throwables.Projectile;
import zones.Zone;

import static utils.Constantes.*;

public abstract class ShooterCreature extends Creature
{

	private double projSpeed,projSize,projDamages;
	protected int cdShoot = 0;
	private Color projColor;
	private int cooldown;

	public ShooterCreature(double x, double y, double radius, double hpMax, double speed, double hpLostPerInstant,
			double projSpeed, double projSize, double projDamages, int cooldown, Color projColor,
			Captor[] captors, Individu brain, int type, Color color, int nbInput, List<Creature> creatures,
			List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		super(x, y, radius, hpMax, speed, 3, hpLostPerInstant, captors,
				new int[] {SOLDIER,TANK,PROJECTILE,FUEL,POWERUP,WALL},
				brain, type, color, nbInput, creatures, collectables,
				delimitations, box);
		
		this.projDamages=projDamages;
		this.projSize=projSize;
		this.projSpeed=projSpeed;
		this.projColor=projColor;
		this.cooldown=cooldown;
	}


	public abstract void targetReport(int targetType);
	
	protected void shoot()
	{
		if (cdShoot<=0)
		{
			delimitations.add(new Projectile(x, y, projSize, projSpeed, orientation, projDamages, this, projColor));
			cdShoot=cooldown;
		}
	}
	
	@Override
	public void interactWith(Zone z)
	{
		
	}
}
