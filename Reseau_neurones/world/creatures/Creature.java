package creatures;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.List;

import captors.Captor;
import collectables.Collectable;
import genetics.Individu;
import genetics.Selection;
import limitations.Delimitation;
import limitations.DelimitationBox;
import limitations.throwables.Projectile;
import zones.Zone;

public abstract class Creature
{
	private final double xInit, yInit;
	private final Selection selec;


	private boolean invincible = true;
	private int invincibleTime = 60;
	private int invincibleTimeLeft = 60;

	protected double x;
	protected final double hpMax;
	protected double hp;
	protected double y;
	protected final double radius;
	protected double orientation=0;
	protected final double dOrientation;
	protected final double orientationMin=0;
	protected final double orientationMax=2*Math.PI;
	protected final double speed;
	protected Individu brain;
	protected Captor[] captors;
	protected boolean alive;
	private final int type;
	private final Color color;
	private boolean selected=false;

	protected int nbInput;
	protected double hpLostPerInstant;

	protected List<Creature> creatures;
	protected List<Collectable> collectables;
	protected List<Delimitation> delimitations;
	protected DelimitationBox box;

	public Creature(double x, double y, double radius, double hpMax, double speed, double rotationSpeed, double hpLostPerInstant, Captor[] captors,
			int[] thingsToSee, Individu brain, Selection selec, int type, Color color, int nbInput, List<Creature> creatures, 
			List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{

		this.xInit = x;
		this.yInit = y;
		this.selec = selec;

		this.x=x;
		this.y=y;
		this.radius=radius;
		this.hpMax=hpMax;
		this.hp=hpMax;
		this.speed=speed;
		this.dOrientation = rotationSpeed * Math.PI/40;
		this.hpLostPerInstant=hpLostPerInstant;
		this.captors=captors;
		this.brain=brain;
		this.type=type;
		this.alive=true;
		this.color=color;
		this.nbInput=nbInput;
		this.creatures=creatures;
		this.collectables=collectables;
		this.delimitations=delimitations;
		this.box=box;
		initCaptors(thingsToSee);
	}

	public void reset(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.hp = hpMax;
		Individu newBrain = selec.getOffspring(this.brain);
		this.brain = newBrain;
		this.alive = true;
		invincibleTimeLeft = invincibleTime;
		invincible = true;

	}

	public void draw(Graphics g, boolean selected)
	{
		this.selected=selected;
		if (selected)
			drawScore(g);
		if (selected || DRAW_HP)
			drawHP(g);
		if (selected || DRAW_CAPTORS)
			drawCaptors(g);
		drawCreature(g);
	}
	
	private void drawScore(Graphics g)
	{
		Color oldColor = g.getColor();
		g.setColor(Color.BLACK);
		g.drawString("Score créature : "+new DecimalFormat("##.##").format(brain.getScore()),10, 150);
		g.setColor(oldColor);
	}

	private void drawHP(Graphics g)
	{
		Color oldColor = g.getColor();
		int xF = xFinal();
		int yF = (int)((y-radius/2)*SIZE+SCROLL_Y);
		int szF = sizeFinal();
		int hpSzF = (int)(szF*hp/hpMax);
		int hF = (int)((radius/5)*SIZE);

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
		updateInvincibility();
		updatePosition();
		updateCaptors();
		updateScore();
	}

	private void updateInvincibility()
	{
		if (invincibleTimeLeft>0)
		{
			invincibleTimeLeft--;
			if (invincibleTimeLeft <= 0)
				invincible = false;
		}
	}

	protected void loseHp(double damages)
	{
		if (!isInvincible())
			hp -= damages;
	}
	
	protected abstract void updateScore();

	private void initCaptors(int[] thingsToSee)
	{
		for (Captor c : captors)
		{
			c.setCreature(this);
			c.setThingsToSee(thingsToSee);
			c.update(x+radius/2, y+radius/2, orientation);
		}
	}

	public void updateCaptors()
	{
		for (Captor c : captors)
			c.update(x+radius/2, y+radius/2, orientation);
	}

	public void detect()
	{
		for (Captor c : captors)
			c.detect(creatures, collectables, delimitations, box);
	}

	private void updatePosition()
	{
		hp-=hpLostPerInstant;
		if (hp<=0)
		{
			alive=false;
			hp = 0;
			return;
		}
		double[] inputs = new double[nbInput];
		int cpt=0;
		for (int i=0;i<captors.length;i++)
		{
			double[] results = captors[i].getResults();
			for (int j=0;j<results.length;j++)
				inputs[cpt++] = results[j];
			List<Integer> seenThings = captors[i].getThingsInSight();
			applySeenFitness(seenThings);
		}
		inputs[cpt++]=1-hp/hpMax;
		double xCenter = box.width/2;
		double yCenter = box.height/2;
		double xRatio = x - xCenter;
		double yRatio = y - yCenter;
		inputs[cpt++] = (xRatio>0)?xRatio/xCenter:0; 
		inputs[cpt++] = (xRatio>0)?0:-xRatio/xCenter;
		inputs[cpt++] = (yRatio>0)?yRatio/yCenter:0;
		inputs[cpt++] = (yRatio>0)?0:-yRatio/yCenter;
		double[] decisions = brain.getOutputs(inputs);
		applyDecisions(decisions);
	}

	protected abstract void applySeenFitness(List<Integer> seenThings);

	protected abstract void applyDecisions(double[] decisions);

	protected void moveFront(double intensity)
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

	protected void straff(double intensity)
	{
		x-=Math.cos(orientation+Math.PI/2)*speed*intensity;
		y+=Math.sin(orientation+Math.PI/2)*speed*intensity;
	}

	/**
	 * Interactions
	 */

	public abstract void interactWith(Collectable c);

	public abstract void interactWith(Creature c);

	public abstract void interactWith(Zone z);

	public void interactWith(Delimitation d)
	{
		switch (d.getType())
		{
		case PROJECTILE:
			if (((Projectile)d).getSender()==this)
				return;
			break;
		case FIREBALL:
			break;
		default:
			System.out.println("Creature.interactWith(Delimitation d) - Delimitation inconnue : "+d.getType());
			System.exit(0);
			break;
		}
		loseHp(d.getDamages());
		if (hp<=0)
			alive=false;
	}

	/**
	 *  CALCULS DE LA POSITION SUR L'ECRAN
	 */

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
		return (int) (radius*SIZE);
	}

	/**
	 *  GETTERS
	 */

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
		return radius;
	}

	public Individu getBrain()
	{
		return brain;
	}

	public boolean isInvincible()
	{
		return invincible;
	}

}
