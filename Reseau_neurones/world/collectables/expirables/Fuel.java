package collectables.expirables;

import java.awt.Color;

public class Fuel extends ExpirableCollectable {
	
	public Fuel(double x, double y) {
		super(x, y, 1, utils.Constantes.FUEL,10000,Color.GRAY);
	}

}
