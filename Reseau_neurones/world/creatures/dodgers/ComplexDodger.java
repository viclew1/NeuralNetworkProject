package creatures.dodgers;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import genetics.Individu;
import genetics.Selection;
import static utils.Constantes.*;

public class ComplexDodger extends DodgerCreature
{

	public ComplexDodger(double x, double y, Individu brain, Selection selec, World world)
	{
		super(x, y, 1, 1, 1, 0, new Captor[]{
				new EyeCaptor(Math.PI/2,15,Math.PI/3.6),
				new EyeCaptor(-Math.PI/2,15,Math.PI/3.6),
				new EyeCaptor(-Math.PI,15,Math.PI/3.6),
				new EyeCaptor(0,15,Math.PI/3.6),
				new EyeCaptor(Math.PI/4,15,Math.PI/3.6),
				new EyeCaptor(-Math.PI/4,15,Math.PI/3.6),
				new EyeCaptor(-Math.PI*3/4,15,Math.PI/3.6),
				new EyeCaptor(Math.PI*3/4,15,Math.PI/3.6),
		}, brain, selec, COMPLEXDODGER, Color.GREEN, LAYERS_SIZES_COMPLEXDODGER[0], world);
	}
	
	@Override
	protected void applyDecisions(double[] decisions)
	{
		turn(2*(0.5-decisions[0]));
		moveFront(2*(0.5-decisions[1]));
	}

	@Override
	protected void applySeenFitness(List<Integer> seenThings)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateScore()
	{
		brain.addScore(0.01);
	}

}
