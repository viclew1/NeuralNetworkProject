package zones;

import java.awt.Color;

public abstract class SpeedChangingZone extends Zone
{

	public SpeedChangingZone(double[] x, double[] y, int timeToLive, int type, Color color)
	{
		super(x, y, timeToLive, type, color);
	}
	
}
