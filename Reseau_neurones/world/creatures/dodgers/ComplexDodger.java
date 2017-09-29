package creatures.dodgers;

import java.awt.Color;
import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import genetics.Individu;
import static utils.Constantes.*;

public class ComplexDodger extends DodgerCreature
{

	public ComplexDodger(double x, double y, Individu brain, World world)
	{
		super(x, y, 1, 1, 1, 0, new Captor[]{
				new EyeCaptor(Math.PI/7,10,Math.PI/3),
				new EyeCaptor(-Math.PI/7,10,Math.PI/3),
				new EyeCaptor(-Math.PI,6,Math.PI/4),
				new EyeCaptor(Math.PI/2,7,Math.PI/2),
				new EyeCaptor(-Math.PI/2,7,Math.PI/2),
		}, brain, COMPLEXDODGER, Color.GREEN, LAYERS_SIZES_COMPLEXDODGER[0], world);
	}

	@Override
	protected void applyDecisions(double[] decisions)
	{
		turn(2*(0.5-decisions[0]));
		moveFront(2*(0.5-decisions[1]));
	}

}
