package UI;

import UI.worldsIMPL.WorldBeesWasps;
import UI.worldsIMPL.WorldHappyTreeFriends;
import UI.worldsIMPL.WorldOfDodge;

public class Application
{

	public static void main(String[] args)
	{
		World world = new WorldBeesWasps("World of dodge");
		//World world = new WorldOfDodge("World of dodge");
		//World world = new WorldHappyTreeFriends("World of niggers");
		world.start(120, 120, true);
	}
	
}
