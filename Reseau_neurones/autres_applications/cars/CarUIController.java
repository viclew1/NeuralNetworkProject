package cars;

import static utils.Constantes.SCROLL_X;
import static utils.Constantes.SCROLL_Y;
import static utils.Constantes.SIZE;
import static utils.Constantes.SIZE_MAX;
import static utils.Constantes.SIZE_MIN;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import UI.World;
import UI.controls.Controller;

public class CarUIController extends Controller
{

	private Environnement env;
	private Point lastClicLocation=new Point(0,0);
	private int clicType;


	public CarUIController(Environnement env)
	{
		this.env=env;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
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
			env.pause();
			break;
		case KeyEvent.VK_S:
			env.changeShowCaptors();
			break;
		case KeyEvent.VK_G:
			env.changeShowAll();
			break;
		case KeyEvent.VK_V:
			env.changeSlowMo();
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
