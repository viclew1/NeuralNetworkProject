package cars;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.Random;

import genetics.Individu;
import genetics.Selection;

public class Car
{
	private final Selection selec;


	protected double x;
	protected double y;
	protected final double w, h;

	protected final int ttl = 1200;
	private int timeLived = 0;
	
	protected double orientation=0.9 * Math.PI;
	protected final double dOrientation = Math.PI/40;
	protected final double orientationMin=0;
	protected final double orientationMax=2*Math.PI;
	protected double speed = 0.6;
	private final double minSpeed = 0;
	private final double maxSpeed = 1.50;
	protected Individu brain;
	protected final Captor[] captors;
	private boolean alive;
	protected Color color;
	private boolean selected=false;

	protected int nbInput;

	protected final Environnement environnement;
	protected final Line2D[] sides;
	protected final Rectangle2D around;
	
	public Car(double x, double y, Individu brain, Selection selec, Environnement environnement)
	{
		this.selec = selec;

		captors = new Captor[] {
				new Captor(this, 0, 10),
				new Captor(this, Math.PI / 6, 10),
				new Captor(this, 2 * Math.PI / 6, 10),
				new Captor(this, 3 * Math.PI / 6, 10),
				new Captor(this, -Math.PI / 6, 10),
				new Captor(this, -2 * Math.PI / 6, 10),
				new Captor(this, -3 * Math.PI / 6, 10),
		};
		
		this.x=x;
		this.y=y;
		this.w = 1;
		this.h = 2;
		this.brain=brain;
		this.alive=true;
		this.color=new Color(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
		this.environnement = environnement;
		sides = new Line2D[4];
		around = new Rectangle2D.Double();
		updateHitbox();
	}

	public void reset(double x, double y, Individu newBrain)
	{
		this.x = x;
		this.y = y;
		this.brain = newBrain;
		revive();
		timeLived = 0;

	}

	public void die()
	{
		this.alive = false;
	}


	public void revive()
	{
		this.alive = true;
	}

	public void draw(Graphics g, boolean selected)
	{
		this.selected=selected;
		if (selected)
			drawScore(g);
		if (selected || DRAW_CAPTORS)
			drawCaptors(g);
		drawCar(g);
	}

	private void drawScore(Graphics g)
	{
		Color oldColor = g.getColor();
		g.setColor(Color.BLACK);
		g.drawString("Score créature : "+new DecimalFormat("##.##").format(brain.getScore()),10, 150);
		g.setColor(oldColor);
	}

	private void drawCar(Graphics g)
	{
		Color oldColor = g.getColor();
		if (!selected)
		{
			g.setColor(color);
		}
		else
		{
			g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),127));
		}


		for (Line2D line : sides)
		{
			g.drawLine(xFinal(line.getX1()), yFinal(line.getY1()), xFinal(line.getX2()), yFinal(line.getY2()));
		}

		g.setColor(Color.BLACK);
		g.setColor(oldColor);
	}

	public void drawBrain(Graphics g)
	{
		brain.draw(g);
	}

	private void drawCaptors(Graphics g)
	{
		for (Captor c : captors)
			c.draw(g);
	}

	public void update()
	{
		brain.resetScore();
		double minD = Double.MAX_VALUE;
		int minIndex = -1;
		for (int i = Map.CLOSEST ; i <= Map.FAREST ; i++)
		{
			Line2D line = environnement.lines[i];
			double xx = line.getX1();
			double yy = line.getY1();
			double dToLine = (x - xx) * (x - xx) + (y - yy) * (y - yy);
			if (dToLine < minD)
			{
				minD = dToLine;
				minIndex = i;
			}
		}
		double dToStart = (x - environnement.xStart) * (x - environnement.xStart) + (y - environnement.yStart) * (y - environnement.yStart);
		brain.addScore(Math.abs(Map.FAREST-minIndex)*100 + dToStart);
		for (Captor c : captors)
			c.update();
		detect();
		accelerate(-0.2);
		updatePosition();
		moveFront();
		updateHitbox();
		
		timeLived++;
		if (timeLived > ttl) die();
	}

	private void updateHitbox()
	{
		double x2 = x+Math.cos(orientation)*h;
		double y2 = y-Math.sin(orientation)*h;

		double x3 = x-Math.cos(orientation+Math.PI/2)*w;
		double y3 = y+Math.sin(orientation+Math.PI/2)*w;

		double x4 = x3+Math.cos(orientation)*h;
		double y4 = y3-Math.sin(orientation)*h;

		sides[0] = new Line2D.Double(x, y, x2, y2);
		sides[1] = new Line2D.Double(x2, y2, x4, y4);
		sides[2] = new Line2D.Double(x4, y4, x3, y3);
		sides[3] = new Line2D.Double(x3, y3, x, y);
		
		
		double xMin = Math.min(Math.min(Math.min(x4, x3), x2), x);
		double xMax = Math.max(Math.max(Math.max(x4, x3), x2), x);
		double yMin = Math.min(Math.min(Math.min(y4, y3), y2), y);
		double yMax = Math.max(Math.max(Math.max(y4, y3), y2), y);
		
		around.setFrame(xMin, yMin, xMax - xMin, yMax - yMin );
	}

	public void detect()
	{
		for (Captor c : captors)
			c.detect(environnement);
	}

	private void updatePosition()
	{
		double[] inputs = new double[8];
		int cpt=0;
		for (int i=0;i<captors.length;i++)
		{
			double results = captors[i].getResults();
			inputs[cpt++] = results;
		}
		inputs[cpt++] = speed / maxSpeed;
		double[] decisions = brain.getOutputs(inputs);
		applyDecisions(decisions);
	}

	protected void applyDecisions(double[] decisions)
	{
		accelerate(1 - 2*decisions[0]);
		turn(1 - 2 * decisions[1]);
	}

	protected void accelerate(double power)
	{
		speed += power * 5 / 20;
		speed = Math.min(speed, maxSpeed);
		speed = Math.max(speed, minSpeed);
	}

	protected void moveFront()
	{
		x+=Math.cos(orientation)*speed;
		y-=Math.sin(orientation)*speed;
	}

	protected void turn(double intensity)
	{
		orientation+=dOrientation*intensity;
		if (orientation>orientationMax)
			orientation-=Math.PI * 2;
		if (orientation<orientationMin)
			orientation+=Math.PI * 2;
	}

	/**
	 *  CALCULS DE LA POSITION SUR L'ECRAN
	 */

	protected int xFinal(double xx)
	{
		return (int)(xx*SIZE+SCROLL_X);
	}

	protected int yFinal(double yy)
	{
		return (int)(yy*SIZE+SCROLL_Y);
	}

	protected int sizeFinal(double sz)
	{
		return (int) (sz*SIZE);
	}

	/**
	 *  GETTERS
	 */

	public boolean isAlive()
	{
		return alive;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getW()
	{
		return w;
	}

	public double getH()
	{
		return h;
	}

	public Individu getBrain()
	{
		return brain;
	}

	public Selection getSelection()
	{
		return selec;
	}
}
