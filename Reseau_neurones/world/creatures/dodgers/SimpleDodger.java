package creatures.dodgers;

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

public class SimpleDodger extends DodgerCreature
{

	public SimpleDodger(double x, double y, Individu brain, List<Creature> creatures,
			List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		super(x, y, 1, 1, 1, 0, new Captor[]{
				new EyeCaptor(Math.PI/2,8,Math.PI/3.6),
				new EyeCaptor(-Math.PI/2,8,Math.PI/3.6),
				new EyeCaptor(-Math.PI,8,Math.PI/3.6),
				new EyeCaptor(0,8,Math.PI/3.6),
				new EyeCaptor(Math.PI/4,8,Math.PI/3.6),
				new EyeCaptor(-Math.PI/4,8,Math.PI/3.6),
				new EyeCaptor(-Math.PI*3/4,8,Math.PI/3.6),
				new EyeCaptor(Math.PI*3/4,8,Math.PI/3.6),
		}, brain, SIMPLEDODGER, Color.GREEN, LAYERS_SIZES_SIMPLEDODGER[0], creatures,
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
