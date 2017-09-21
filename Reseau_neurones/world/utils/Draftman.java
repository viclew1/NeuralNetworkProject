package utils;

import java.awt.Graphics;

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

}
