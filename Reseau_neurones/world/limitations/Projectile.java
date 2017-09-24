package limitations;

import static utils.Constantes.*;

import java.awt.Color;
import creatures.ShooterCreature;

public class Projectile extends ThrowableDelimitation {

	
	public Projectile(double x, double y, double size, double speed, double orientation, double damages, ShooterCreature sender, Color color) {
		super(x, y, size, speed, orientation, damages, true, sender, PROJECTILE, color);

	}

}