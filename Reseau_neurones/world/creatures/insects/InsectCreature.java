package creatures.insects;

import java.awt.Color;
import UI.World;
import captors.Captor;
import creatures.Creature;
import genetics.Individu;
import zones.Zone;

import static utils.Constantes.*;

public abstract class InsectCreature extends Creature
{

	public InsectCreature(double x, double y, double size, double hpMax, double speed, double hpLostPerInstant,
			Captor[] captors, Individu brain, int type, Color color, int nbInput, World world)
	{
		super(x, y, size, hpMax, speed, hpLostPerInstant, captors,
				new int[] {WASP,BEE,VEGETABLE,MEAT},
				brain, type, color, nbInput, world);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void applyDecisions(double[] decisions)
	{
		turn(2*(0.5-decisions[0]));
		moveFront(1);
	}
	
	@Override
	public void interactWith(Zone z)
	{
		
	}
}
