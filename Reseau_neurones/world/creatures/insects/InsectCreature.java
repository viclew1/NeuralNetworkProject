package creatures.insects;

import java.awt.Color;
import java.util.List;

import captors.Captor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import limitations.Delimitation;
import limitations.DelimitationBox;
import zones.Zone;

import static utils.Constantes.*;

public abstract class InsectCreature extends Creature
{

	public InsectCreature(double x, double y, double size, double hpMax, double speed, double rotationSpeed, double hpLostPerInstant,
			Captor[] captors, Individu brain, int type, Color color, int nbInput, List<Creature> creatures,
			List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		super(x, y, size, hpMax, speed, rotationSpeed, hpLostPerInstant, captors,
				new int[] {WASP,BEE,VEGETABLE,WALL},
				brain, type, color, nbInput, creatures, collectables,
				delimitations, box);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void applyDecisions(double[] decisions)
	{
		turn(2*(0.5-decisions[0]));
		moveFront(decisions[1]);
	}
	
	@Override
	public void interactWith(Zone z)
	{
		
	}
}
