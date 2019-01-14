package UI;

import UI.worldsIMPL.WorldOfDodge;


@SuppressWarnings("unused")
public class Application
{

	public static void main(String[] args)
	{
		//World world = new WorldBeesWasps();
		World world = new WorldOfDodge();
//		World world = new WorldHappyTreeFriends();
		//World world = new WorldAdaptative();
		//World world = new WorldOfTanks();
		
		world.start(75, 75, false);

	}

}
