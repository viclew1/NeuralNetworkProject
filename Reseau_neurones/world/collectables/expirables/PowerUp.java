package collectables.expirables;

import java.awt.Color;

public class PowerUp extends ExpirableCollectable {
	
	public PowerUp(double x, double y) {
		super(x, y, 0.5, utils.Constantes.POWERUP, 3000, Color.ORANGE);
	}

}
