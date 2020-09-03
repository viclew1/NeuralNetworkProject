package zones;

import java.awt.Color;

import static utils.Constantes.*;

public class Water extends SpeedChangingZone
{

	public Water(double[] x, double[] y, int timeToLive)
	{
		super(x, y, timeToLive, WATER, Color.BLUE);
	}

}
