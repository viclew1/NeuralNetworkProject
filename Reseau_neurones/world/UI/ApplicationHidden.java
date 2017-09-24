package UI;

import UI.worldsIMPL.WorldBeesWasps;

public class ApplicationHidden
{

	public static void main(String[] args)
	{
		World world = new WorldBeesWasps();
		world.start(120, 120);
	}

}
