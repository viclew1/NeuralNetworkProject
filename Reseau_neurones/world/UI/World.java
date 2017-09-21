package UI;

import static utils.Constantes.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import UI.controls.Controller;
import UI.controls.WorldController;
import collectables.Collectable;
import creatures.Bee;
import creatures.Creature;
import creatures.Wasp;
import genetics.Epreuve;
import genetics.Individu;
import genetics.Selection;
import interactions.CreaCollecInteraction;
import interactions.CreaCreaInteraction;
import interactions.CreaDelimInteraction;
import limitations.Delimitation;
import limitations.DelimitationBox;
import neural_network.NeuralNetwork;
import utils.Draftman;
import utils.IntersectionsChecker;

public abstract class World extends JPanel implements Epreuve
{
	private static final long serialVersionUID = 6616688571724528064L;

	private final long SLOW_MO_TIME = 1000/60;
	private Controller controller;

	protected List<Delimitation> delimitations;
	protected List<Creature> creatures;
	protected List<Collectable> collectables;
	protected DelimitationBox box;
	protected int meatCount,vegetableCount;
	protected int generationCount=1;

	private long timeStartCompute;

	private boolean done;

	private double[] fpsHistory = new double[500];
	private int fpsCount=0;

	private boolean sleepAndRefreshStop;
	private Thread[] sleepAndRefreshThreads;
	private ExecutorService es;

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
		sleepAndRefreshThreads = new Thread[10];
	}

	/**
	 * Démarre la sélection
	 */

	public void start(int x, int y)
	{

		box=new DelimitationBox(0, 0, x, y);
		delimitations.add(box);
		initSelections();
		initComputeThread();
	}

	/**
	 * Méthodes à implémenter pour personnaliser le monde
	 */

	protected abstract void initSelections();
	protected abstract void generateCollectables();
	protected abstract List<String> infosToPrint();


	/**
	 * Méthodes de calcul de position des objets
	 */

	private void initSleepAndRefreshThread(final int indexThread, final int indexStart, final int indexEnd)
	{
		sleepAndRefreshThreads[indexThread] = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				for (int i=indexStart; i<indexEnd ;i++)
				{
					Creature creature1 = creatures.get(i);
					creature1.detect();
					creature1.update();
					for (int j=0;j<collectables.size();j++)
					{
						Collectable collect = collectables.get(j);
						if (IntersectionsChecker.intersects(creature1,collect))
							new CreaCollecInteraction(creature1,collect).process();;
					}
					for (int j=0;j<delimitations.size();j++)
					{
						Delimitation delim = delimitations.get(j);
						if (!delim.isEmptyInside())
						{
							if (IntersectionsChecker.intersects(creature1,delim))
								new CreaDelimInteraction(creature1,delim).process();;
						}
						else
						{
							if (!IntersectionsChecker.contains(delim,creature1))
								new CreaDelimInteraction(creature1,delim).process();
						}
					}
					for (int j=i+1; j<creatures.size() ;j++)
					{
						Creature creature2 = creatures.get(j);
						if (IntersectionsChecker.intersects(creature1,creature2))
							new CreaCreaInteraction(creature1,creature2).process();;
					}
					if (!IntersectionsChecker.contains(box,creature1))
						new CreaDelimInteraction(creature1,box).process();;
				}
			}
		});
	}

	private boolean sleepAndRefresh()
	{
		long timeStart=System.currentTimeMillis();
		if (sleepAndRefreshStop)
			return false;
		if (PAUSE)
			return false;

		if (creatures.isEmpty())
		{
			sleepAndRefreshStop=true;
			finTest();
			return false;
		}

		meatCount=0;
		vegetableCount=0;

		for (int i=0; i<creatures.size() ;i++)
		{
			Creature creature1 = creatures.get(i);
			if (!creature1.isAlive())
			{
				creatures.remove(creature1);
				i--;
			}
		}

		for (int i=0;i<delimitations.size();i++)
		{
			Delimitation delim = delimitations.get(i);
			delim.update();
			/**
			 * TODO
			 * Retirer de la liste si expiré
			 */
		}

		for (int i=0;i<collectables.size();i++)
		{
			Collectable c = collectables.get(i);
			if (c.isConsumed())
			{
				collectables.remove(c);
				i--;
			}
			else
			{
				if (c.getType()==MEAT)
					meatCount++;
				else if (c.getType()==VEGETABLE)
					vegetableCount++;
			}
		}

		for (int i=collectables.size();i<FOOD_AMOUNT;i++)
			generateCollectables();

		startSleepAndRefreshMultiThreads();


		long timeToWait = SLOW_MO_TIME-(System.currentTimeMillis()-timeStart);
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
		return true;
	}

	private void startSleepAndRefreshMultiThreads()
	{
		int nbCrea = creatures.size();
		int nbThreads = 0;

		if (nbCrea<10)
		{
			for (int i=0; i<nbCrea;i++)
				initSleepAndRefreshThread(i, i, i+1);
			nbThreads = nbCrea;
		}
		else
		{
			nbThreads=10;
			int indexStart = 0;
			int indexStop = 0;
			double sum = 0;
			for (int i=0; i<9;i++)
			{
				indexStart= (int) sum;
				sum+=(double)nbCrea/10;
				indexStop = (int) (sum);
				initSleepAndRefreshThread(i, indexStart, indexStop);
			}
			indexStart = indexStop;
			indexStop = nbCrea;
			initSleepAndRefreshThread(9, indexStart, indexStop);
		}

		es = Executors.newCachedThreadPool();

		for (int i=0;i<nbThreads;i++) {
			es.execute(sleepAndRefreshThreads[i]);
		}
		es.shutdown();
		try {
			es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		timeStartCompute = System.currentTimeMillis();
		super.getRootPane().updateUI();

		Draftman draftman = new Draftman();
		if (DRAW_ALL && !sleepAndRefreshStop)
		{
			draftman.drawWorld(selectedCreature, creatures, collectables, delimitations, g);
			draftman.drawFPS(currentFrameRate(), g);
		}
		draftman.drawInfos(infosToPrint(), g);

		fpsHistory[fpsCount]=System.currentTimeMillis()-timeStartCompute;
		fpsCount++;
		if (fpsCount==fpsHistory.length)
			fpsCount=0;
	}

	/**
	 * Générateurs de créatures
	 */

	public void generateBee(Individu intelligence)
	{
		creatures.add(new Bee(3+new Random().nextDouble()*(box.getW()/3), 3+new Random().nextDouble()*(box.getH()-6), intelligence,
				creatures,collectables,delimitations));
	}

	public void generateWasp(Individu intelligence)
	{
		creatures.add(new Wasp(box.getW()*2/3+new Random().nextDouble()*(box.getW()/3-3), 3+new Random().nextDouble()*(box.getH()-6), intelligence,
				creatures,collectables,delimitations));
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
			default:
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
				int inputCpt,hiddenCpt,outputCpt;
				switch (type)
				{
				case TYPE_BEE:
					inputCpt=INPUT_COUNT_BEE;
					hiddenCpt=HIDDEN_COUNT_BEE;
					outputCpt=OUTPUT_COUNT_BEE;
					break;
				case TYPE_TANK:
					inputCpt=INPUT_COUNT_TANK;
					hiddenCpt=HIDDEN_COUNT_TANK;
					outputCpt=OUTPUT_COUNT_TANK;
					break;
				case TYPE_WASP:
					inputCpt=INPUT_COUNT_WASP;
					hiddenCpt=HIDDEN_COUNT_WASP;
					outputCpt=OUTPUT_COUNT_WASP;
					break;
				default:
					inputCpt=-1;
					hiddenCpt=-1;
					outputCpt=-1;
				}
				for (int i=0;i<selection.nombreIndividus;i++)
					selection.population[i]=new NeuralNetwork(type,inputCpt, hiddenCpt, outputCpt);
				Individu leDieu=selection.lancerSelection();
				System.out.println(leDieu);
			}
		}).start();

	}

	@Override
	public double fitness(Individu individu)
	{
		return individu.score();
	}

	public synchronized void finTest()
	{
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
		creatures.clear();
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
