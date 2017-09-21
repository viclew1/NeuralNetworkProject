package utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;

public class Draftman
{

	public void drawCreature(Creature c, boolean selected, Graphics g)
	{
		if (c!=null)
			c.draw(g,selected);
	}

	public void drawCollectable(Collectable c, Graphics g)
	{
		if (c!=null)
			c.draw(g);
	}

	public void drawDelimitation(Delimitation d, Graphics g)
	{
		if (d!=null)
			d.draw(g);
	}
	
	public void drawWorld(Creature selectedCreature, List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, Graphics g)
	{
		try
		{
			for (int i=0;i<collectables.size();i++)
				drawCollectable(collectables.get(i), g);

			for (int i=0;i<creatures.size();i++)
			{
				Creature c = creatures.get(i);
				boolean selected = (selectedCreature==c);
				drawCreature(c,selected, g);
			}

			for (int i=0;i<delimitations.size();i++)
				drawDelimitation(delimitations.get(i), g);

			if (selectedCreature!=null && selectedCreature.isAlive())
				selectedCreature.drawBrain(g);
		}
		catch(Exception e) {}
	}

	public void drawInfos(List<String> list, Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		for (int i=0;i<list.size();i++)
			g2.drawString(list.get(i), 10, 15+15*i);
	}
	
	public void drawFPS(int currentFrameRate, Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.drawString("FPS : "+ currentFrameRate, 10, 400);
	}
}
