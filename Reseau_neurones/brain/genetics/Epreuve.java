package genetics;

public interface Epreuve
{
	public abstract void initSelection(int nbIndiv, int nbGen, String type);
	public abstract double fitness(Individu individu);
	public abstract void lancerEpreuve(Individu[] population, String type);
}
