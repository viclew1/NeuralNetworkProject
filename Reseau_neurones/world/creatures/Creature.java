package creatures;

import static utils.Constantes.DRAW_CAPTORS;
import static utils.Constantes.DRAW_HP;
import static utils.Constantes.FIREBALL;
import static utils.Constantes.PROJECTILE;
import static utils.Constantes.SCROLL_X;
import static utils.Constantes.SCROLL_Y;
import static utils.Constantes.SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import UI.World;
import captors.Captor;
import collectables.Collectable;
import fr.lewon.Individual;
import fr.lewon.exceptions.NNException;
import limitations.Delimitation;
import limitations.DelimitationBox;
import limitations.throwables.Projectile;
import zones.Zone;

public abstract class Creature
{
	private boolean invincible = true;
	private int invincibleTime = 60;
	private int invincibleTimeLeft = 60;

	protected double x;
	protected final double hpMax;
	protected double hp;
	protected double y;
	protected final double radius;
	private final double range;

	protected double damages = 0;

	protected double orientation=0;
	protected final double dOrientation;
	protected final double orientationMin=0;
	protected final double orientationMax=2*Math.PI;
	protected double speed;
	protected Individual brain;
	protected Captor[] captors;
	private boolean alive;
	private final int type;
	protected Color color;
	private boolean selected=false;

	protected int nbInput;
	protected double hpLostPerInstant;

	protected double maturity = 0;
	protected int childCount = 0;
	protected final double adultAge = 25;
	
	protected int ttl = 12000;
	private int timeLived = 0;

	protected World world;
	protected List<Creature> creatures;
	protected List<Collectable> collectables;
	protected List<Delimitation> delimitations;
	protected DelimitationBox box;

	private Line2D attackHitbox; 

	public Creature(double x, double y, double radius, double hpMax, double speed, double rotationSpeed, double hpLostPerInstant, Captor[] captors, int[][] thingsToSee, Individual brain, int type, Color color, int nbInput, World world)
	{
		this.invincibleTimeLeft = invincibleTime;
		this.invincible = true;
		this.x=x;
		this.y=y;
		this.radius=radius;
		this.range = 1.1 * radius;
		this.attackHitbox = new Line2D.Double(x+radius/2, y+radius/2, x+radius/2+Math.cos(orientation)*range, y+radius/2-Math.sin(orientation)*range);
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
		this.world = world;
		this.creatures=world.creatures;
		this.collectables=world.collectables;
		this.delimitations=world.delimitations;
		this.box=world.box;
		initCaptors(thingsToSee);
	}
	
	public void reset(double x, double y, Individual newBrain)
	{
		this.x = x;
		this.y = y;
		this.brain = newBrain;
		this.maturity = 0;
		this.childCount = 0;
		revive();
		invincibleTimeLeft = invincibleTime;
		invincible = true;

	}

	public Line2D getAttackHitbox()
	{
		return attackHitbox;
	}

	public double getDamages()
	{
		return damages;
	}

	public void die()
	{
		this.alive = false;
		this.hp = 0;
	}
	
	public void setHp(double hp)
	{
		this.hp = hp;
	}
	
	public void heal(double hp)
	{
		this.hp += hp;
		if (this.hp > hpMax) this.hp = hpMax;	
	}
	
	public void loseHp(double damages)
	{
		if (!isInvincible())
		{
			hp -= damages;
			if (hp<=0)
				die();
		}
	}

