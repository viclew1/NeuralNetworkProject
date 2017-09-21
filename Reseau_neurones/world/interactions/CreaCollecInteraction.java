package interactions;

import collectables.Collectable;
import creatures.Creature;

public class CreaCollecInteraction extends Interaction
{

	public CreaCollecInteraction(Creature creature, Collectable collect)
	{
		super(creature, collect);
	}

	@Override
	public void process()
	{
		((Creature) o1).interactWith((Collectable) o2);
	}	

}
