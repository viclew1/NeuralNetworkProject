package creatures.shooters;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;

import static utils.Constantes.*;

public class Tank extends ShooterCreature
{

	public Tank(double x, double y, Individu brain, Selection selec, World world)
	{
		super(x, y, 3, 500, 0.15,0.05,
				0.2,1.2,150, Integer.MAX_VALUE, 150, Color.BLACK,
				new Captor[] {
						new EyeCaptor(Math.PI/7,16,Math.PI/3),
						new EyeCaptor(-Math.PI/7,16,Math.PI/3),
						new EyeCaptor(-Math.PI,4,Math.PI/2),
		},
				new int[] {SOLDIER,TANK,PROJECTILE,FUEL,POWERUP},
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
	public void interactWith(Creature c)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void applyDecisions(double[] decisions)
	{
		cdShoot--;
		turn(2*(0.5-decisions[0]));
		moveFront(2*(0.5-decisions[1]));
		straff(2*(0.5-decisions[2]));
		if (decisions[3]>0.5)
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
			brain.addScore(5);
			break;
		default:
			break;
		}
	}

	@Override
	protected void applySeenFitness(List<Integer> seenThings)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateScore()
	{
		// TODO Auto-generated method stub

	}
}
