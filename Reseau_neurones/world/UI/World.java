package UI;

import static utils.Constantes.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

import UI.controls.Controller;
import UI.controls.WorldController;
import collectables.Collectable;
import creatures.Creature;
import creatures.dodgers.ComplexDodger;
import creatures.dodgers.SimpleDodger;
import creatures.insects.Bee;
import creatures.insects.Wasp;
import creatures.shooters.Soldier;
import creatures.shooters.Tank;
import genetics.Epreuve;
import genetics.Individu;
import genetics.Selection;
import interactions.CreaCollecInteraction;
import interactions.CreaCreaInteraction;
import interactions.CreaDelimInteraction;
import interactions.CreaZoneInteraction;
import limitations.Delimitation;
import limitations.DelimitationBox;
import neural_network.NeuralNetwork;
import utils.Draftman;
import utils.IntersectionsChecker;
import zones.Zone;

public abstract class World extends JPanel implements Epreuve
{
	private static final long serialVersionUID = 6616688571724528064L;

	private Controller controller;

	protected List<Delimitation> delimitations;
	protected List<Creature> creatures;
	protected List<Collectable> collectables;
	protected List<Zone> zones;
	protected DelimitationBox box;
	protected int collectableAmount;
	protected int meatCount,vegetableCount, powerUpCount, fuelCount;
	protected int generationCount=1;

	private long timeStartCompute;

	private boolean done;
	private int refreshCount;

	private double[] fpsHistory = new double[500];
	private int fpsCount=0;

	private boolean sleepAndRefreshStop = true;

	private Creature selectedCreature;

	public World()
	{
		resetAllConstantes();
		controller = new WorldController(this);
		addMouseListener(controller);
		addKeyListener(controller);
		addMouseWheelListener(controller);
		addMouseMotionListener(controller);
		setFocusable(true);

		this.setSize(new Dimension(100000, 100000));
		creatures = new ArrayList<>(1000);
		collectables = new ArrayList<>(1000);
		delimitations = new ArrayList<>(1000);
		zones = new ArrayList<>(1000);
	}

	/**
	 * Démarre la sélection
	 */

	public void start(int x, int y)
	{

		box=new DelimitationBox(0, 0, x, y);
		for (Delimitation delim : box.getWalls())
			delimitations.add(delim);
		initSelections();
		initComputeThread();
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

	private boolean sleepAndRefresh()
	{
		timeStartCompute = System.currentTimeMillis();
		if (sleepAndRefreshStop)
			return false;
		if (PAUSE)
			return false;

		refreshCount++;
		if (creatures.isEmpty() || refreshCount >= GENERATION_LENGTH)
		{
			sleepAndRefreshStop=true;
			finTest();
			return false;
		}

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

		for (int i=0;i<zones.size();i++)
		{
			Zone z = zones.get(i);
			if (z.isExpired() || !IntersectionsChecker.contains(box, z))
				zones.remove(i--);
			else
				z.update();
		}

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
				creatures.remove(i--);
				continue;
			}
			creature1.detect();
			creature1.update();
			for (int j=0;j<collectables.size();j++)
			{
				Collectable collect = collectables.get(j);
				if (collect!=null)
					if (!collect.isConsumed())
						if (IntersectionsChecker.intersects(creature1,collect))
							new CreaCollecInteraction(creature1,collect).process();
			}
			for (int j=0;j<delimitations.size();j++)
			{
				Delimitation delim = delimitations.get(j);
				if (delim!=null)
					if (!delim.isExpired())
						if (IntersectionsChecker.intersects(creature1,delim))
							new CreaDelimInteraction(creature1,delim).process();
			}
			for (int j=0;j<zones.size();j++)
			{
				Zone z = zones.get(j);
				if (z!=null)
					if (!z.isExpired())
						if (IntersectionsChecker.intersects(creature1,z))
							new CreaZoneInteraction(creature1,z).process();
			}
			for (int j=i+1; j<creatures.size() ;j++)
			{
				Creature creature2 = creatures.get(j);
				if (creature2!=null)
					if (creature2.isAlive())
						if (IntersectionsChecker.intersects(creature1,creature2))
							new CreaCreaInteraction(creature1,creature2).process();
			}
		}


		long timeToWait = SLOW_MO_TIME-(System.currentTimeMillis()-timeStartCompute);
		if (SLOW_MO_MODE && timeToWait>0)
		{
			try
			{
				Thread.sleep(timeToWait);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}


		fpsHistory[fpsCount]=System.currentTimeMillis()-timeStartCompute;
		fpsCount++;
		if (fpsCount==fpsHistory.length)
			fpsCount=0;

		return true;
	}

