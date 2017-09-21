package interactions;

import creatures.Creature;

public class CreaCreaInteraction extends Interaction
{

	public CreaCreaInteraction(Creature c1, Creature c2)
	{
		super(c1, c2);
	}

	@Override
	public void process()
	{
		((Creature)o1).interactWith((Creature)o2);
		((Creature)o2).interactWith((Creature)o1);
	}

}
