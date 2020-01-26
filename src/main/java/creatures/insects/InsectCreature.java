package creatures.insects;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import creatures.Creature;
import fr.lewon.Individual;
import zones.Zone;

public abstract class InsectCreature extends Creature
{

	protected final double shareRadius = 4;
	
	public InsectCreature(double x, double y, double radius, double hpMax, double speed, double rotationSpeed, double hpLostPerInstant,
			Captor[] captors, int[][] thingsToSee, Individual brain, int type, Color color, int nbInput, World world)
	{
		super(x, y, radius, hpMax, speed, rotationSpeed, hpLostPerInstant, captors, thingsToSee,
				brain, type, color, nbInput, world);
	}

	@Override
	protected void applyDecisions(List<Double> decisions)
	{
		turn(2*(0.5-decisions.get(0)));
		moveFront(decisions.get(1));
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
