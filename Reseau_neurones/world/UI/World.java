package UI;

import static utils.Constantes.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
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
import creatures.chargers.Rhinoceros;
import creatures.dodgers.ComplexDodger;
import creatures.dodgers.SimpleDodger;
import creatures.insects.Bee;
import creatures.insects.Wasp;
import creatures.shooters.Hedgehog;
import creatures.shooters.Soldier;
import creatures.shooters.Tank;
import creatures.slugs.Slug;
import genetics.Epreuve;
import genetics.Individu;
import genetics.Selection;
import genetics.Utils;
import interactions.CreaCollecInteraction;
import interactions.CreaCreaInteraction;
import interactions.CreaDelimInteraction;
import limitations.Delimitation;
import limitations.DelimitationBox;
import neural_network.NeuralNetwork;
import utils.Draftman;
import utils.IntersectionsChecker;
import zones.Zone;

public abstract class World implements Epreuve
{

	private final String name;

	private Map<String, double[]> weights;

	private JFrame jf;
	private JPanel jp;

	private int fpsToDraw = 0;

	private List<Selection> selections;

	protected List<Delimitation> delimitations;
	protected List<Creature> creatures;
	protected List<Collectable> collectables;
	protected List<Zone> zones;
	protected DelimitationBox box;
	protected int collectableAmount;
	protected int meatCount,vegetableCount, powerUpCount, fuelCount, bombCount;

	private Creature selectedCreature;


	/**
	 * Crée un environnement avec une image de la taille de l'écran
	 */
	public World(String name)
	{
		this.name = name;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		init((int)(d.getWidth()), (int)(d.getHeight()));
	}

	/**
	 * Crée un environnement avec une image de la taille de la dimension passée en argument
	 * @param dimension Dimension de l'image
	 */
	public World(String name, Dimension dimension)
	{
		this.name = name;
		init((int)(dimension.getWidth()), (int)(dimension.getHeight()));
	}

	/**
	 * Crée un environnement avec pour largeur et hauteur d'image les arguments passés
	 * @param w largeur de l'image
	 * @param h hauteur de l'image
	 */
	public World(String name, int w, int h)
	{
		this.name = name;
		init(w,h);
	}

	/**
	 * Initialise l'image en fonction de la largeur / hauteur passés en argument
	 * Initialise les Kernels utilsés pour le calcul et le dessin
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
		while (true)
		{
			if (!SLOW_MO || System.nanoTime() - timeRefFps > TIME_TO_WAIT)
			{
				timeRefFps = System.nanoTime();
				if (!PAUSE)
				{
					sleepAndRefresh();
					fps++;
					if (saveIt++ >= saveDelta)
					{
						saveIt = 0;
						Utils.initSave();
						for (Selection s : selections)
							s.saveBest();
						Utils.endSave();
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
		meatCount=0;
		vegetableCount=0;
		fuelCount=0;
		powerUpCount=0;

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

		/*for (int i=0;i<zones.size();i++)
		{
			Zone z = zones.get(i);
			if (z.isExpired() || !IntersectionsChecker.contains(box, z))
				zones.remove(i--);
			else
				z.update();
		}*/

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

		generateDelimitations();

