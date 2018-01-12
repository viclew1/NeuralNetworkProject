package collectables.expirables;

import java.awt.Color;
import java.awt.Graphics;

import creatures.slugs.Slug;

public class Bomb extends ExpirableCollectable {
	
	Slug owner;
	
	public Bomb(double x, double y, Slug o) {
		super(x, y, 1, utils.Constantes.BOMB,250);
		this.owner=o;
	}

	@Override
	public void draw(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(Color.BLACK);
		g.drawOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(oldColor);
	}
	
	public void consumedByKill()
	{
		owner.addScore(1000);
		consumed=true;
	}
}
