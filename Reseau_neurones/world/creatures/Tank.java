package creatures;

import java.awt.Color;

import collectables.Collectable;
import creatures.captors.Captor;
import creatures.captors.EyeCaptor;
import genetics.Individu;
import limitations.Delimitation;

import static utils.Constantes.*;

public class Tank extends Creature
{

	public Tank(double x, double y, Individu brain)
	{
		super(x, y, 3, 500, 0.15, new Captor[] {
				new EyeCaptor(Math.PI/7,20,Math.PI/3),
				new EyeCaptor(-Math.PI/7,20,Math.PI/3),
		}, brain, TANK, Color.BLUE);
		// TODO Auto-generated constructor stub
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
	protected void updatePosition()
	{
		double[] inputs = new double[INPUT_COUNT_TANK];
		int cpt=0;
		for (int i=0;i<captors.length;i++)
		{
			double[] results = captors[i].getResults();
			for (int j=0;j<results.length;j++)
			{
				inputs[cpt] = results[j];
				cpt++;
			}
		}
		inputs[cpt]=hp/hpMax;
		double[] decisions = brain.getOutputs(inputs);
		turn(2*(0.5-decisions[0]));
		forward(1);
	}
}
