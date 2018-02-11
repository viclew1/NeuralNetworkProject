package UI;

import static utils.Constantes.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import UI.controls.Controller;
import UI.controls.WorldController;
import collectables.Collectable;
import creatures.Creature;
import creatures.CreatureFactory;
import genetics.Epreuve;
import genetics.Individu;
import genetics.Selection;
import interactions.CreaCollecInteraction;
import interactions.CreaCreaInteraction;
import interactions.CreaDelimInteraction;
import limitations.Delimitation;
import limitations.DelimitationBox;
import neural_network.NeuralNetwork;
import utils.CSVUtils;
import utils.Draftman;
import utils.IntersectionsChecker;
import utils.SaveUtils;
import zones.Zone;

public abstract class World implements Epreuve
{

	private final String name = "NEURAL NETWORK PROJECT";

	private final String fileName;

	private Map<String, double[]> weights;

	private JFrame jf;
	private JPanel jp;

	private int fpsToDraw = 0;

	private List<Selection> selections;
	private boolean evolving = true;

	public List<Delimitation> delimitations;
	public List<Creature> creatures;
	public List<Collectable> collectables;
	public List<Zone> zones;
	public DelimitationBox box;

	protected int collectableAmount;
	protected int meatCount,vegetableCount, powerUpCount, fuelCount, bombCount;

	private Creature selectedCreature;


	/**
	 * Crée un environnement de la taille de l'écran
	 */
	public World(String name)
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		init((int)(d.getWidth()), (int)(d.getHeight()));
		this.fileName = name;
	}

	/**
	 * Crée un environnement de la taille de la dimension passée en argument
	 * @param dimension Dimension de l'image
	 */
	public World(String name, Dimension dimension)
	{
		init((int)(dimension.getWidth()), (int)(dimension.getHeight()));
		this.fileName = name;
	}

	/**
	 * Crée un environnement avec pour largeur et hauteur d'image les arguments passés
	 * @param w largeur de l'image
	 * @param h hauteur de l'image
	 */
	public World(String name, int w, int h)
	{
		init(w,h);
		this.fileName = name;
	}

	/**
	 * Initialise l'environnement en fonction de la largeur / hauteur passés en argument
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
		selections = new ArrayList<>();

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
		start(x, y, true, new String[] {}, new double[][] {});
	}

	public void start(int x, int y, boolean visible)
	{
		start(x, y, visible,new String[] {}, new double[][] {});
	}

	public void start(int x, int y, String[] types, double[][] weights)
	{
		start(x, y, true, types, weights);
	}

	public void start(int x, int y, boolean visible, String[] types, double[][] weights)
	{
		jf.setVisible(visible);
		int fps = 0;
		int saveDelta = 10000;
		int saveIt = 0;
		box=new DelimitationBox(0, 0, x, y);
		this.weights = new HashMap<String, double[]>();
		for (int i = 0 ; i < types.length ; i++)
			this.weights.put(types[i], weights[i]);

		initSelections();
		long timeRefFps = System.nanoTime();
		long timeRefRecount = System.nanoTime();
		if (evolving)
			SaveUtils.prepareSave(fileName);
		while (true)
		{
			if (!SLOW_MO || System.nanoTime() - timeRefFps > TIME_TO_WAIT)
			{
				timeRefFps = System.nanoTime();
				if (!PAUSE)
				{
					sleepAndRefresh();
					fps++;
					if (evolving && (saveIt++ >= saveDelta))
					{
						saveIt = 0;
						SaveUtils.initSave();
						for (Selection s : selections)
							s.saveBest();
						SaveUtils.endSave();
					}
				} else
					try
				{
						Thread.sleep(1);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			if (System.nanoTime() - timeRefRecount > 1000000000)
			{
				timeRefRecount = System.nanoTime();
				if (!visible)
					System.out.println("FPS : " + fps);
				else
					fpsToDraw = fps;
				fps = 0;
			}
		}
	}


	public void generateCsv(int x, int y, boolean visible, String[] types, double[][] weights)
	{
		evolving = false;
		start(x, y, visible, types, weights);
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
	 * Méthodes à implémenter pour personnaliser le monde
	 */

	protected abstract void initSelections();
	protected abstract void generateCollectables();
	protected abstract void generateDelimitations();

	/**
	 * Méthodes de calcul de position des objets
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
		for (int i=0; i<creatures.size() ;i++)
		{
			Creature c = creatures.get(i);
			if (!c.isAlive() || !IntersectionsChecker.contains(box, c))
			{
				if (evolving)
				{
					Individu newBrain = c.getSelection().getOffspring(c.getBrain());
					c.reset(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), newBrain);
				}
				else
				{
					c.reset(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), c.getBrain());
				}
			}
			c.update(evolving);
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
	}


	/**
	 * Actions Override depuis Epreuve
	 */

	@Override
	public void lancerEpreuve(Selection selec, Individu[] population, String type)
	{
		for (Individu i : population)
		{
			i.resetScore();
			creatures.add(CreatureFactory.generate(type, i, selec, this));
		}
	}

	@Override
	public void initSelection(int nbIndiv, int nbGen, String type)
	{
		Selection selection=new Selection(World.this, nbIndiv, nbGen, type);
		selection.population=new Individu[selection.nombreIndividus];
		int[] layersSize = CreatureFactory.getLayersSize(type);
		for (int i=0;i<selection.nombreIndividus;i++)
		{
			double[] wValues;
			if ((wValues = weights.get(type)) == null)
				selection.population[i]=new NeuralNetwork(type,i,layersSize);
			else
				selection.population[i]=new NeuralNetwork(type,i,layersSize,wValues);
		}

		if (!evolving)
		{
			Writer w = CSVUtils.prepareSave(fileName + "_" + type);
			selection.csvOut = w;
		}
		selections.add(selection);
		lancerEpreuve(selection, selection.population, type);
	}

	@Override
	public double fitness(Individu individu)
	{
		return individu.getScore();
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


}
