package UI.controls;

import static utils.Constantes.*;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import UI.World;

public class WorldController extends Controller
{

	private World world;
	private Point lastClicLocation=new Point(0,0);
	private int clicType;


	public WorldController(World world)
	{
		this.world=world;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		world.selectCreature(e.getPoint());
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		//world.grabFocus();
		clicType=e.getButton();
		if (clicType==MouseEvent.BUTTON1)
			lastClicLocation = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (clicType==MouseEvent.BUTTON1)
		{
			Point newMouseLoc = e.getPoint();
			SCROLL_X+=(newMouseLoc.x-lastClicLocation.x);
			SCROLL_Y+=(newMouseLoc.y-lastClicLocation.y);
			lastClicLocation=newMouseLoc;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		double newSize;
		double multiplier;
		int rotation = e.getWheelRotation();
		if (rotation > 0)
			multiplier = Math.pow(0.9, rotation);
		else
			multiplier = Math.pow(1.1, -rotation);
		newSize = SIZE * multiplier;
		if (newSize>SIZE_MAX) newSize=SIZE_MAX;
		if (newSize<SIZE_MIN) newSize=SIZE_MIN;
		SIZE=newSize;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_SPACE:
			world.pause();
			break;
		case KeyEvent.VK_S:
			world.changeShowCaptors();
			break;
		case KeyEvent.VK_H:
			world.changeShowHP();
			break;
		case KeyEvent.VK_G:
			world.changeShowAll();
			break;
		case KeyEvent.VK_V:
			world.changeSlowMo();
			break;
		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

}
