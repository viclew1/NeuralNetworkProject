package collectables.expirables;

import java.awt.Color;
import java.awt.Graphics;

public class Bomb extends ExpirableCollectable {
	
	public Bomb(double x, double y) {
		super(x, y, 1, utils.Constantes.BOMB,250);
	}

	@Override
	public void draw(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(Color.GRAY);
		g.fillOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(Color.BLACK);
		g.drawOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(oldColor);
	}
}
