package creatures;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import collectables.Collectable;
import creatures.captors.Captor;
import creatures.captors.EyeCaptor;
import genetics.Individu;
import limitations.Delimitation;

public abstract class Creature
{

	protected double x;
	protected final double hpMax;
	protected double hp;
	protected double y;
	protected final double size;
	protected double orientation=0;
	protected final double dOrientation = 3*Math.PI/40;
	protected final double orientationMin=0;
	protected final double orientationMax=2*Math.PI;
	protected final double speed;
	protected Individu brain;
	protected Captor[] captors;
	protected boolean alive;
	private final int type;
	private final Color color;
	private boolean selected=false;

	public Creature(double x, double y, double size, double hpMax, double speed, Individu brain, int type, Color color)
	{
		this.x=x;
		this.y=y;
		this.size=size;
		this.hpMax=hpMax;
		this.hp=hpMax;
		this.speed=speed;
		this.captors=new Captor[]{
				new EyeCaptor(Math.PI/7,10,Math.PI/3),
				new EyeCaptor(-Math.PI/7,10,Math.PI/3),
				new EyeCaptor(-Math.PI,6,Math.PI/4),
		};
		this.brain=brain;
		this.type=type;
		this.alive=true;
		this.color=color;
		initCaptors();
	}

	public void draw(Graphics g, boolean selected)
	{
		this.selected=selected;
		if (selected || DRAW_HP)
			drawHP(g);
		if (selected || DRAW_CAPTORS)
			drawCaptors(g);
		drawCreature(g);
	}

	private void drawHP(Graphics g)
	{
		Color oldColor = g.getColor();
		int xF = xFinal();
		int yF = (int)((y-size/2)*SIZE+SCROLL_Y);
		int szF = sizeFinal();
		int hpSzF = (int)(szF*hp/hpMax);
		int hF = (int)((size/5)*SIZE);

		g.setColor(Color.GREEN);
		g.fillRect(xF, yF, hpSzF, hF);
		g.setColor(Color.BLACK);
		g.drawRect(xF, yF, szF, hF);
		g.setColor(oldColor);

	}

	private void drawCreature(Graphics g)
	{
		Color oldColor = g.getColor();
		if (!selected)
			g.setColor(color);
		else
			g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),127));
		g.fillOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(Color.BLACK);
		g.drawOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
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
		updatePosition();
		updateCaptors();
	}

	protected void initCaptors()
	{
		for (Captor c : captors)
		{
			c.setCreature(this);
			c.update(x, y, size, orientation);
		}
	}

	public void updateCaptors()
	{
		for (Captor c : captors)
			c.update(x, y, size, orientation);
	}

	public void detect(List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations)
	{
		for (Captor c : captors)
			c.detect(creatures, collectables, delimitations);
	}

	protected void updatePosition()
	{
		hp--;
		if (hp<=0)
			alive=false;
		double[] inputs = new double[INPUT_COUNT];
		int cpt=0;
		for (int i=0;i<captors.length;i++)
		{
			double[] results = captors[i].getResults();
			for (int j=0;j<results.length;j++)
			{
				inputs[cpt] = results[j];
				cpt++;
			}
		}
		inputs[cpt]=hp/hpMax;
		double[] decisions = brain.getOutputs(inputs);
		turn(2*(0.5-decisions[0]));
		forward(1);
	}


	protected void forward(double intensity)
	{
		x+=Math.cos(orientation)*speed*intensity;
		y-=Math.sin(orientation)*speed*intensity;
	}

	protected void turn(double intensity)
	{
		orientation+=dOrientation*intensity;
		if (orientation>orientationMax)
			orientation-=orientationMin;
		if (orientation<orientationMin)
			orientation+=orientationMax;
	}


	public abstract void interactWith(Collectable c);
	public abstract void interactWith(Creature c);
	public abstract void interactWith(Delimitation d);


	// CALCULS DE LA POSITION SUR L'ECRAN

	protected int xFinal()
	{
		return (int)(x*SIZE+SCROLL_X);
	}

	protected int yFinal()
	{
		return (int)(y*SIZE+SCROLL_Y);
	}

	protected int sizeFinal()
	{
		return (int) (size*SIZE);
	}

	// GETTERS

	public int getType()
	{
		return type;
	}

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

	public double getSize()
	{
		return size;
	}

	public Individu getBrain()
	{
		return brain;
	}

}
