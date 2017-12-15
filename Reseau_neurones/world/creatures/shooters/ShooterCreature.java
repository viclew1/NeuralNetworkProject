package creatures.shooters;

import java.awt.Color;
import java.util.List;

import captors.Captor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
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

	public ShooterCreature(double x, double y, double radius, double hpMax, double speed, double hpLostPerInstant,
			double projSpeed, double projSize, double projDamages, Color projColor,
			Captor[] captors, Individu brain, Selection selec, int type, Color color, int nbInput, List<Creature> creatures,
			List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		super(x, y, radius, hpMax, speed, 3, hpLostPerInstant, captors,
				new int[] {SOLDIER,TANK,PROJECTILE,FUEL,POWERUP,WALL},
				brain, selec, type, color, nbInput, creatures, collectables,
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
	
	@Override
	public void interactWith(Zone z)
	{
		
	}
}
