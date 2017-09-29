package collectables;

import static utils.Constantes.SCROLL_X;
import static utils.Constantes.SCROLL_Y;
import static utils.Constantes.SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Collectable
{

	protected double x,y;
	protected boolean consumed;
	private final int type;
	private double size;
	private Rectangle2D hitbox;
	private Color color;
	
	public Collectable(double x, double y, double size, int type, Color color)
	{
		this.x=x;
		this.y=y;
		this.size=size;
		this.consumed=false;
		this.type=type;
		this.color=color;
		hitbox = new Rectangle2D.Double(x,y,size,size);
	}
	
	public abstract void update();
	
	public void draw(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(color);
		g.fillRect(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(Color.BLACK);
		g.drawRect(xFinal(), yFinal(), sizeFinal(), sizeFinal());
		g.setColor(oldColor);
	}
	
	public void consume()
	{
		consumed=true;
	}
	
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
	
	//GETTERS
	
	public boolean isConsumed()
	{
		return consumed;
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
