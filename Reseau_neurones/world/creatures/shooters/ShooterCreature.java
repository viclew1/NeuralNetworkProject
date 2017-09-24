package creatures.shooters;

import java.awt.Color;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import creatures.captors.Captor;
import genetics.Individu;
import limitations.Delimitation;
import limitations.DelimitationBox;
import limitations.Projectile;

import static utils.Constantes.*;

public abstract class ShooterCreature extends Creature
{

	private double projSpeed,projSize,projDamages;
	protected int cdShoot = 0;
	private Color projColor;

	public ShooterCreature(double x, double y, double size, double hpMax, double speed, double hpLostPerInstant,
			double projSpeed, double projSize, double projDamages, Color projColor,
			Captor[] captors, Individu brain, int type, Color color, int nbInput, List<Creature> creatures,
			List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		super(x, y, size, hpMax, speed, hpLostPerInstant, captors,
				new int[] {SOLDIER,TANK,PROJECTILE,FUEL,POWERUP},
				brain, type, color, nbInput, creatures, collectables,
				delimitations, box);
		
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
}
