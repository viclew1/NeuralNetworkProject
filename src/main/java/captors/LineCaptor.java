package captors;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import limitations.DelimitationBox;

public class LineCaptor extends Captor
{


	public LineCaptor(double orientation, double range)
	{
		super(orientation, range);
		this.hitbox = new Line2D.Double();
		this.around = new Rectangle2D.Double();
	}

	@Override
	public void update(double x, double y, double deltaOrientation)
	{
		double x1 = x;
		double y1 = y;
		double x2 = x + Math.cos(orientation+deltaOrientation)*range;
		double y2 = y - Math.sin(orientation+deltaOrientation)*range;
		((Line2D)hitbox).setLine(x1, y1, x2, y2);
		around.setFrame(Math.min(x1,x2),Math.min(y1, y2),Math.max(x1,x2) - Math.min(x1,x2),Math.max(y1, y2) - Math.min(y1, y2));
	}

	@Override
	public void draw(Graphics g)
	{
		Color color = g.getColor();
		g.setColor(Color.BLACK);
		int x1 = (int) (((Line2D)hitbox).getX1() * SIZE + SCROLL_X);
		int y1 = (int) (((Line2D)hitbox).getY1() * SIZE + SCROLL_Y);
		int x2 = (int) (((Line2D)hitbox).getX2() * SIZE + SCROLL_X);
		int y2 = (int) (((Line2D)hitbox).getY2() * SIZE + SCROLL_Y);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(Color.RED);
		g.drawRect((int)(around.getX()*SIZE+SCROLL_X), (int)(around.getY()*SIZE+SCROLL_Y), (int)(around.getWidth()*SIZE), (int)(around.getHeight()*SIZE));
		g.setColor(color);
	}

	@Override
	protected void detectWall(DelimitationBox box)
	{

		wallResult = 0;
		if (!box.contains(((Line2D)hitbox).getX2(),((Line2D)hitbox).getY2()))
			wallResult = 1;
	}

}
