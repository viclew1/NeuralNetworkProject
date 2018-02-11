package utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;

public class Draftman
{

	public void drawCreature(Creature c, boolean selected, Graphics g)
	{
		if (c!=null && c.isAlive())
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
	
	public void drawBox(DelimitationBox box, Graphics g)
	{
		if (box!=null)
			box.draw(g);
	}
	
	public void drawWorld(Creature selectedCreature, List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box, Graphics g)
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
			
			drawBox(box, g);
			
			if (selectedCreature!=null && selectedCreature.isAlive())
				selectedCreature.drawBrain(g);
		}
		catch(Exception e) {}
	}

	public void drawInfos(List<String> list, Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		for (int i=0;i<list.size();i++)
			g2.drawString(list.get(i), 10, 40+15*i);
	}
	
}
