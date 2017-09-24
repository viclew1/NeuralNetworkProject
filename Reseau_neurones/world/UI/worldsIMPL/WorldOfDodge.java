package UI.worldsIMPL;

import static utils.Constantes.*;

import java.util.Random;

import UI.World;
import limitations.throwables.FireBall;

public class WorldOfDodge extends World
{
	
	private int cdFireBall=5;
	private int cdAvancement=0;

	@Override
	protected void initSelections()
	{
		GENERATION_LENGTH = 200000;
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
		cdAvancement++;
		if (cdAvancement>=cdFireBall)
		{
			cdAvancement=0;
			delimitations.add(new FireBall(0, new Random().nextDouble()*box.getH()));
		}
	}
}
