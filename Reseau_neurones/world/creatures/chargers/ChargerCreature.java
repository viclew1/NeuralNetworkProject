package creatures.chargers;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import collectables.Collectable;
import creatures.Creature;
import fr.lewon.Individual;
import zones.Zone;

public abstract class ChargerCreature extends Creature {
	
	static Color pourpre = new Color(65, 11, 16);
	protected Boolean isCharging = false;
	protected int chargeTimeLeft = 50;
	protected int chargeCooldown = (int) (450*Math.random());

	public ChargerCreature(double x, double y, double radius, double hpMax, double speed,
			double hpLostPerInstant, Captor[] captors, int[][] thingsToSee, Individual brain, int type,
			Color color, int nbInput, World world) {
		super(x, y, radius, hpMax, speed, 3, hpLostPerInstant, captors, thingsToSee, brain, type, color,
				nbInput, world);
	}

	public void charge() {
		if (chargeCooldown<=0){
			this.isCharging = true;
			this.speed = 1.3;
			this.color = pourpre;
		}
	}

	@Override
	public void interactWith(Collectable c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactWith(Zone z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void applyDecisions(List<Double> decisions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addParticularInput(double[] inputs, int currentCount)
	{
		// TODO Auto-generated method stub
		
	}

	

}
