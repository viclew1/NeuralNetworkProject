package genetics;
import java.util.Random;
import static genetics.Utils.*;

public class Selection
{
	public final Epreuve epreuve;
	public final int nombreIndividus;
	public final int nombreGenerations;
	public final String type;
	public Individu[] population;
	public Individu[] deads;
	public int deadCount = 0;
	public int deadIndex = 0;
	public Individu meilleurTrouve;
	public double meilleureFitness=Double.MIN_VALUE;

	public Selection(Epreuve epreuve, int nombreIndividus, int nombreGenerations, String type)
	{
		this.epreuve=epreuve;
		this.nombreIndividus=nombreIndividus;
		deads = new Individu[nombreIndividus*2];
		this.nombreGenerations=nombreGenerations;
		this.type=type;
	}

	boolean found=false;

	public Individu lancerSelection()
	{
		for (int i=0;i<nombreGenerations;i++)
		{
			//System.out.println("------ DEBUT GENERATION "+(i+1)+" ------");
			epreuve.lancerEpreuve(this,population,type);
			if (found)
				return meilleurIndividu();
			double meilleurScore = epreuve.fitness(meilleurIndividu());
			//System.out.println("Meilleur individu : "+meilleurScore);
			if (meilleurScore>meilleureFitness)
			{
				meilleureFitness=meilleurScore;
				meilleurTrouve=meilleurIndividu().deepCopy();
				System.out.println("Generation : "+(i+1));
				System.out.println("Meilleure trouvaille : "+meilleurTrouve+"\nFitness : "+meilleureFitness);
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
			if (rdm>=100-Utils.chancesCrossOver)
				if (new Random().nextBoolean())
					loser.crossOverPriorities(winner);
				else
					loser.crossOverProduction(winner);
			rdm=new Random().nextInt(100);
			if (rdm>=100-Utils.chancesMutation)
				if (new Random().nextBoolean())
					loser.mutatePriorities();
				else
					loser.mutateProduction();
		}
	}

	public Individu[] stochasticNewPopulationGenerator(Individu[] popRef, double fitnessSum)
	{
		double deltaP = fitnessSum/popRef.length;
		double start = new Random().nextDouble()*deltaP;
		double[] pointers = new double[popRef.length];
		for (int i = 0 ; i<pointers.length ; i++)
			pointers[i] = start + i*deltaP;

		Individu[] newPopulation = new Individu[popRef.length];
		for (int pointerCpt=0 ; pointerCpt<popRef.length ; pointerCpt++)
		{
			double p = pointers[pointerCpt];
			int i = 0;
			double partialFitnessSum = 0;
			while ((partialFitnessSum += popRef[i].getScore()) < p)
				i++;
			newPopulation[pointerCpt] = popRef[i];
		}
		return newPopulation;
	}

	public void stochasticUniversalSamplingSelection()
	{
		double fitnessSum = 0;
		for (Individu i : population)
			fitnessSum+=epreuve.fitness(i);

		Random r = new Random();

		Individu[] matingPopulation = stochasticNewPopulationGenerator(population, fitnessSum);

		Individu[] newPopulation = new Individu[nombreIndividus];
		for (int i=0;i<nombreIndividus;i++)
			newPopulation[i] = breed(matingPopulation[r.nextInt(nombreIndividus)],matingPopulation[r.nextInt(nombreIndividus)]);
		population = newPopulation;

		/*bubbleSort(population);
		int n = population.length;
		for (int i = 0 ; i < n/2 ; i++)
		{
			int rdm = r.nextInt(100);
			if (rdm>=100-Utils.chancesCrossOver)
				population[i].crossOver(population[n-1-i]);
			rdm = r.nextInt(100);
			if (rdm>=100-Utils.chancesMutation)
				population[i].mutate();
		}*/
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
		Random r = new Random();
		if (r.nextBoolean())
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

		int rdm=r.nextInt(100);
		if (rdm>=100-Utils.chancesCrossOver)
			if (r.nextBoolean())
				child.crossOverPriorities(parent2);
			else
				child.crossOverProduction(parent2);
		rdm=r.nextInt(100);
		if (rdm>=100-Utils.chancesMutation)
			if (r.nextBoolean())
				child.mutatePriorities();
			else
				child.mutateProduction();


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

	public Individu getOffspring(Individu deadOne)
	{
		double fitness = deadOne.getScore();
		if (fitness > meilleureFitness)
		{
			meilleureFitness = fitness;
			meilleurTrouve = deadOne;
			if (fitness > 10)
				System.out.println("Meilleure trouvaille : "+meilleurTrouve+"\nFitness : "+meilleureFitness);
		}

		deads[deadIndex++] = deadOne;
		if (deadCount!=deads.length)
			deadCount++;
		if (deadIndex==deads.length)
			deadIndex = 0;

		Individu[] matingPopulation = new Individu[nombreIndividus + deadCount];
		for (int i = 0 ; i < nombreIndividus ; i++)
			matingPopulation[i] = population[i];
		for (int i = 0 ; i < deadCount ; i++)
			matingPopulation[i + nombreIndividus] = deads[i];

		double fitnessSum = 0;
		for (Individu i : matingPopulation)
			fitnessSum+=i.getScore();
		Random r = new Random();



		matingPopulation = stochasticNewPopulationGenerator(matingPopulation, fitnessSum);
		Individu offspring = breed(matingPopulation[r.nextInt(nombreIndividus)],matingPopulation[r.nextInt(nombreIndividus)]);
		offspring.setIndex(deadOne.index);
		population[deadOne.index] = offspring;
		return offspring;
	}

}
