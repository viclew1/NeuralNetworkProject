package genetics;

import java.awt.Graphics;

public abstract class Individu
{
	protected double score=0;
	protected boolean incroyable=false;
	protected String name;
	
	public Individu(String name)
	{
		this.name=name;
	}
	
	public abstract void crossOver(Individu individu);
	
	public abstract void mutate();
	
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

	public double score()
	{
		return score;
	}

	public void resetScore()
	{
		score=0;
	}

	public abstract void draw(Graphics g);

}
