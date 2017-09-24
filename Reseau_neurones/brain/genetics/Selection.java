package genetics;
import java.util.Random;


public class Selection
{
	public Epreuve epreuve;
	public int nombreIndividus;
	public int nombreGenerations;
	public Individu[] population;
	public Individu meilleurTrouvé;
	public double meilleureFitness=Double.MIN_VALUE;
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
		for (int i=0;i<nombreGenerations;i++)
		{
			//System.out.println("------ DEBUT GENERATION "+(i+1)+" ------");
			epreuve.lancerEpreuve(population,type);
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
			generateNextGeneration();
		}

		return meilleurIndividu();
	}

	public void generateNextGeneration()
	{
		double fitnessSum = 0;
		for (Individu i : population)
			fitnessSum+=epreuve.fitness(i);

		Individu[] newPopulation = new Individu[nombreIndividus];
		for (int i=0 ; i<nombreIndividus ; i++)
		{
			Individu i1=null;
			Individu i2=null;
			double partialFitnessSum = 0;
			double randomDouble = new Random().nextDouble()*fitnessSum;
			for (int j=nombreIndividus-1 ; j>=0 ; j--)
			{
				partialFitnessSum += epreuve.fitness(population[j]);
				if (partialFitnessSum >= randomDouble)
					i1 = population[j];
			}
			partialFitnessSum = 0;
			randomDouble = new Random().nextDouble()*fitnessSum;
			for (int j=nombreIndividus-1 ; j>=0 ; j--)
			{
				partialFitnessSum += epreuve.fitness(population[j]);
				if (partialFitnessSum >= randomDouble)
					i2 = population[j];
			}

			newPopulation[i] = breed(i1,i2);
		}

		population = newPopulation;
	}

	private Individu breed(Individu i1, Individu i2)
	{
		Individu parent1,parent2;
		if (new Random().nextBoolean())
		{
			parent1 = i1;
			parent2 = i2;
		}
		else
		{
			parent1 = i2;
			parent2 = i1;
		}
		Individu child = parent1.deepCopy();

		int rdm=new Random().nextInt(100);
		if (rdm>=100-Const.chancesCrossOver)
			child.crossOver(parent2);
		rdm=new Random().nextInt(100);
		if (rdm>=100-Const.chancesMutation)
			child.mutate();


		return child;
	}

	public Individu meilleurIndividu()
	{
		Individu meilleurIndividu=population[0];
		double meilleurScore=Double.MIN_VALUE;
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
