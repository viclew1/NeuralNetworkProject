package cars;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

import UI.controls.Controller;
import genetics.Epreuve;
import genetics.Individu;
import genetics.Selection;
import neural_network.NeuralNetwork;
import utils.SaveUtils;

import static utils.Constantes.*;

public class Environnement implements Epreuve
{

	private final String name = "NEURAL NETWORK PROJECT";

	private final String fileName;

	private JFrame jf;
	private JPanel jp;

	private int fpsToDraw = 0;

	private List<Selection> selections;


	protected int collectableAmount;
	protected int meatCount,vegetableCount, powerUpCount, fuelCount, bombCount;

	private Car selectedCar;
	private List<Car> cars;

	public Line2D[] lines;
	private final int sz = 16;

	public double xStart = 2.3153687 * sz;
	public double yStart = 3.4860064 * sz;

	private double[] weights; 

	public Environnement()
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		init((int)(d.getWidth()), (int)(d.getHeight()));
		this.fileName = "CARS_PROJECT";
	}

	/**
	 * Initialise l'environnement en fonction de la largeur / hauteur passés en argument
	 * @param w largeur
	 * @param h hauteur
	 */
	@SuppressWarnings("serial")
	private void init(int w, int h)
	{
		cars = new ArrayList<>(1000);
		selections = new ArrayList<>();
		initLines();

		jf = new JFrame(name);
		jf.setSize(w, h);
		jp = new JPanel() 
		{

			public void paint(Graphics g)
			{
				super.getRootPane().updateUI();
				drawAll(infosToPrint(), g);
				g.drawString("FPS : " + fpsToDraw, 10, 15);
			}

		};
		jf.add(jp);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Controller ctrl = new CarUIController(this);
		jp.addMouseListener(ctrl);
		jp.addMouseMotionListener(ctrl);
		jp.addKeyListener(ctrl);
		jp.addMouseWheelListener(ctrl);
		jp.setFocusable(true);

	}

	private void initLines()
	{
		double[][] coordinates = new double[][]{
			{-1.6136169,-3.4222644},
			{-1.5662384,-3.4983431},
			{-0.9338379,-3.4147248},
			{-0.5053711,-2.932069},
			{-0.5932617,-2.3833456},
			{-0.9008789,-1.8344033},
			{-0.703125,-1.6037944},
			{-0.4284668,-1.7026302},
			{-0.1538086,-1.9442069},
			{0.2526855,-1.9112665},
			{0.6262207,-0.7470491},
			{-0.2307129,-0.549308},
			{-0.8898926,1.2193904},
			{-0.703125,1.2303742},
			{-0.4394531,0.6591651},
			{0.4833984,0.4724068},
			{0.736084,0.7580345},
			{0.8129883,1.1315182},
			{0.637207,1.3292265},
			{0.670166,1.6367402},
			{0.7800293,1.9881263},
			{1.0656738,2.2516175},
			{1.7907715,2.9430409},
			{2.5817871,3.4256915},
			{2.4060059,3.7765593},
			{1.8786621,3.3818237},
			{1.1645508,2.921097},
			{0.5822754,2.3394376},
			{0.2087402,1.7136117},
			{0.2087402,1.4280748},
			{0.3955078,1.0985655},
			{0.2197266,0.8678867},
			{0.0109863,0.9283041},
			{-0.1977539,1.0106898},
			{-0.4174805,1.3731595},
			{-0.637207,1.8014609},
			{-1.1975098,1.7136117},
			{-1.2634277,1.0765968},
			{-0.670166,-0.549308},
			{0.1538086,-1.1315182},
			{-0.0439453,-1.5598659},
			{-0.3735352,-1.2193904},
			{-0.8679199,-1.1315182},
			{-1.2963867,-1.472006},
			{-1.505127,-1.9551868},
			{-1.2414551,-2.2296616},
			{-1.0876465,-2.800398},
			{-1.4172363,-3.00887},
			{-1.6136169,-3.4222644}
		};
		for (int j = 0 ; j < coordinates.length ; j++)
		{
			double[] darray = coordinates[j];
			for (int i = 0 ; i < darray.length ; i++)
			{
				darray[i] *= sz;
			}
		}
		lines = new Line2D[coordinates.length];
		for (int i = 0 ; i < coordinates.length - 1 ; i++)
		{
			double[] pair = coordinates[i];
			double[] nextPair = coordinates[i+1];
			lines[i] = new Line2D.Double(pair[0], pair[1], nextPair[0], nextPair[1]);
		}
		double[] pair = coordinates[coordinates.length - 1];
		double[] nextPair = coordinates[0];
		lines[coordinates.length - 1] = new Line2D.Double(pair[0], pair[1], nextPair[0], nextPair[1]);

	}

	private void drawAll(List<String> infos, Graphics g)
	{

		for (int i = 0 ; i < lines.length ; i++)
		{
			Line2D line = lines[i];
			int x1 = (int)(line.getX1() * SIZE + SCROLL_X);
			int y1 = (int)(line.getY1() * SIZE + SCROLL_Y);
			int x2 = (int)(line.getX2() * SIZE + SCROLL_X);
			int y2 = (int)(line.getY2() * SIZE + SCROLL_Y);
			g.drawLine(x1, y1, x2, y2);
			if (i<25)
				g.drawRect(x1, y1, (int)(0.6 * SIZE), (int)(0.6 * SIZE));
		}
		for (int i = 0 ; i < cars.size() ; i++)
		{
			Car c = cars.get(i);
			c.draw(g, selectedCar == c);
		}
	}

	public void start(int popSize)
	{
		start(popSize, true, new double[] {});
	}

	public void start(int popSize, boolean visible)
	{
		start(popSize, visible, new double[] {});
	}

	public void start(int popSize, boolean visible, double[] weights)
	{
		jf.setVisible(visible);
		int fps = 0;

		int saveDelta = 10000;
		int saveIt = 0;

		SaveUtils.prepareSave(fileName);
		initSelections(popSize, weights);
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
					if ((saveIt++ >= saveDelta))
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

	protected void initSelections(int popSize, double[] weights)
	{
		if (weights!= null && weights.length!=0)
			this.weights = weights;
		initSelection(popSize, 1000, "CARS");
	}

	/**
	 * Méthodes de calcul de position des objets
	 */

	private void sleepAndRefresh()
	{
		updateCars();
	}

	private void updateCars()
	{
		for (int i=0; i<cars.size() ;i++)
		{
			Car c = cars.get(i);
			boolean ok = true;

			for (Line2D line : lines)
			{
				if (c.around.intersectsLine(line))
					for (Line2D side : c.sides)
						if (line.intersectsLine(side))
						{
							ok = false;
							break;
						}
			}

			if (!c.isAlive() || !ok)
			{
				Individu newBrain = c.getSelection().getOffspring(c.getBrain());
				c.reset(xStart, yStart, newBrain);
			}
			c.update();
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
			cars.add(new Car(xStart, yStart, i, selec, this));
		}
	}

	@Override
	public void initSelection(int nbIndiv, int nbGen, String type)
	{
		Selection selection=new Selection(Environnement.this, nbIndiv, nbGen, type);
		selection.population=new Individu[selection.nombreIndividus];
		int[] layersSize = new int[] {7, 13, 2};
		for (int i=0;i<selection.nombreIndividus;i++)
		{
			if (weights == null)
				selection.population[i]=new NeuralNetwork(type,i,layersSize);
			else
				selection.population[i]=new NeuralNetwork(type,i,layersSize,weights);
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

}
