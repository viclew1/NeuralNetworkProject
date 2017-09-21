package limitations;

import static utils.Constantes.*;

import java.awt.Color;
import java.awt.Graphics;

import creatures.Creature;
import creatures.ShooterCreature;

public class Projectile extends Delimitation {

	private double speed, orientation;
	private Color color;
	
	public Projectile(double x, double y, double size, double speed, double orientation, double damages, ShooterCreature sender, Color color) {
		super(x, y, size, size, damages, sender, PROJECTILE);

		this.speed=speed;
		this.orientation=orientation;
		this.color=color;
	}

	@Override
	public void draw(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(color);
		g.fillOval((int)(x*SIZE+SCROLL_X), (int)(y*SIZE+SCROLL_Y), (int)(w*SIZE), (int)(h*SIZE));
		g.setColor(oldColor);
	}

	@Override
	public void update() {
		x+=Math.cos(orientation)*speed;
		y-=Math.sin(orientation)*speed;
	}

	@Override
	public void interactWith(Creature o1)
	{
		if (getSender()!=o1)
		{
			sender.targetReport(o1.getType());
			expire();
		}
	}

}