package UI;

import static utils.Constantes.*;

import java.util.ArrayList;
import java.util.List;

public class WorldOfTanks extends World
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8366677204022090143L;

	@Override
	protected void initSelections()
	{
		//initSelection(POPULATION_SIZE_SOLDIER, GENERATION_COUNT, TYPE_SOLDIER);
		initSelection(POPULATION_SIZE_TANK, GENERATION_COUNT, TYPE_TANK);
	}

	@Override
	protected void generateCollectables()
	{
		
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
