package creatures.dodgers;

import static utils.Constantes.*;
import java.awt.Color;
import java.util.List;

import captors.Captor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import limitations.Delimitation;
import limitations.DelimitationBox;
import zones.Zone;

public abstract class DodgerCreature extends Creature
{

	

	public DodgerCreature(double x, double y, double radius, double hpMax, double speed, double hpLostPerInstant,
			Captor[] captors, Individu brain, int type, Color color, int nbInput,
			List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations,
			DelimitationBox box)
	{
		super(x, y, radius, hpMax, speed, 3, hpLostPerInstant, captors, new int[] {
				FIREBALL,
				WALL
		}, brain, type, color, nbInput, creatures,
				collectables, delimitations, box);
	}
	
	@Override
	public void interactWith(Collectable c)
	{
		
	}

	@Override
	public void interactWith(Creature c)
	{
		
	}

	@Override
	public void interactWith(Zone z)
	{
		
	}
}
