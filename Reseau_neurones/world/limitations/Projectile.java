package limitations;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;

import creatures.Creature;

public class Projectile extends Delimitation {
	
	private double speed, orientation, damages;
	private Creature sender;

	public Projectile(double x, double y, double size, double speed, double orientation, double damages, Creature sender) {
		super(x, y, size, size, damages, PROJECTILE);
		
		this.speed=speed;
		this.orientation=orientation;
		this.sender=sender;
	}

	@Override
	public void draw(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(Color.RED);
		g.fillOval((int)(x*SIZE+SCROLL_X), (int)(y*SIZE+SCROLL_Y), (int)(w*SIZE), (int)(h*SIZE));
		g.setColor(oldColor);
	}

	@Override
	public void update() {
		x+=Math.cos(orientation)*speed;
		y-=Math.sin(orientation)*speed;
	}

	public double getDamages()
	{
		return damages;
	}

	public Creature getSender()
	{
		return sender;
	}
	
}
