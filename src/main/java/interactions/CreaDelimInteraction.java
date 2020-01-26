package interactions;

import creatures.Creature;
import limitations.Delimitation;

public class CreaDelimInteraction extends Interaction
{

	public CreaDelimInteraction(Creature creature, Delimitation delim)
	{
		super(creature, delim);
	}

	@Override
	public void process()
	{
		((Creature)o1).interactWith((Delimitation)o2);
		((Delimitation)o2).interactWith((Creature)o1);
	}

}
