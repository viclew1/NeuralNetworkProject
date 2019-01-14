package creatures.shooters;

import static utils.Constantes.FUEL;
import static utils.Constantes.LAYERS_SIZES_TANK;
import static utils.Constantes.POWERUP;
import static utils.Constantes.PROJECTILE;
import static utils.Constantes.SOLDIER;
import static utils.Constantes.TANK;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import captors.LineCaptor;
import collectables.Collectable;
import creatures.Creature;
import fr.lewon.Individual;

public class Tank extends ShooterCreature
{

	public Tank(double x, double y, Individual brain, World world)
	{
		super(x, y, 1.2, 400, 0.15,1,
				1.3,1,500, Integer.MAX_VALUE, 150, Color.BLACK,
				new Captor[] {
						new LineCaptor(0, 30),
						new EyeCaptor(Math.PI/6,25,Math.PI/3),
						new EyeCaptor(-Math.PI/6,25,Math.PI/3),
						new EyeCaptor(-Math.PI,10,Math.PI/2),
		},
				new int[][] {
					{
						TANK,
					},
					{
					},
					{
						PROJECTILE,
					}},
				brain, TANK, Color.BLUE,	LAYERS_SIZES_TANK[0],
				world);
	}

	@Override
	public void interactWith(Collectable c)
	{
		if (c.isConsumed()) {
			return;
		}
		switch (c.getType())
		{
		case FUEL:
			hp+=50;
			if (hp>hpMax) {
				hp=hpMax;
			}
			c.consume();
			break;
		case POWERUP:

			break;
		default:
			break;
		}
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

	@Override
	protected void applyDecisions(List<Double> decisions)
	{
		cdShoot--;
		turn(2*(0.5-decisions.get(0)));
		straff(2*(0.5-decisions.get(1)));
		moveFront(2*(0.5-decisions.get(2)));
		if (decisions.get(3)>0.7)
			shoot();
	}

	@Override
	public void targetReport(int targetType)
	{
		switch (targetType)
		{
		case SOLDIER:
			break;
		case TANK:
			heal(hpMax);
			break;
		default:
			break;
		}
	}
}
