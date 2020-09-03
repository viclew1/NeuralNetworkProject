package zones;

import java.awt.Color;

public abstract class HealthAlteringZone extends Zone
{
	
	public HealthAlteringZone(double[] x, double[] y, int timeToLive, int type, Color color)
	{
		super(x, y, timeToLive, type, color);
	}
}
