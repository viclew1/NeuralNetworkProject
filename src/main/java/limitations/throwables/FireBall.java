package limitations.throwables;

import java.awt.Color;
import static utils.Constantes.*;

public class FireBall extends ThrowableDelimitation
{

	public FireBall(double x, double y, double orientation)
	{
		super(x, y, 5, 0.6, orientation, Double.MAX_VALUE, Integer.MAX_VALUE, false, null, FIREBALL, Color.RED);
	}

}
