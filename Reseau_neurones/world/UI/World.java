package UI;

import static utils.Constantes.BOMB;
import static utils.Constantes.DRAW_ALL;
import static utils.Constantes.DRAW_CAPTORS;
import static utils.Constantes.DRAW_HP;
import static utils.Constantes.FUEL;
import static utils.Constantes.MEAT;
import static utils.Constantes.PAUSE;
import static utils.Constantes.POWERUP;
import static utils.Constantes.SCROLL_X;
import static utils.Constantes.SCROLL_Y;
import static utils.Constantes.SIZE;
import static utils.Constantes.SLOW_MO;
import static utils.Constantes.VEGETABLE;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import UI.controls.Controller;
import UI.controls.WorldController;
import collectables.Collectable;
import creatures.Creature;
import creatures.CreatureFactory;
import fr.lewon.Individual;
import fr.lewon.SelectionProcessor;
import fr.lewon.Trial;
import fr.lewon.exceptions.NNException;
import fr.lewon.nn.impl.NeuralNetworkClassic;
import fr.lewon.selection.SelectionType;
import fr.lewon.utils.PopulationInfos;
import interactions.CreaCollecInteraction;
import interactions.CreaCreaInteraction;
import interactions.CreaDelimInteraction;
import limitations.Delimitation;
import limitations.DelimitationBox;
import utils.Constantes;
import utils.Draftman;
import utils.IntersectionsChecker;
import zones.Zone;

public abstract class World extends Trial
{

	private final String name = "NEURAL NETWORK PROJECT";

	private JFrame jf;
	private JPanel jp;

	private int fpsToDraw = 0;

	public List<Delimitation> delimitations;
	public List<Creature> creatures;
	public List<Collectable> collectables;
	public List<Zone> zones;
	public DelimitationBox box;

	protected int collectableAmount;
	protected int meatCount,vegetableCount, powerUpCount, fuelCount, bombCount;

	private Creature selectedCreature;

	private JFreeChart chart;
	private List<PopulationInfos> graphInfos;
	private static final int MAX_GRAPHINFOS_SIZE = 100;


	/**
	 * Cr�e un environnement de la taille de l'�cran
	 */
	public World(String name)
	{
		this(name, Toolkit.getDefaultToolkit().getScreenSize());
	}

	/**
	 * Cr�e un environnement de la taille de la dimension pass�e en argument
	 * @param dimension Dimension de l'image
	 */
	public World(String name, Dimension dimension)
	{
		this(name, (int) dimension.getWidth(), (int) dimension.getHeight());
	}

	/**
	 * Cr�e un environnement avec pour largeur et hauteur d'image les arguments pass�s
	 * @param w largeur de l'image
	 * @param h hauteur de l'image
	 */
	public World(String name, int w, int h)
	{
		init(w,h);
	}

	/**
	 * Initialise l'environnement en fonction de la largeur / hauteur pass�s en argument
	 * @param w largeur
	 * @param h hauteur
	 */
	@SuppressWarnings("serial")
	private void init(int w, int h)
	{
		creatures = new ArrayList<>(1000);
		collectables = new ArrayList<>(1000);
		delimitations = new ArrayList<>(1000);
		zones = new ArrayList<>(1000);
		graphInfos = new ArrayList<>(MAX_GRAPHINFOS_SIZE);

		jf = new JFrame(name);
		jf.setSize(w, h);
		jp = new JPanel() 
		{

			public void paint(Graphics g)
			{
				super.getRootPane().updateUI();
				Draftman draftman = new Draftman();
				if (DRAW_ALL)
				{
					draftman.drawWorld(selectedCreature, creatures, collectables, delimitations, box, g);
					g.drawString("FPS : " + fpsToDraw, 10, 15);
				}
				draftman.drawInfos(infosToPrint(), g);	
			}

		};
		jf.add(jp);
		
		DefaultXYDataset dataSet = new DefaultXYDataset();
		dataSet.addSeries("Moyenne", new double[2][100]);
		chart = ChartFactory.createXYLineChart("popInfos", "Generation", "Score", dataSet);
		ChartPanel graphPane = new ChartPanel(chart) {
			
		};
		jf.add(graphPane);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Controller ctrl = new WorldController(this);
		jp.addMouseListener(ctrl);
		jp.addMouseMotionListener(ctrl);
		jp.addKeyListener(ctrl);
		jp.addMouseWheelListener(ctrl);
		jp.setFocusable(true);

	}

	public void start(int x, int y)
	{
		start(x, y, true);
	}

