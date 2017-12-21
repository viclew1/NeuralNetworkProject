package creatures.slugs;

import java.awt.Color;
import java.util.List;

import captors.Captor;
import collectables.Collectable;
import collectables.expirables.Bomb;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import limitations.Delimitation;
import limitations.DelimitationBox;
import limitations.throwables.Projectile;
import zones.Zone;

public abstract class SlugsCreature extends Creature {
	
	private int bombCooldown;

	public SlugsCreature(double x, double y, double radius, double hpMax, double speed, double rotationSpeed,
			double hpLostPerInstant, Captor[] captors, int[] thingsToSee, Individu brain, Selection selec, int type, Color color,
			int nbInput, List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations,
			DelimitationBox box) {
		
		super(x, y, radius, hpMax, speed, rotationSpeed, hpLostPerInstant, captors, thingsToSee, brain, selec, type, color, nbInput,
				creatures, collectables, delimitations, box);
		
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
			collectables.add(new Bomb(x,y));
		}
	}
	
	@Override
	protected void applyDecisions(double[] decisions) {
		turn(2*(0.5-decisions[0]));
		moveFront(decisions[1]);
	}

}