	private void initComputeThread()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					if (!sleepAndRefresh())
					{
						try
						{
							Thread.sleep(1);

						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}			
				}
			}
		}).start();
	}

	private int currentFrameRate()
	{
		double average=0;
		for (int i=0;i<fpsHistory.length;i++)
			average+=fpsHistory[i];
		average/=fpsHistory.length;
		if (average==0)
			return Integer.MAX_VALUE;
		return (int)(1000/average);
	}

	/**
	 * Dessinateur
	 */

	public void paint(Graphics g)
	{
		super.getRootPane().updateUI();

		Draftman draftman = new Draftman();
		if (DRAW_ALL && !sleepAndRefreshStop)
		{
			draftman.drawWorld(selectedCreature, creatures, collectables, delimitations, g);
			draftman.drawFPS(currentFrameRate(), g);
		}
		draftman.drawInfos(infosToPrint(), g);
		draftman.drawAvancement(refreshCount, GENERATION_LENGTH, g);
	}

	private List<String> infosToPrint()
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

	/**
	 * Générateurs de créatures
	 */

	public void generateBee(Individu intelligence)
	{
		creatures.add(new Bee(3+new Random().nextDouble()*(box.getWidth()/3), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence,
				creatures,collectables,delimitations, box));
	}

	public void generateWasp(Individu intelligence)
	{
		creatures.add(new Wasp(box.getWidth()*2/3+new Random().nextDouble()*(box.getWidth()/3-3), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence,
				creatures,collectables,delimitations, box));
	}

	public void generateSoldier(Individu intelligence)
	{
		creatures.add(new Soldier(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence,
				creatures,collectables,delimitations, box));
	}

	public void generateTank(Individu intelligence)
	{
		creatures.add(new Tank(3+new Random().nextDouble()*(box.getWidth()-6), 3+new Random().nextDouble()*(box.getHeight()-6), intelligence,
				creatures,collectables,delimitations, box));
	}

	public void generateComplexDodger(Individu intelligence)
	{
		creatures.add(new ComplexDodger(box.getWidth()/2, box.getHeight()/2, intelligence,
				creatures,collectables,delimitations, box));
	}

	public void generateSimpleDodger(Individu intelligence)
	{
		creatures.add(new SimpleDodger(box.getWidth()/2, box.getHeight()/2, intelligence,
				creatures,collectables,delimitations, box));
	}


	/**
	 * Actions Override depuis Epreuve
	 */

	@Override
	public synchronized void lancerEpreuve(Individu[] population, String type)
	{
		for (Individu i : population)
		{
			i.resetScore();
			switch (type)
			{
			case TYPE_WASP:
				generateWasp(i);
				break;
			case TYPE_BEE:
				generateBee(i);
				break;
			case TYPE_TANK:
				generateTank(i);
				break;
			case TYPE_SOLDIER:
				generateSoldier(i);
				break;
			case TYPE_COMPLEXDODGER:
				generateComplexDodger(i);
				break;
			case TYPE_SIMPLEDODGER:
				generateSimpleDodger(i);
				break;
			default:
				System.out.println("World.lancerEpreuve : Type non défini");
				System.exit(0);
				break;
			}
		}
		sleepAndRefreshStop=false;
		done=false;
		while (!done)
		{
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initSelection(int nbIndiv, int nbGen, String type)
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
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
				default:
					System.out.println("World.initSelection - Type inconnu");
					System.exit(0);
				}
				for (int i=0;i<selection.nombreIndividus;i++)
					selection.population[i]=new NeuralNetwork(type,layersSize);
				Individu leDieu=selection.lancerSelection();
				System.out.println(leDieu);
			}
		}).start();

	}

	@Override
	public double fitness(Individu individu)
	{
		return individu.getScore();
	}

	public synchronized void finTest()
	{
		refreshCount = 0;
		for (int i = 0 ; i < delimitations.size() ; i ++)
		{
			Delimitation d = delimitations.get(i);
			if (d!=null)
				if (d.getType()!=WALL)
				{
					delimitations.remove(i);
					i--;
				}
		}
		collectables.clear();
		done=true;
		generationCount++;
		notifyAll();
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

	public void changeSlowMo()
	{
		SLOW_MO_MODE=!SLOW_MO_MODE;
	}

	public void changeShowAll()
	{
		DRAW_ALL=!DRAW_ALL;
	}

	public void genocide()
	{
		sleepAndRefreshStop=true;
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		creatures.clear();
		sleepAndRefreshStop=false;
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
