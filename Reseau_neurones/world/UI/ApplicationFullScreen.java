package UI;

import java.awt.Dimension;

import javax.swing.JFrame;

import UI.worldsIMPL.WorldBeesWasps;
import UI.worldsIMPL.WorldOfDodge;
import UI.worldsIMPL.WorldOfTanks;

public class ApplicationFullScreen
{

	public static void main(String[] args)
	{
		JFrame jf = new JFrame();
		World world = new WorldOfDodge();
		jf.setContentPane(world);
		jf.setPreferredSize(new Dimension(1920,1080));
		jf.pack();
		jf.setVisible(true);
		//jf.setUndecorated(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		world.start(120, 120);
	}
	
}
