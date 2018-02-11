package creatures.insects;

import java.awt.Color;

import UI.World;
import captors.Captor;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import zones.Zone;

public abstract class InsectCreature extends Creature
{

	protected final double shareRadius = 4;
	
	public InsectCreature(double x, double y, double radius, double hpMax, double speed, double rotationSpeed, double hpLostPerInstant,
			Captor[] captors, int[][] thingsToSee, Individu brain, Selection selec, int type, Color color, int nbInput, World world)
	{
		super(x, y, radius, hpMax, speed, rotationSpeed, hpLostPerInstant, captors, thingsToSee,
				brain, selec, type, color, nbInput, world);
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
	
	@Override
	protected void addParticularInput(double[] inputs, int currentCount)
	{
		
	}
}
