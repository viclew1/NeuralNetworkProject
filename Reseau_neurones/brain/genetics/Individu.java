package genetics;

import java.awt.Graphics;

public abstract class Individu
{
	protected double score=0;
	protected int index;
	protected boolean incroyable=false;
	protected String name;
	
	public Individu(String name, int index)
	{
		this.name = name;
		this.index = index;
	}
	
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public abstract void randomize();
	
	public abstract void crossOverPriorities(Individu individu);
	public abstract void crossOverProduction(Individu individu);
	public abstract void mutatePriorities();
	public abstract void mutateProduction();
	
	public abstract Individu deepCopy();

	public void addScore(double points)
	{
		score+=points;
		if (!incroyable && score>1000000)
		{
			incroyable=true;
			System.out.println(name + " incroyable !!");
			System.out.println(this);
		}
	}
	
	public abstract double[] getOutputs(double[] input);

	public double getScore()
	{
		return Math.max(0, score);
	}

	public void resetScore()
	{
		score=0;
	}

	public abstract void draw(Graphics g);

}
