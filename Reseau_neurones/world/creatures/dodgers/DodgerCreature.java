package creatures.dodgers;

import static utils.Constantes.*;
import java.awt.Color;
import UI.World;
import captors.Captor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import zones.Zone;

public abstract class DodgerCreature extends Creature
{

	

	public DodgerCreature(double x, double y, double radius, double hpMax, double speed, double hpLostPerInstant,
			Captor[] captors, Individu brain, Selection selec, int type, Color color, int nbInput,
			World world)
	{
		super(x, y, radius, hpMax, speed, 3, hpLostPerInstant, captors, new int[] {
				FIREBALL,
		}, brain, selec, type, color, nbInput, world);
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
	
	@Override
	protected void addParticularInput(double[] inputs, int currentCount)
	{
		
	}
}
