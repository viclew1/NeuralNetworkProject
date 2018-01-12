package creatures.shooters;

import static utils.Constantes.*;

import java.awt.Color;
import java.util.List;

import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import limitations.Delimitation;
import limitations.DelimitationBox;
import limitations.throwables.Projectile;

public class Dragon extends ShooterCreature
{

	
	
	public Dragon(double x, double y, Individu brain, Selection selec, List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box)
	{
		super(x, y, 1.5, 500, 0.40,0.05,
				0.4,1,60, 150, Color.RED,
				new Captor[] {
						new EyeCaptor(0,45,0.001),
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI,6,Math.PI/4),
		}, brain, selec, DRAGON, Color.RED, LAYERS_SIZES_DRAGON[0],
				creatures,collectables,delimitations, box);
	}

	protected void shoot()
	{
		if (cdShoot<=0)
		{
			delimitations.add(new Projectile(x, y, projSize, projSpeed, orientation, projDamages, this, projColor));
			cdShoot=cooldown;
		}
	}
	
	@Override
	public void targetReport(int targetType)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateScore()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void applySeenFitness(List<Integer> seenThings)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void applyDecisions(double[] decisions)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactWith(Collectable c)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactWith(Creature c)
	{
		// TODO Auto-generated method stub
		
	}

}
