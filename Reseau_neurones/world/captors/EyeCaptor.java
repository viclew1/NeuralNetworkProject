package captors;

public class EyeCaptor extends Captor
{

	private final double orientation,widthAngle;

	public EyeCaptor(double orientation, double range, double widthAngle)
	{
		super(range,3);
		this.orientation=orientation;
		this.widthAngle=widthAngle;
	}

	@Override
	public void update(double x, double y, double deltaOrientation)
	{
		xPoints[0] = x;
		yPoints[0] = y;
		xPoints[1] = x + Math.cos(orientation+widthAngle/2+deltaOrientation)*range;
		yPoints[1] = y - Math.sin(orientation+widthAngle/2+deltaOrientation)*range;
		xPoints[2] = x + Math.cos(orientation-widthAngle/2+deltaOrientation)*range;
		yPoints[2] = y - Math.sin(orientation-widthAngle/2+deltaOrientation)*range;
	}
}
