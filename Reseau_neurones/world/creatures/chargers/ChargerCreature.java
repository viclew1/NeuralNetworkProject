package creatures.chargers;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import zones.Zone;

public class ChargerCreature extends Creature {
	
	protected Boolean invincible = false;
	protected int invicibilityTimeLeft = 30;
	protected int invicibilityCooldown = 800;

	public ChargerCreature(double x, double y, double radius, double hpMax, double speed,
			double hpLostPerInstant, Captor[] captors, int[] thingsToSee, Individu brain, Selection selec, int type,
			Color color, int nbInput, World world) {
		super(x, y, radius, hpMax, speed, 3, hpLostPerInstant, captors, thingsToSee, brain, selec, type, color,
				nbInput, world);
	}

	public void charge() {
		if (invicibilityCooldown<=0){
			this.invincible = true;
			this.speed = 1.3;
		}
	}
	
	@Override
	protected void updateScore() {
		
	}

	@Override
	protected void applySeenFitness(List<Integer> seenThings) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactWith(Collectable c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactWith(Creature c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactWith(Zone z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void applyDecisions(double[] decisions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addParticularInput(double[] inputs, int currentCount)
	{
		// TODO Auto-generated method stub
		
	}

}
