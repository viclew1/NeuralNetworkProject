package cars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import utils.Constantes;

public class Captor
{
	private double pseudoOrientation, orientation, range;
	private double currentRange;
	private Car car;

	public Captor(Car car, double orientation, double range)
	{
		this.car = car;
		this.pseudoOrientation = orientation;
		this.range = range;
	}

	public double getResults()
	{
		return 1 - currentRange / range;
	}

	public void update()
	{
		orientation = car.orientation + pseudoOrientation;
	}

	public void detect(Environnement environnement)
	{
		double carCenterX = car.getX() + Math.cos(car.orientation)*car.getH()/2 - Math.cos(car.orientation+Math.PI/2)*car.getW()/2;
		double carCenterY = car.getY() - Math.sin(car.orientation)*car.getH()/2 + Math.sin(car.orientation+Math.PI/2)*car.getW()/2;
		Line2D[] lines = environnement.lines;
		Rectangle2D around = new Rectangle2D.Double();
		for (currentRange = 0.01 ; currentRange < range ; currentRange += 0.4)
		{
			double x = carCenterX + Math.cos(orientation)*currentRange;
			double y = carCenterY - Math.sin(orientation)*currentRange;


			double xMin = Math.min(carCenterX, x);
			double xMax = Math.max(carCenterX, x);
			double yMin = Math.min(carCenterY, y);
			double yMax = Math.max(carCenterY, y);
			around.setFrame(xMin, yMin, xMax - xMin, yMax - yMin );

			Line2D lineCaptor = new Line2D.Double(carCenterX, carCenterY, x, y);
			for (int j = 0 ; j < lines.length ; j++)
			{
				Line2D l = lines[j];
				if (around.intersectsLine(l))
					if (l.intersectsLine(lineCaptor))
						return;
			}
		}
	}

	public void draw(Graphics g)
	{
		Color oldColor = g.getColor();
		g.setColor(Color.BLACK);
		double carCenterX = Constantes.SCROLL_X + Constantes.SIZE * (car.getX() + Math.cos(car.orientation)*car.getH()/2 - Math.cos(car.orientation+Math.PI/2)*car.getW()/2);
		double carCenterY = Constantes.SCROLL_Y + Constantes.SIZE * (car.getY() - Math.sin(car.orientation)*car.getH()/2 + Math.sin(car.orientation+Math.PI/2)*car.getW()/2);

		double x = carCenterX + Constantes.SIZE * ( Math.cos(orientation)*currentRange);
		double y = carCenterY - Constantes.SIZE * ( Math.sin(orientation)*currentRange);

		g.drawLine((int)carCenterX, (int)carCenterY, (int)x, (int)y);
		g.setColor(oldColor);
	}



}
