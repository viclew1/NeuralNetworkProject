package creatures.team;

import java.util.Random;

import UI.World;
import creatures.Creature;
import creatures.CreatureFactory;
import genetics.Individu;
import genetics.Selection;

public class Team
{
	private final Creature[] members;
	private final Creature[] deads;
	private int deadCount = 0;
	private World world;
	private Individu brain;
	private Selection selec;

	private final double colorModifier;


	public Team(String type, int size, Individu brain, Selection selec, World world)
	{
		members = new Creature[size];
		deads = new Creature[size];
		for (int i = 0 ; i < size ; i++)
		{
			members[i] = CreatureFactory.generate(type, brain, selec, world);
			members[i].setTeam(this);
			members[i].setTeamIndex(i);
		}
		this.world = world;
		this.selec = selec;
		this.brain = brain;

		this.colorModifier = new Random().nextDouble()+1;
	}

	public double colorModifier()
	{
		return colorModifier;
	}

	public int size()
	{
		return members.length;
	}

	public Creature[] getAll()
	{
		return members;
	}

	public Creature get(int index)
	{
		return members[index];
	}

	public boolean allDead()
	{
		return members.length == deadCount;
	}

	public void reset()
	{
		Individu newBrain = selec.getOffspring(this.brain);
		for (int i = 0 ; i < members.length ; i++)
		{
			if (members[i] == null)
				members[i] = deads[i];
			deads[i] = null;
			members[i].reset(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), newBrain);
		}
		deadCount = 0;
	}

	public void remove(Creature c)
	{
		int i = c.getTeamIndex();
		if (c.getTeam() == this)
			if (deads[i] == null)
			{
				deads[i] = members[i];
				members[i] = null;
				deadCount++;
			}
	}

}
