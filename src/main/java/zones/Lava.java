package zones;

import java.awt.Color;

import static utils.Constantes.*;

public class Lava extends HealthAlteringZone
{

	public Lava(double[] x, double[] y, int timeToLive)
	{
		super(x, y, timeToLive, LAVA, Color.ORANGE);
		// TODO Auto-generated constructor stub
	}

}
