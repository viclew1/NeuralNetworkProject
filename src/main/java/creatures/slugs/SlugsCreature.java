package creatures.slugs;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import creatures.Creature;
import fr.lewon.Individual;

public abstract class SlugsCreature extends Creature {
	
	public int bombCooldown;

	public SlugsCreature(double x, double y, double radius, double hpMax, double speed, double rotationSpeed,
			double hpLostPerInstant, Captor[] captors, int[][] thingsToSee, Individual brain, int type, Color color,
			int nbInput, World world) {
		
		super(x, y, radius, hpMax, speed, rotationSpeed, hpLostPerInstant, captors, thingsToSee, brain, type, color, nbInput,
				world);
		
		this.bombCooldown = 20;
		
	}
	
	@Override
	protected void moveFront(double intensity)
	{
		x+=Math.cos(orientation)*speed*intensity;
		y-=Math.sin(orientation)*speed*intensity;
		
		bombCooldown--;
		if(bombCooldown<=0) {
			bombCooldown = 20;
			//collectables.add(new Bomb(x,y));
		}
	}
	
	@Override
	protected void applyDecisions(List<Double> decisions) {
		turn(2*(0.5-decisions.get(0)));
		moveFront(decisions.get(1));
	}
	
	@Override
	protected void addParticularInput(double[] inputs, int currentCount)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void addSpecialFitness()
	{
		// TODO Auto-generated method stub
		
	}

}
