package limitations.throwables;

import static utils.Constantes.*;

import java.awt.Color;

import creatures.shooters.ShooterCreature;

public class Projectile extends ThrowableDelimitation {

	
	public Projectile(double x, double y, double size, double speed, double orientation, double damages, double projRange, ShooterCreature sender, Color color) {
		super(x, y, size, speed, orientation, damages, projRange, true, sender, PROJECTILE, color);

	}

}