package creatures.chargers;

import static utils.Constantes.FUEL;
import static utils.Constantes.POWERUP;
import static utils.Constantes.PROJECTILE;
import static utils.Constantes.SOLDIER;
import static utils.Constantes.TANK;

import java.awt.Color;
import java.util.List;

import captors.Captor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import limitations.Delimitation;
import limitations.DelimitationBox;
import zones.Zone;

public class ChargerCreature extends Creature {
	
	protected Boolean invincible = false;
	protected int invicibilityTimeLeft = 30;
	protected int invicibilityCooldown = 800;

	public ChargerCreature(double x, double y, double radius, double hpMax, double speed,
			double hpLostPerInstant, Captor[] captors, int[] thingsToSee, Individu brain, Selection selec, int type,
			Color color, int nbInput, List<Creature> creatures, List<Collectable> collectables,
			List<Delimitation> delimitations, DelimitationBox box) {
		super(x, y, radius, hpMax, speed, 3, hpLostPerInstant, captors, thingsToSee, brain, selec, type, color,
				nbInput, creatures, collectables, delimitations, box);
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

}