	public void start(int x, int y, boolean visible)
	{
		jf.setVisible(visible);
		box=new DelimitationBox(0, 0, x, y);

		SelectionProcessor sp = new SelectionProcessor(this, SelectionType.ROULETTE.getSelectionImpl(), 0.25, 0.6);

		List<Individual> brains = new ArrayList<>();
		for (int i = 0 ; i < 400 ; i++) {
			brains.add(new NeuralNetworkClassic(23, 2, Arrays.asList(5)));
		}
		try {
			Individual best = sp.start(brains, 600, Integer.MAX_VALUE);
			while (true) {
				getFitness(best);
			}
		} catch (NNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Dessinateur
	 */

	private List<String> infosToPrint()
	{
		List<String> infos = new ArrayList<>();
		infos.add((DRAW_CAPTORS?"Cacher":"Afficher")+" capteurs : S");
		infos.add((DRAW_HP?"Cacher":"Afficher")+" points de vie : H");
		infos.add((DRAW_ALL?"Cacher":"Afficher")+" la simulation : G");
		infos.add((SLOW_MO?"Désactiver":"Activer")+" le slow motion : V");
		infos.add((!PAUSE?"Mettre en pause":"Quitter la pause")+" : SPACE");
		return infos;
	}


	/**
	 * M�thodes � impl�menter pour personnaliser le monde
	 */

	protected abstract void initSelections();
	protected abstract void generateCollectables();
	protected abstract void generateDelimitations();

	/**
	 * M�thodes de calcul de position des objets
	 */

	private void sleepAndRefresh()
	{
		updateAndRemoveDelimitations();

		updateAndReplaceCollectables();

		generateDelimitations();

		updateCreatures();
	}



	private void updateAndRemoveDelimitations()
	{
		for (int i=0;i<delimitations.size();i++)
		{
			Delimitation d = delimitations.get(i);
			if (d==null)
				continue;
			if (d.isExpired() || !IntersectionsChecker.contains(box, d))
			{
				delimitations.remove(i);
				i--;
			}
			else
				d.update();
		}
	}

	private void updateAndReplaceCollectables()
	{
		meatCount=0;
		vegetableCount=0;
		fuelCount=0;
		powerUpCount=0;

		for (int i=0;i<collectables.size();i++)
		{
			Collectable c = collectables.get(i);
			if (c.isConsumed() || !IntersectionsChecker.contains(box, c))
				collectables.remove(i--);
			else
			{
				c.update();
				switch (c.getType())
				{
				case MEAT : 
					meatCount++;
					break;
				case VEGETABLE :
					vegetableCount++;
					break;
				case FUEL :
					fuelCount++;
					break;
				case POWERUP :
					powerUpCount++;
					break;
				case BOMB :
					bombCount++;
					break;
				default:
					System.out.println("World.sleepAndRefresh - Collectable inconnu");
				}
			}
		}
		for (int i=collectables.size();i<collectableAmount;i++)
			generateCollectables();
	}

	private void updateCreatures()
	{
		List<Creature> toRemove = new ArrayList<>();
		for (int i=0; i<creatures.size() ;i++)
		{
			Creature c = creatures.get(i);
			if (!c.isAlive() || !IntersectionsChecker.contains(box, c))
			{
				toRemove.add(c);
				continue;
			}
			c.update();
			for (int j=0;j<collectables.size();j++)
			{
				Collectable collect = collectables.get(j);
				if (!collect.isConsumed())
					if (IntersectionsChecker.preciseIntersects(c,collect))
						new CreaCollecInteraction(c,collect).process();
			}
			for (int j=0;j<delimitations.size();j++)
			{
				Delimitation delim = delimitations.get(j);
				if (!delim.isExpired())
					if (IntersectionsChecker.preciseIntersects(c,delim))
						new CreaDelimInteraction(c,delim).process();
			}
			for (int j=0; j<creatures.size() ;j++)
			{
				Creature creature2 = creatures.get(j);
				if (creature2 == c)
					continue;
				if (creature2.isAlive())
					if ( IntersectionsChecker.preciseIntersects(c, creature2) || IntersectionsChecker.intersects(c.getAttackHitbox(),creature2))
					{
						new CreaCreaInteraction(c,creature2).process();
					}
			}
		}
		for (Creature c : toRemove) {
			creatures.remove(c);
		}
	}


	/**
	 * Actions disponibles pour le Controller
	 */

	public void pause()
	{
		PAUSE=!PAUSE;
	}

	public void changeShowCaptors()
	{
		DRAW_CAPTORS=!DRAW_CAPTORS;
	}

	public void changeShowHP()
	{
		DRAW_HP=!DRAW_HP;
	}

	public void changeShowAll()
	{
		DRAW_ALL=!DRAW_ALL;
	}

	public void changeSlowMo()
	{
		SLOW_MO=!SLOW_MO;
	}

	public void selectCreature(Point point)
	{
		double x = point.getX();
		double y = point.getY();
		double realX = (x-SCROLL_X)/SIZE;
		double realY = (y-SCROLL_Y)/SIZE;

		for (int i=0;i<creatures.size();i++)
		{
			Creature c = creatures.get(i);
			double c_x  = c.getX();
			double c_y  = c.getY();
			double c_sz = c.getSize();
			if (c_x>realX) 		continue;
			if (c_y>realY) 		continue;
			if (c_x+c_sz<realX) continue;
			if (c_y+c_sz<realY) continue;
			selectedCreature=c;
			return;
		}
		selectedCreature=null;
	}

	@Override
	protected double testIndividual(Individual individual) throws NNException {

		delimitations.clear();

		List<Creature> testedCrea = new ArrayList<>();
		for (int i = 0 ; i < 10 ; i++) {
			testedCrea.add(CreatureFactory.generateComplexDodger(individual, this));
		}
		creatures.addAll(testedCrea);

		int fps = 0;
		long timeRefFps = System.nanoTime();
		long timeRefRecount = System.nanoTime();
		while (!creatures.isEmpty())
		{
			if (!SLOW_MO || System.nanoTime() - timeRefFps > Constantes.TIME_TO_WAIT)
			{
				timeRefFps = System.nanoTime();
				if (!PAUSE) {
					sleepAndRefresh();
					fps++;
				} else {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if (System.nanoTime() - timeRefRecount > 1000000000)
			{
				timeRefRecount = System.nanoTime();
				fpsToDraw = fps;
				fps = 0;
			}
		}



		double score = 0;
		for (Creature c : testedCrea) {
			score += c.getBrain().getFitness();
		}
		return score;
	}

	@Override
	public void processBetweenGenerationsActions(PopulationInfos infos) {
		if (graphInfos.size() == MAX_GRAPHINFOS_SIZE) {
			graphInfos.remove(0);
		}
		graphInfos.add(infos);

		System.out.println("Max : " + infos.getMaxScore());
	}


}
