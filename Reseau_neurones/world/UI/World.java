package UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import UI.controls.Controller;
import UI.controls.WorldController;
import collectables.Collectable;
import collectables.Meat;
import collectables.Vegetable;
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

import static utils.Constantes.*;

public class World extends JPanel implements Epreuve
{
	private static final long serialVersionUID = 6616688571724528064L;

	private final long SLOW_MO_TIME = 1000/60;
	private Controller controller;

	private List<Delimitation> delimitations;
	private List<Creature> creatures;
	private List<Collectable> collectables;
	private DelimitationBox box;

	private long timeStartCompute;
	private int generationCount=1;

	private boolean done;

	private int meatCount,vegetableCount;

	private double[] fpsHistory = new double[500];
	private int fpsCount=0;

	private boolean sleepAndRefreshStop;


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
	}

	public void start(int x, int y)
	{
		box=new DelimitationBox(0, 0, x, y);
		delimitations.add(box);
		initSelection(POPULATION_SIZE_BEE, GENERATION_COUNT, TYPE_BEE);
		initSelection(POPULATION_SIZE_WASP, GENERATION_COUNT, TYPE_WASP);
		initComputeThread();
	}

	private void sleepAndRefresh()
	{
		long timeStart=System.currentTimeMillis();
		if (sleepAndRefreshStop)
			return;

		if (PAUSE)
			return;

		if (creatures.isEmpty())
		{
			sleepAndRefreshStop=true;
			finTest();
			return;
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
			generateFood();

		for (int i=0; i<creatures.size() ;i++)
		{
			Creature creature1 = creatures.get(i);
			creature1.detect(creatures, collectables, delimitations);
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
		
		long timeToWait = SLOW_MO_TIME-(System.currentTimeMillis()-timeStart);
		if (SLOW_MO_MODE && timeToWait>0)
			try
		{
				Thread.sleep(timeToWait);
		} catch (InterruptedException e)
		{
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
					sleepAndRefresh();
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

	public void paint(Graphics g)
	{
		timeStartCompute = System.currentTimeMillis();
		super.getRootPane().updateUI();
		if (DRAW_ALL)
			draw(g);

		printInfos(g);

		fpsHistory[fpsCount]=System.currentTimeMillis()-timeStartCompute;
		fpsCount++;
		if (fpsCount==fpsHistory.length)
			fpsCount=0;
	}

	private void draw(Graphics g)
	{
		if (sleepAndRefreshStop)
		{
			return;
		}
		Draftman draftman = new Draftman();

		try
		{
			for (int i=0;i<collectables.size();i++)
				draftman.drawCollectable(collectables.get(i), g);

			for (int i=0;i<creatures.size();i++)
			{
				Creature c = creatures.get(i);
				boolean selected = (selectedCreature==c);
				draftman.drawCreature(c,selected, g);
			}

			for (int i=0;i<delimitations.size();i++)
				draftman.drawDelimitation(delimitations.get(i), g);

			if (selectedCreature!=null && selectedCreature.isAlive())
				selectedCreature.drawBrain(g);
		}
		catch(Exception e) {}

	}

	private void printInfos(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		g2.drawString("Génération : "+generationCount, 10, 15);
		g2.drawString((SLOW_MO_MODE?"Désactiver":"Activer")+" slow motion : V",10,30);
		g2.drawString((DRAW_CAPTORS?"Cacher":"Afficher")+" capteurs : S",10,45);
		g2.drawString((DRAW_HP?"Cacher":"Afficher")+" points de vie : H",10,60);
		g2.drawString((DRAW_ALL?"Cacher":"Afficher")+" la simulation : G",10,75);
		g2.drawString((!PAUSE?"Mettre en pause":"Quitter la pause")+" : SPACE",10,90);
		g2.drawString("Tuer cette génération entière : K",10,105);

		g2.drawString("FPS : "+ currentFrameRate(), 10, 400);
	}

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
				for (int i=0;i<selection.nombreIndividus;i++)
					selection.population[i]=new NeuralNetwork(type,INPUT_COUNT, HIDDEN_COUNT, OUTPUT_COUNT);
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

	public void generateBee(Individu intelligence)
	{
		creatures.add(new Bee(3+new Random().nextDouble()*(box.getW()/3), 3+new Random().nextDouble()*(box.getH()-6), intelligence));
	}

	public void generateFood()
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

	public void generateWasp(Individu intelligence)
	{
		creatures.add(new Wasp(box.getW()*2/3+new Random().nextDouble()*(box.getW()/3-3), 3+new Random().nextDouble()*(box.getH()-6), intelligence));
	}

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

	public synchronized void finTest()
	{
		collectables.clear();
		done=true;
		generationCount++;
		notifyAll();
	}
}
