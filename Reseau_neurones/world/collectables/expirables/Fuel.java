package collectables.expirables;

import java.awt.Color;
import java.awt.Graphics;

public class Fuel extends ExpirableCollectable {
	
	public Fuel(double x, double y) {
		super(x, y, 1, utils.Constantes.FUEL,10000);
	}

	@Override
	public void draw(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(Color.BLACK);
		g.drawRect(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(oldColor);
	}
}
