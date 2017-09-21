package creatures;

import java.awt.Color;
import java.util.List;

import collectables.Collectable;
import creatures.captors.Captor;
import creatures.captors.EyeCaptor;
import genetics.Individu;
import limitations.Delimitation;

import static utils.Constantes.*;

public class Tank extends Creature
{

	public Tank(double x, double y, Individu brain, List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations)
	{
		super(x, y, 3, 500, 0.15,0, new Captor[] {
				new EyeCaptor(Math.PI/7,20,Math.PI/3),
				new EyeCaptor(-Math.PI/7,20,Math.PI/3),
		}, brain, TANK, Color.BLUE,
				INPUT_COUNT_TANK,
				creatures,collectables,delimitations);
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

	}
}
