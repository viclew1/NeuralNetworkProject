package UI.worldsIMPL;

import static utils.Constantes.*;

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
		super();
	}

	public WorldOfDodge(Dimension dimensions)
	{
		super(dimensions);
	}

	public WorldOfDodge(int w, int h)
	{
		super(w, h);
	}

	@Override
	protected void initSelections()
	{
		initSelection(POPULATION_SIZE_COMPLEXDODGER, GENERATION_COUNT, TYPE_COMPLEXDODGER);
		initSelection(POPULATION_SIZE_SIMPLEDODGER, GENERATION_COUNT, TYPE_SIMPLEDODGER);
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