		for (int i=0; i<creatures.size() ;i++)
		{
			Creature creature1 = creatures.get(i);
			if (creature1==null || !creature1.isAlive() || !IntersectionsChecker.contains(box, creature1))
			{
				creature1.reset(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6));
			}
			creature1.update();
			creature1.detect();
			for (int j=0;j<collectables.size();j++)
			{
				Collectable collect = collectables.get(j);
				if (collect!=null)
					if (!collect.isConsumed())
						if (IntersectionsChecker.preciseIntersects(creature1,collect))
							new CreaCollecInteraction(creature1,collect).process();
			}
			for (int j=0;j<delimitations.size();j++)
			{
				Delimitation delim = delimitations.get(j);
				if (delim!=null)
					if (!delim.isExpired())
						if (IntersectionsChecker.preciseIntersects(creature1,delim))
							new CreaDelimInteraction(creature1,delim).process();
			}
			/*for (int j=0;j<zones.size();j++)
			{
				Zone z = zones.get(j);
				if (z!=null)
					if (!z.isExpired())
						if (IntersectionsChecker.intersects(creature1,z))
							new CreaZoneInteraction(creature1,z).process();
			}*/
			for (int j=i+1; j<creatures.size() ;j++)
			{
				Creature creature2 = creatures.get(j);
				if (creature2!=null)
					if (creature2.isAlive())
						if (IntersectionsChecker.preciseIntersects(creature1,creature2))
							new CreaCreaInteraction(creature1,creature2).process();
			}
		}
	}

	/**
	 * Générateurs de créatures
	 */

	public void generateBee(Individu intelligence, Selection selec)
	{
		creatures.add(new Bee(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence, selec,
				creatures,collectables,delimitations, box));
	}

	public void generateWasp(Individu intelligence, Selection selec)
	{
		creatures.add(new Wasp(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence, selec,
				creatures,collectables,delimitations, box));
	}

	public void generateSoldier(Individu intelligence, Selection selec)
	{
		creatures.add(new Soldier(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence, selec,
				creatures,collectables,delimitations, box));
	}

	public void generateTank(Individu intelligence, Selection selec)
	{
		creatures.add(new Tank(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence, selec,
				creatures,collectables,delimitations, box));
	}

	public void generateComplexDodger(Individu intelligence, Selection selec)
	{
		creatures.add(new ComplexDodger(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence, selec,
				creatures,collectables,delimitations, box));
	}

	public void generateSimpleDodger(Individu intelligence, Selection selec)
	{
		creatures.add(new SimpleDodger(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence, selec,
				creatures,collectables,delimitations, box));
	}

	public void generateSlug(Individu intelligence, Selection selec)
	{
		creatures.add(new Slug(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence, selec,
				creatures,collectables,delimitations, box));
	}

	public void generateHedgehog(Individu intelligence, Selection selec)
	{
		creatures.add(new Hedgehog(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence, selec,
				creatures,collectables,delimitations, box));
	}
	
	public void generateRhinoceros(Individu intelligence, Selection selec)
	{
		creatures.add(new Rhinoceros(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence, selec,
				creatures,collectables,delimitations, box));
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
			switch (type)
			{
			case TYPE_WASP:
				generateWasp(i, selec);
				break;
			case TYPE_BEE:
				generateBee(i, selec);
				break;
			case TYPE_TANK:
				generateTank(i, selec);
				break;
			case TYPE_SOLDIER:
				generateSoldier(i, selec);
				break;
			case TYPE_COMPLEXDODGER:
				generateComplexDodger(i, selec);
				break;
			case TYPE_SIMPLEDODGER:
				generateSimpleDodger(i, selec);
				break;
			case TYPE_SLUG:
				generateSlug(i, selec);
				break;
			case TYPE_HEDGEHOG:
				generateHedgehog(i, selec);
				break;
			case TYPE_RHINOCEROS:
				generateRhinoceros(i, selec);
				break;
			default:
				System.out.println("World.lancerEpreuve : Type non défini");
				System.exit(0);
				break;
			}
		}
	}

	@Override
	public void initSelection(int nbIndiv, int nbGen, String type)
	{
		Selection selection=new Selection(World.this, nbIndiv, nbGen, type);
		selection.population=new Individu[selection.nombreIndividus];
		int[] layersSize = null;
		switch (type)
		{
		case TYPE_BEE:
			layersSize=LAYERS_SIZES_BEE;
			break;
		case TYPE_TANK:
			layersSize=LAYERS_SIZES_TANK;
			break;
		case TYPE_WASP:
			layersSize=LAYERS_SIZES_WASP;
			break;
		case TYPE_SOLDIER:
			layersSize=LAYERS_SIZES_SOLDIER;
			break;
		case TYPE_COMPLEXDODGER:
			layersSize=LAYERS_SIZES_COMPLEXDODGER;
			break;
		case TYPE_SIMPLEDODGER:
			layersSize=LAYERS_SIZES_SIMPLEDODGER;
			break;
		case TYPE_SLUG:
			layersSize=LAYERS_SIZES_SLUG;
			break;
		case TYPE_HEDGEHOG:
			layersSize=LAYERS_SIZES_HEDGEHOG;
			break;
		case TYPE_RHINOCEROS:
			layersSize=LAYERS_SIZES_RHINOCEROS;
			break;
		default:
			System.out.println("World.initSelection - Type inconnu");
			System.exit(0);
		}
		for (int i=0;i<selection.nombreIndividus;i++)
		{
			double[] wValues;
			if ((wValues = weights.get(type)) == null)
				selection.population[i]=new NeuralNetwork(type,i,layersSize);
			else
				selection.population[i]=new NeuralNetwork(type,i,layersSize,wValues);
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
			if (c_x>realX) continue;
			if (c_y>realY) continue;
			if (c_x+c_sz<realX) continue;
			if (c_y+c_sz<realY) continue;
			selectedCreature=c;
			return;
		}
		selectedCreature=null;
	}


}
