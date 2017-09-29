package interactions;

import creatures.Creature;
import zones.Zone;

public class CreaZoneInteraction extends Interaction
{

	public CreaZoneInteraction(Creature c, Zone z)
	{
		super(c, z);
	}

	@Override
	public void process()
	{
		((Creature)o1).interactWith((Zone)o2);
	}

}
