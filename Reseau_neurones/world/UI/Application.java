package UI;

import UI.worldsIMPL.WorldOfDodge;

public class Application
{

	public static void main(String[] args)
	{
		World world = new WorldOfDodge("World of dodge");
		world.start(120, 120, true);
	}
	
}
