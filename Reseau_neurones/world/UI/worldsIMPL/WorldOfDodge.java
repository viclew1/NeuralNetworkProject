package UI.worldsIMPL;

import java.awt.Dimension;
import java.util.Random;

import UI.World;
import limitations.throwables.FireBall;

public class WorldOfDodge extends World
{

	private int cdFireBall=7;
	private int cdAvancement=0;


	public WorldOfDodge()
	{
		super("World Of Dodge");
	}

	public WorldOfDodge(Dimension dimensions)
	{
		super("World Of Dodge", dimensions);
	}

	public WorldOfDodge(int w, int h)
	{
		super("World Of Dodge", w, h);
	}

	@Override
	protected void initSelections()
	{
	}

	@Override
	protected void generateCollectables()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void generateDelimitations()
	{
		Random r = new Random();
		cdAvancement++;
		if (cdAvancement>=cdFireBall)
		{
			cdAvancement=0;
			if (r.nextBoolean())
				delimitations.add(new FireBall(0, r.nextDouble()*box.getHeight(), -Math.PI/8 + r.nextDouble()*Math.PI/4));
			else
				delimitations.add(new FireBall(box.getWidth()-5, r.nextDouble()*box.getHeight(), Math.PI - Math.PI/8 + r.nextDouble()*Math.PI/4));
		}
	}
}
