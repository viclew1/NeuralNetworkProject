package creatures.dodgers;

import static utils.Constantes.COMPLEXDODGER;
import static utils.Constantes.LAYERS_SIZES_COMPLEXDODGER;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import creatures.Creature;
import fr.lewon.Individual;

public class ComplexDodger extends DodgerCreature
{

	public ComplexDodger(double x, double y, Individual brain, World world)
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
		}, brain, COMPLEXDODGER, Color.GREEN, LAYERS_SIZES_COMPLEXDODGER[0], world);
	}
	
	@Override
	protected void applyDecisions(List<Double> decisions)
	{
		turn(2*(0.5-decisions.get(0)));
		moveFront(0.3 + 0.7 * decisions.get(1));
	}
	
	@Override
	public void touchedBy(Creature c)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touch(Creature c)
	{
		switch (c.getType())
		{
		case COMPLEXDODGER:
			reproduceWith(c);
			break;
		default:
			break;
		}
	}

	@Override
	protected void addSpecialFitness()
	{
		
	}

}
