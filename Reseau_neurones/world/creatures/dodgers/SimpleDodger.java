package creatures.dodgers;

import static utils.Constantes.*;

import java.awt.Color;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import creatures.captors.Captor;
import creatures.captors.EyeCaptor;
import genetics.Individu;
import limitations.Delimitation;
import limitations.DelimitationBox;

public class SimpleDodger extends DodgerCreature
{

	public SimpleDodger(double x, double y, Individu brain, List<Creature> creatures,
			List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		super(x, y, 1, 1, 0.7, 0, new Captor[]{
				new EyeCaptor(Math.PI/2,8,Math.PI/1.8),
				new EyeCaptor(-Math.PI/2,8,Math.PI/1.8),
				new EyeCaptor(-Math.PI,8,Math.PI/1.8),
				new EyeCaptor(0,8,Math.PI/1.8),
		}, brain, SIMPLEDODGER, Color.GREEN, INPUT_COUNT_SIMPLEDODGER, creatures,
				collectables, delimitations, box);
	}
	
	private void move(double intensityX, double intensityY)
	{
		x+=speed*intensityX;
		y+=speed*intensityY;
	}

	@Override
	protected void applyDecisions(double[] decisions)
	{
		double intensityX = 2*(0.5-decisions[0]);
		double intensityY = 2*(0.5-decisions[1]);
		move(intensityX, intensityY);
	}

}
