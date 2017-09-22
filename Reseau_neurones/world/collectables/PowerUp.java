package collectables;

import java.awt.Color;
import java.awt.Graphics;

public class PowerUp extends ExpirableCollectable {
	
	public PowerUp(double x, double y) {
		super(x, y, 0.5, utils.Constantes.POWERUP, 3000);
	}

	@Override
	public void draw(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(Color.BLACK);
		g.drawOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(oldColor);
	}
	
}