	public void revive()
	{
		this.alive = true;
		this.hp = hpMax;
		timeLived = 0;
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
		g.drawString("Score cr�ature : "+new DecimalFormat("##.##").format(brain.getFitness()),10, 150);
		g.drawString("Maturit� : "+ new DecimalFormat("##.##").format(maturity), 10, 170);
		g.drawString("Compte d'enfants : "+ childCount, 10, 190);
		g.drawString("�ge : "+ timeLived, 10, 210);
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
		{
			g.setColor(color);
		}
		else
		{
			g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),127));
		}

		g.drawLine(
				(int)(attackHitbox.getX1()*SIZE+SCROLL_X), 
				(int)(attackHitbox.getY1()*SIZE+SCROLL_Y), 
				(int)(attackHitbox.getX2()*SIZE+SCROLL_X), 
				(int)(attackHitbox.getY2()*SIZE+SCROLL_Y));
		g.fillOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(Color.BLACK);
		g.drawOval(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(oldColor);
	}

	public void drawBrain(Graphics g)
	{
	}

	private void drawCaptors(Graphics g)
	{
		for (Captor c : captors)
			c.draw(g);
	}

	public void update()
	{
		loseHp(hpLostPerInstant);
		
		updateInvincibility();
		detect();
		updatePosition();
		updateCaptors();

		addSpecialFitness();
		
		brain.setFitness(brain.getFitness() + 0.01 * (childCount+1));
		maturity+=0.01;
		timeLived+=0.01;
		if (timeLived >= ttl) die();
	}
	
	protected abstract void addSpecialFitness();

	private void updateInvincibility()
	{
		if (invincibleTimeLeft>0)
		{
			invincibleTimeLeft--;
			if (invincibleTimeLeft <= 0)
				invincible = false;
		}
	}

	private void initCaptors(int[][] thingsToSee)
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
		//System.out.println(CreatureFactory.getTypeStrFromTypeInt(getType()) + " " + (captors[0].getResultCount() * captors.length + 7));
		double[] inputs = new double[nbInput];
		int cpt=0;
		for (int i=0;i<captors.length;i++)
		{
			double[] results = captors[i].getResults();
			for (int j=0;j<results.length;j++)
				inputs[cpt++] = results[j];
		}
		double xCenter = box.width/2;
		double yCenter = box.height/2;
		double xRatio = x - xCenter;
		double yRatio = y - yCenter;
		inputs[cpt++] = 1 - hp/hpMax;
		inputs[cpt++] = (orientation >= Math.PI)?(orientation-Math.PI)/Math.PI:0;
		inputs[cpt++] = (orientation < Math.PI)?orientation/Math.PI:0;
		inputs[cpt++] = (xRatio>0)?xRatio/xCenter:0;
		inputs[cpt++] = (xRatio>0)?0:-xRatio/xCenter;
		inputs[cpt++] = (yRatio>0)?yRatio/yCenter:0;
		inputs[cpt++] = (yRatio>0)?0:-yRatio/yCenter;
		addParticularInput(inputs, cpt);
		List<Double> inputsList = new ArrayList<>();
		for (Double d : inputs) {
			inputsList.add(d);
		}
		List<Double> decisions;
		try {
			decisions = brain.getOutputs(inputsList);
			applyDecisions(decisions);
		} catch (NNException e) {
		}

		attackHitbox.setLine(
				x+radius/2, 
				y+radius/2, 
				x+radius/2+Math.cos(orientation)*range, 
				y+radius/2-Math.sin(orientation)*range);
	}

	protected abstract void addParticularInput(double[] inputs, int currentCount);

	protected abstract void applyDecisions(List<Double> decisions);

	protected void moveFront(double intensity)
	{
		x+=Math.cos(orientation)*speed*intensity;
		y-=Math.sin(orientation)*speed*intensity;
	}

	protected void turn(double intensity)
	{
		orientation+=dOrientation*intensity;
		if (orientation>orientationMax)
			orientation-=Math.PI * 2;
		if (orientation<orientationMin)
			orientation+=Math.PI * 2;
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

	public abstract void touchedBy(Creature c);

	public abstract void touch(Creature c);

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
	}


	/**
	 * Reproduction
	 */

	public void reproduceWith(Creature c)
	{
		/*if (canReproduce() && c.canReproduce() && c.getType() == type)
		{
			maturity = 0;
			c.maturity = 0;
			if (team != null)
			{
				team.add(CreatureFactory.generate(CreatureFactory.getTypeStrFromTypeInt(type), selec.getOffspring(this.getBrain(),c.getBrain()), selec, world));
			}
			else
			{
				creatures.add(CreatureFactory.generate(CreatureFactory.getTypeStrFromTypeInt(type), selec.getOffspring(this.getBrain(),c.getBrain()), selec, world));
			}
			childCount ++;
			c.childCount ++;
		}*/
	}

	public boolean canReproduce()
	{
		return maturity >= adultAge;
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

	public Individual getBrain()
	{
		return brain;
	}

	public double getHp() 
	{
		return hp;
	}

	public boolean isInvincible()
	{
		return invincible;
	}

}
