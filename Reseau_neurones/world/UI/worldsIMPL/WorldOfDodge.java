package UI.worldsIMPL;

import static utils.Constantes.*;

import java.util.Random;

import UI.World;
import limitations.throwables.FireBall;

public class WorldOfDodge extends World
{
	
	private static final long serialVersionUID = -5324916435284951987L;
	
	private int cdFireBall=10;
	private int cdAvancement=0;

	@Override
	protected void initSelections()
	{
		GENERATION_LENGTH = 200000;
		initSelection(POPULATION_SIZE_COMPLEXDODGER, GENERATION_COUNT, TYPE_COMPLEXDODGER);
	}

	@Override
	protected void generateCollectables()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void generateDelimitations()
	{
		cdAvancement++;
		if (cdAvancement>=cdFireBall)
		{
			cdAvancement=0;
			if (new Random().nextBoolean())
			delimitations.add(new FireBall(0, new Random().nextDouble()*box.getHeight(), 0));
			else
				delimitations.add(new FireBall(box.getWidth()-5, new Random().nextDouble()*box.getHeight(), Math.PI));
		}
	}
}
