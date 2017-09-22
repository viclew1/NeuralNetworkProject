package creatures;

import java.awt.Color;
import java.util.List;

import collectables.Collectable;
import creatures.captors.Captor;
import creatures.captors.EyeCaptor;
import genetics.Individu;
import limitations.Delimitation;
import limitations.DelimitationBox;

import static utils.Constantes.*;

public class Tank extends ShooterCreature
{

	public Tank(double x, double y, Individu brain, List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		super(x, y, 3, 500, 0.15,0.05,
				0.2,1.2,150,Color.BLACK,
				new Captor[] {
				new EyeCaptor(Math.PI/7,10,Math.PI/3),
				new EyeCaptor(-Math.PI/7,10,Math.PI/3),
				new EyeCaptor(-Math.PI,5,Math.PI/2),
		}, brain, TANK, Color.BLUE,
				INPUT_COUNT_TANK,
				creatures,collectables,delimitations, box);
	}

	@Override
	public void interactWith(Collectable c)
	{
		// TODO Auto-generated method stub
		
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
			brain.addScore(1);
			break;
		case TANK:
			brain.addScore(5);
			break;
		default:
			break;
		}
	}
}
