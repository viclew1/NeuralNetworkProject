package genetics;
import java.util.Random;


public class Selection
{
	public final Epreuve epreuve;
	public final int nombreIndividus;
	public final int nombreGenerations;
	public final String type;
	public Individu[] population;
	public Individu meilleurTrouvé;
	public double meilleureFitness=Double.MIN_VALUE;

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
			shuffleArray(population);
			//rouletteSelection();
			stochasticUniversalSamplingSelection();
			//tournamentSelection();
		}

		return meilleurIndividu();
	}
	
	public void tournamentSelection()
	{
		for (int i = 0 ; i < nombreIndividus-1 ; i++)
		{
			if (i%2==1)
				continue;
			Individu challenger = population[i];
			Individu opponent = population[i+1];
			Individu winner,loser;
			if (epreuve.fitness(challenger)>epreuve.fitness(opponent))
			{
				winner = challenger;
				loser = opponent;
			}
			else
			{
				winner = opponent;
				loser = challenger;
			}
			
			int rdm=new Random().nextInt(100);
			if (rdm>=100-Const.chancesCrossOver)
				loser.crossOver(winner);
			rdm=new Random().nextInt(100);
			if (rdm>=100-Const.chancesMutation)
				loser.mutate();
		}
	}
	
	public Individu[] stochasticNewPopulationGenerator(double fitnessSum)
	{
		double deltaP = fitnessSum/nombreIndividus;
		double start = new Random().nextDouble()*deltaP;
		double[] pointers = new double[nombreIndividus];
		for (int i = 0 ; i<pointers.length ; i++)
			pointers[i] = start + i*deltaP;
		
		Individu[] newPopulation = new Individu[nombreIndividus];
		for (int pointerCpt=0 ; pointerCpt<nombreIndividus ; pointerCpt++)
		{
			double p = pointers[pointerCpt];
			int i = 0;
			double partialFitnessSum = 0;
			while ((partialFitnessSum += epreuve.fitness(population[i])) < p)
				i++;
			newPopulation[pointerCpt] = population[i];
		}
		return newPopulation;
	}
	
	public void stochasticUniversalSamplingSelection()
	{
		double fitnessSum = 0;
		for (Individu i : population)
			fitnessSum+=epreuve.fitness(i);
		Individu[] parents1 = stochasticNewPopulationGenerator(fitnessSum);
		Individu[] parents2 = stochasticNewPopulationGenerator(fitnessSum);
		shuffleArray(parents1);
		shuffleArray(parents2);
		
		Individu[] newPopulation = new Individu[nombreIndividus];
		for (int i=0;i<nombreIndividus;i++)
			newPopulation[i] = breed(parents1[i],parents2[i]);
		population = newPopulation;
	}
	
	private void shuffleArray(Object[] pop)
	{
		  for (int i=nombreIndividus-1 ; i>= 1 ; i--)
		  {
			  int j = new Random().nextInt(i+1);
			  Object temp = pop[j];
			  pop[j] = pop[i];
			  pop[i] = temp;
		  }
	}

	
	public void rouletteSelection()
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
				{
					i1 = population[j];
					break;
				}
			}
			partialFitnessSum = 0;
			randomDouble = new Random().nextDouble()*fitnessSum;
			for (int j=nombreIndividus-1 ; j>=0 ; j--)
			{
				partialFitnessSum += epreuve.fitness(population[j]);
				if (partialFitnessSum >= randomDouble)
				{
					i2 = population[j];
					break;
				}
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
