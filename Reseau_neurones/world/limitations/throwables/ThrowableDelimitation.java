package limitations.throwables;

import static utils.Constantes.SCROLL_X;
import static utils.Constantes.SCROLL_Y;
import static utils.Constantes.SIZE;

import java.awt.Color;
import java.awt.Graphics;

import creatures.Creature;
import creatures.shooters.ShooterCreature;
import limitations.Delimitation;

public class ThrowableDelimitation extends Delimitation
{
	private double speed, orientation;
	private Color color;
	private boolean expireOnTouch;

	public ThrowableDelimitation(double x, double y, double sz, double speed, double orientation, double damages, double range, boolean expireOnTouch, ShooterCreature sender,
			int type, Color color)
	{
		super(x, y, sz, sz, damages, range, sender, type);
		this.speed=speed;
		this.orientation=orientation;
		this.color=color;
		this.expireOnTouch=expireOnTouch;
	}

	@Override
	public void draw(Graphics g) 
	{
		Color oldColor = g.getColor();
		g.setColor(color);
		g.fillOval((int)(x*SIZE+SCROLL_X), (int)(y*SIZE+SCROLL_Y), (int)(w*SIZE), (int)(h*SIZE));
		g.setColor(oldColor);
	}

	@Override
	public void update() 
	{
		x+=Math.cos(orientation)*speed;
		y-=Math.sin(orientation)*speed;
		range -= speed;
		if (range <= 0) expire();
	}

	@Override
	public void interactWith(Creature o1)
	{
		if (getSender()!=o1)
		{
			if (sender!=null)
				sender.targetReport(o1.getType());
			if (expireOnTouch)
				expire();
		}
	}

}
