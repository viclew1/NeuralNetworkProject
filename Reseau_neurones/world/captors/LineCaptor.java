package captors;

import static utils.Constantes.*;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;


public class LineCaptor extends Captor
{

	private Line2D line;
	protected final double orientation;
	
	public LineCaptor(double orientation, double range)
	{
		super(range);
		this.orientation=orientation;
	}

	@Override
	public void update(double x, double y, double sz, double deltaOrientation)
	{
		double xCenter = x + sz/2;
		double yCenter = y + sz/2;
		Point2D p1 = new Point2D.Double(xCenter, yCenter);
		double xFront = xCenter + Math.cos(orientation+deltaOrientation)*range;
		double yFront = yCenter - Math.sin(orientation+deltaOrientation)*range;
		Point2D p2 = new Point2D.Double(xFront, yFront);
		line = new Line2D.Double(p1, p2);
	}

	@Override
	protected void detectCreatures(List<Creature> creatures)
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void detectCollectables(List<Collectable> collectables)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void detectDelimitations(List<Delimitation> delimitations, DelimitationBox box)
	{
		// TODO Auto-generated method stub
		
	}



}
