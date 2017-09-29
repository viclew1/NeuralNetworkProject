package creatures.dodgers;

import static utils.Constantes.FIREBALL;
import java.awt.Color;
import UI.World;
import captors.Captor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import zones.Zone;

public abstract class DodgerCreature extends Creature
{

	

	public DodgerCreature(double x, double y, double size, double hpMax, double speed, double hpLostPerInstant,
			Captor[] captors, Individu brain, int type, Color color, int nbInput,
			World world)
	{
		super(x, y, size, hpMax, speed, hpLostPerInstant, captors, new int[] {
				FIREBALL
		}, brain, type, color, nbInput, world);
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
