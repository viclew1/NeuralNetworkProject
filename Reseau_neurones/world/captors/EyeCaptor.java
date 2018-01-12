package captors;

import static utils.Constantes.SCROLL_X;
import static utils.Constantes.SCROLL_Y;
import static utils.Constantes.SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;
import utils.DistanceChecker;
import utils.IntersectionsChecker;
import zones.Zone;

public class EyeCaptor extends Captor
{

	private final double orientation,widthAngle;
	protected double[] xPoints,yPoints;
	protected final Path2D hitbox;

	public EyeCaptor(double orientation, double range, double widthAngle)
	{
		super(range);
		this.xPoints = new double[3];
		this.yPoints = new double[3];
		this.orientation=orientation;
		this.widthAngle=widthAngle;
		this.hitbox = new Path2D.Double();
		this.around = new Rectangle2D.Double(xPoints[0]-range,yPoints[0]-range,range*2,range*2);
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

	@Override
	protected boolean checkIntersection(Creature c)
	{
		return IntersectionsChecker.intersects(hitbox,c);
	}

	@Override
	protected boolean checkIntersection(Collectable c)
	{
		return IntersectionsChecker.intersects(hitbox,c);
	}

	@Override
	protected boolean checkIntersection(Delimitation d)
	{
		return IntersectionsChecker.intersects(hitbox,d);
	}

	@Override
	protected boolean checkIntersection(Zone z)
	{
		return IntersectionsChecker.intersects(hitbox,z);
	}

	@Override
	public void draw(Graphics g)
	{
		Color color = g.getColor();
		g.setColor(Color.BLACK);
		int[] xFinal = new int[xPoints.length];
		int[] yFinal = new int[yPoints.length];
		for (int i=0 ; i<xPoints.length ; i++)
		{
			xFinal[i] = (int) (xPoints[i]*SIZE+SCROLL_X);
			yFinal[i] = (int) (yPoints[i]*SIZE+SCROLL_Y);
		}
		g.drawPolygon(xFinal, yFinal, xPoints.length);
		g.setColor(Color.RED);
		g.drawRect((int)(around.getX()*SIZE+SCROLL_X), (int)(around.getY()*SIZE+SCROLL_Y), (int)(around.getWidth()*SIZE), (int)(around.getHeight()*SIZE));
		g.setColor(color);
	}

	@Override
	protected void updateHitbox()
	{
		hitbox.reset();
		hitbox.moveTo(xPoints[0], yPoints[0]);
		for(int i = 1; i < xPoints.length; ++i) {
			hitbox.lineTo(xPoints[i], yPoints[i]);
		}
		hitbox.closePath();
		around.setFrame(xPoints[0]-range,yPoints[0]-range,range*2,range*2);
	}

	@Override
	protected void detectWall(DelimitationBox box)
	{
		wallResult = 0;
		for (int i = 0 ; i < xPoints.length ; i++)
			if (!box.contains(xPoints[i],yPoints[i]))
			{
				wallResult = 1;
				return;
			}
	}

}
