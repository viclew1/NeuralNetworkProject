package creatures.shooters;

import java.awt.Color;
import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import static utils.Constantes.*;

public class Hedgehog extends ShooterCreature{

	static Color brown = new Color(156, 93, 82);

	public Hedgehog(double x, double y, Individu brain, Selection selec, 
			World world)
	{
		super(x, y, 3, 300, 0.7, 0.5, 2, 2, 2000, Integer.MAX_VALUE, 40, brown,
				new Captor[]{
						new EyeCaptor(Math.PI/7,18,Math.PI/3),
						new EyeCaptor(-Math.PI/7,18,Math.PI/3),
						new EyeCaptor(-Math.PI,10,Math.PI/4),
		},
				new int[][] {
					{
						HEDGEHOG,
						SLUG,
						RHINOCEROS,
						DRAGON,
					},
					{
						BOMB,
						VEGETABLE,
					},
					{
						PROJECTILE,
					}},
				brain, selec, HEDGEHOG, brown, 
				LAYERS_SIZES_HEDGEHOG[0],
				world);
	}

	@Override
	public void interactWith(Collectable c) {
		switch (c.getType())
		{
		case BOMB:
			if (!isInvincible())
				die();
			break;
		case VEGETABLE:
			brain.addScore(50);
			hp+=50;
			if (hp>hpMax)
				hp=hpMax;
			c.consume();
			break;

		default:
			break;
		}
	}

	@Override
	public void touchedBy(Creature c)
	{
		switch (c.getType())
		{
		case HEDGEHOG:
			reproduceWith(c);
			break;
		case RHINOCEROS:
			if (!this.isInvincible()) {
				die();
			}
			break;
		case DRAGON:
			if (!this.isInvincible()) {
				die();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void touch(Creature c)
	{
		switch (c.getType())
		{
		case SLUG:
			if (!c.isInvincible())
				{
				hp += 50;
				if (hp > hpMax) hp = hpMax;
				brain.addScore(10);
				}
			break;
		case HEDGEHOG:
			break;
		case RHINOCEROS:
			if (!this.isInvincible()) {
				die();
			}
			break;
		case DRAGON:
			if (!this.isInvincible()) {
				die();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void targetReport(int targetType) {
		switch (targetType)
		{
		case SLUG:
			brain.addScore(10);
			break;
		case RHINOCEROS:
			//brain.addScore(0);
			break;
		case BOMB:
			brain.addScore(5);
			break;
		default:
			break;
		}
	}

	@Override
	protected void applyDecisions(double[] decisions) {
		cdShoot--;
		turn(2*(0.5-decisions[0]));
		moveFront(decisions[1]);
		straff(2*(0.5-decisions[2]));
		if (decisions[3]>0.5)
			shoot();
		
	}
}
