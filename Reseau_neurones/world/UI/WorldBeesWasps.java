package UI;

import static utils.Constantes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import collectables.Meat;
import collectables.Vegetable;

public class WorldBeesWasps extends World
{

	private static final long serialVersionUID = 919723644950485137L;

	@Override
	protected void initSelections()
	{
		GENERATION_LENGTH = 80000;
		collectableAmount = FOOD_AMOUNT;
		initSelection(POPULATION_SIZE_BEE, GENERATION_COUNT, TYPE_BEE);
		initSelection(POPULATION_SIZE_WASP, GENERATION_COUNT, TYPE_WASP);
	}

	@Override
	protected void generateCollectables()
	{
		if (meatCount > vegetableCount)
		{
			vegetableCount++;
			collectables.add(new Vegetable(3+new Random().nextDouble()*(box.getW()-6), 3+new Random().nextDouble()*(box.getH()-6)));
		}
		else
		{
			meatCount++;
			collectables.add(new Meat(3+new Random().nextDouble()*(box.getW()-6), 3+new Random().nextDouble()*(box.getH()-6)));
		}
	}

	@Override
	protected List<String> infosToPrint()
	{
		List<String> infos = new ArrayList<>();
		infos.add("Génération : "+generationCount);
		infos.add((SLOW_MO_MODE?"Désactiver":"Activer")+" slow motion : V");
		infos.add((DRAW_CAPTORS?"Cacher":"Afficher")+" capteurs : S");
		infos.add((DRAW_HP?"Cacher":"Afficher")+" points de vie : H");
		infos.add((DRAW_ALL?"Cacher":"Afficher")+" la simulation : G");
		infos.add((!PAUSE?"Mettre en pause":"Quitter la pause")+" : SPACE");
		infos.add("Tuer cette génération entière : K");
		return infos;
	}
}
