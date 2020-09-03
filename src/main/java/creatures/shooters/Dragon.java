package creatures.shooters;

import static utils.Constantes.BOMB;
import static utils.Constantes.DRAGON;
import static utils.Constantes.HEDGEHOG;
import static utils.Constantes.LAYERS_SIZES_DRAGON;
import static utils.Constantes.PROJECTILE;
import static utils.Constantes.RHINOCEROS;
import static utils.Constantes.SLUG;
import static utils.Constantes.VEGETABLE;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import fr.lewon.Individual;
import limitations.throwables.Projectile;

public class Dragon extends ShooterCreature
{

	private final int projCount = 5;
	private final double angle = Math.PI/5;

	public Dragon(double x, double y, Individual brain, World world)
	{
		super(x, y, 6, 2000, 0.2 ,0.05,
				1,4,150, 40, 400, Color.RED,
				new Captor[] {
						new EyeCaptor(0,45,0.001),
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI,6,Math.PI/4),
		},
				new int[][] {
					{
						HEDGEHOG,
						SLUG,
						RHINOCEROS,
						DRAGON,
					},
					{
						BOMB,
						VEGETABLE,
					},
					{
						PROJECTILE,
					}},
				brain, DRAGON, Color.RED, LAYERS_SIZES_DRAGON[0],
				world);
	}

	protected void shoot()
	{
		if (cdShoot<=0)
		{
			for (int i = 0 ; i < projCount ; i++)
			{
				double dAngle = i * angle / projCount;
				delimitations.add(new Projectile(x, y, projSize, projSpeed, orientation - angle + dAngle, projDamages, projRange, this, projColor));
			}
			cdShoot=cooldown;
		}
	}

	@Override
	public void targetReport(int targetType)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void applyDecisions(List<Double> decisions)
	{
		cdShoot--;
		turn(2*(0.5-decisions.get(0)));
		moveFront(decisions.get(1));
		straff(2*(0.5-decisions.get(2)));
		if (decisions.get(3)>0.5)
			shoot();
	}

	@Override
	public void interactWith(Collectable c)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void touchedBy(Creature c)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touch(Creature c)
	{
		// TODO Auto-generated method stub
		
	}

}
