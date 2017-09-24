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

public class ComplexDodger extends DodgerCreature
{

	public ComplexDodger(double x, double y, Individu brain, List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations,
			DelimitationBox box)
	{
		super(x, y, 1, 1, 0.7, 0, new Captor[]{
				new EyeCaptor(Math.PI/7,10,Math.PI/3),
				new EyeCaptor(-Math.PI/7,10,Math.PI/3),
				new EyeCaptor(-Math.PI,6,Math.PI/4),
				new EyeCaptor(Math.PI/2,7,Math.PI/2),
				new EyeCaptor(-Math.PI/2,7,Math.PI/2),
		}, brain, COMPLEXDODGER, Color.GREEN, INPUT_COUNT_COMPLEXDODGER, creatures,
				collectables, delimitations, box);
	}
	
	@Override
	protected void applyDecisions(double[] decisions)
	{
		turn(2*(0.5-decisions[0]));
		moveFront(2*(0.5-decisions[1]));
	}

}
