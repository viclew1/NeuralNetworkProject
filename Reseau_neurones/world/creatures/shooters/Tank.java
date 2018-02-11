package creatures.shooters;

import java.awt.Color;
import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import captors.LineCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;

import static utils.Constantes.*;

public class Tank extends ShooterCreature
{

	public Tank(double x, double y, Individu brain, Selection selec, World world)
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
				brain, selec, TANK, Color.BLUE,	LAYERS_SIZES_TANK[0],
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
			brain.addScore(1);
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
	protected void applyDecisions(double[] decisions)
	{
		cdShoot--;
		turn(2*(0.5-decisions[0]));
		straff(2*(0.5-decisions[1]));
		moveFront(2*(0.5-decisions[2]));
		if (decisions[3]>0.7)
			shoot();
	}

	@Override
	public void targetReport(int targetType)
	{
		switch (targetType)
		{
		case SOLDIER:
			brain.addScore(5);
			break;
		case TANK:
			brain.addScore(2);
			heal(hpMax);
			break;
		default:
			break;
		}
	}
}
