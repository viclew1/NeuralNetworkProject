package creatures.shooters;

import static utils.Constantes.*;

import java.awt.Color;
import java.util.List;

import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import limitations.Delimitation;
import limitations.DelimitationBox;

public class Soldier extends ShooterCreature
{

	public Soldier(double x, double y, Individu brain, List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		super(x, y, 1.5, 500, 0.40,0.05,
				0.4,1,60,Color.RED,
				new Captor[] {
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI,5,Math.PI/2),
		}, brain, SOLDIER, Color.CYAN, LAYERS_SIZES_SOLDIER[0],
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
			brain.addScore(-1);
			break;
		case TANK:
			brain.addScore(1);
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