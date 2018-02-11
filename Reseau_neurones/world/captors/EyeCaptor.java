package captors;

import static utils.Constantes.SCROLL_X;
import static utils.Constantes.SCROLL_Y;
import static utils.Constantes.SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import limitations.DelimitationBox;

public class EyeCaptor extends Captor
{

	private final double widthAngle;
	protected double[] xPoints,yPoints;

	public EyeCaptor(double orientation, double range, double widthAngle)
	{
		super(orientation, range);
		this.xPoints = new double[3];
		this.yPoints = new double[3];
		this.widthAngle=widthAngle;
		this.hitbox = new Path2D.Double();
		this.around = new Rectangle2D.Double();
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
		
		((Path2D) hitbox).reset();
		((Path2D) hitbox).moveTo(xPoints[0], yPoints[0]);
		for(int i = 1; i < xPoints.length; ++i) {
			((Path2D) hitbox).lineTo(xPoints[i], yPoints[i]);
		}
		((Path2D) hitbox).closePath();
		
		double minX = Math.min(Math.min(xPoints[0],xPoints[1]),xPoints[2]);
		double minY = Math.min(Math.min(yPoints[0],yPoints[1]),yPoints[2]);
		double maxX = Math.max(Math.max(xPoints[0],xPoints[1]),xPoints[2]);
		double maxY = Math.max(Math.max(yPoints[0],yPoints[1]),yPoints[2]);
		around.setFrame(minX,minY,maxX - minX,maxY - minY);
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
