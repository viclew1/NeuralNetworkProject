package creatures.dodgers;

import static utils.Constantes.COMPLEXDODGER;
import static utils.Constantes.FIREBALL;
import static utils.Constantes.INPUT_COUNT_COMPLEXDODGER;

import java.awt.Color;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import creatures.captors.Captor;
import creatures.captors.EyeCaptor;
import genetics.Individu;
import limitations.Delimitation;
import limitations.DelimitationBox;

public abstract class DodgerCreature extends Creature
{

	

	public DodgerCreature(double x, double y, double size, double hpMax, double speed, double hpLostPerInstant,
			Captor[] captors, Individu brain, int type, Color color, int nbInput,
			List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations,
			DelimitationBox box)
	{
		super(x, y, size, hpMax, speed, hpLostPerInstant, captors, new int[] {
				FIREBALL
		}, brain, type, color, nbInput, creatures,
				collectables, delimitations, box);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interactWith(Collectable c)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactWith(Creature c)
	{
		// TODO Auto-generated method stub
		
	}

}
