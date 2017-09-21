package genetics;
import java.util.Random;


public class Selection
{
	public Epreuve epreuve;
	public int nombreIndividus;
	public int nombreGenerations;
	public Individu[] population;
	public Individu meilleurTrouvé;
	public double meilleureFitness=0;
	public String type;

	public Selection(Epreuve epreuve, int nombreIndividus, int nombreGenerations, String type)
	{
		this.epreuve=epreuve;
		this.nombreIndividus=nombreIndividus;
		this.nombreGenerations=nombreGenerations;
		this.type=type;
	}

	boolean found=false;

	public Individu lancerSelection()
	{
		//int cpt=1;
		for (int i=0;i<nombreGenerations;i++)
		{
			//System.out.println("------ DEBUT GENERATION "+(i+1)+" ------");
			championnat();
			if (found)
				return meilleurIndividu();
			double meilleurScore = epreuve.fitness(meilleurIndividu());
			//System.out.println("Meilleur individu : "+meilleurScore);
			if (meilleurScore>meilleureFitness)
			{
				meilleureFitness=meilleurScore;
				meilleurTrouvé=meilleurIndividu().deepCopy();
				System.out.println("Génération : "+(i+1));
				System.out.println("Meilleure trouvaille : "+meilleurTrouvé+"\nFitness : "+meilleureFitness);
			}
		}

		return meilleurIndividu();
	}

	public void championnat()
	{
		epreuve.lancerEpreuve(population,type);

		for (int i = 0; i < population.length ; i++)
		{
			duel(i);
		}
		
	}

	public void duel(int a)
	{
		int b=new Random().nextInt(nombreIndividus);
		while (a==b)
		{
			b=new Random().nextInt(nombreIndividus);
		}

		Individu win,lose;

		double fitA=epreuve.fitness(population[a]);
		double fitB=epreuve.fitness(population[b]);

		if (fitA>fitB)
		{
			win=population[a];
			lose=population[b];
		}
		else
		{
			win=population[b];
			lose=population[a];
		}

		int rdm=new Random().nextInt(100);
		if (rdm>=100-Const.chancesCrossOver)
		{
			lose.crossOver(win);
		}
		if (rdm>=100-Const.chancesMutation)
		{
			lose.mutate();
		}
	}

	public void duel()
	{
		int a=new Random().nextInt(nombreIndividus);
		int b=new Random().nextInt(nombreIndividus);
		while (a==b)
		{
			b=new Random().nextInt(nombreIndividus);
		}

		Individu win,lose;

		double fitA=epreuve.fitness(population[a]);
		double fitB=epreuve.fitness(population[b]);

		if (fitA>fitB)
		{
			win=population[a];
			lose=population[b];
		}
		else
		{
			win=population[b];
			lose=population[a];
		}

		int rdm=new Random().nextInt(100);
		if (rdm>=100-Const.chancesCrossOver)
		{
			lose.crossOver(win);
		}
		if (rdm>=100-Const.chancesMutation)
		{
			lose.mutate();
		}
	}

	public Individu meilleurIndividu()
	{
		Individu meilleurIndividu=population[0];
		double meilleurScore=Integer.MIN_VALUE;
		for (Individu i : population)
		{
			double score=epreuve.fitness(i);
			if (score>meilleurScore)
			{
				meilleurScore=score;
				meilleurIndividu=i;
			}
		}
		return meilleurIndividu;
	}

}
