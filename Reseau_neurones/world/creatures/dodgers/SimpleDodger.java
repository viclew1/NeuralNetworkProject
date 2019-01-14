package creatures.dodgers;

import static utils.Constantes.LAYERS_SIZES_SIMPLEDODGER;
import static utils.Constantes.SIMPLEDODGER;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import creatures.Creature;
import fr.lewon.Individual;

public class SimpleDodger extends DodgerCreature
{

	public SimpleDodger(double x, double y, Individual brain, World world)
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
		}, brain, SIMPLEDODGER, Color.GREEN, LAYERS_SIZES_SIMPLEDODGER[0], world);
	}
	
	private void move(double intensityX, double intensityY)
	{
		x+=speed*intensityX;
		y+=speed*intensityY;
	}

	@Override
	protected void applyDecisions(List<Double> decisions)
	{
		double intensityX = 2*(0.5-decisions.get(0));
		double intensityY = 2*(0.5-decisions.get(1));
		move(intensityX, intensityY);
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
		case SIMPLEDODGER:
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
