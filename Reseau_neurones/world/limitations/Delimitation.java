package limitations;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import creatures.Creature;
import creatures.shooters.ShooterCreature;

public abstract class Delimitation
{

	protected double x,y,w,h;
	protected final ShooterCreature sender;
	protected double damages;
	private Rectangle2D hitbox;

	private boolean expired=false;

	private final int type;


	public Delimitation(double x, double y, double w, double h, double damages, ShooterCreature sender, int type)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.type=type;
		this.damages=damages;
		this.sender=sender;
		hitbox = new Rectangle2D.Double();
		updateHitBox();
	}

	public abstract void draw(Graphics g);

	public void update()
	{
		updatePosition();
		updateHitBox();
	}

	protected abstract void updatePosition();

	private void updateHitBox()
	{
		hitbox.setFrameFromDiagonal(x, y, x+w, y+h);
	}

	public abstract void interactWith(Creature o1);

	public void expire()
	{
		expired=true;
	}

	/**
	 * Getters
	 */

	public double getDamages()
	{
		return damages;
	}

	public Creature getSender()
	{
		return sender;
	}

	public boolean isExpired()
	{
		return expired;
	}

	public int getType()
	{
		return type;
	}
	
	public Rectangle2D getHitBox()
	{
		return hitbox;
	}
}
