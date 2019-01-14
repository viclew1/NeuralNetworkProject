package creatures.shooters;

import static utils.Constantes.BOMB;
import static utils.Constantes.DRAGON;
import static utils.Constantes.HEDGEHOG;
import static utils.Constantes.LAYERS_SIZES_HEDGEHOG;
import static utils.Constantes.PROJECTILE;
import static utils.Constantes.RHINOCEROS;
import static utils.Constantes.SLUG;
import static utils.Constantes.VEGETABLE;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import fr.lewon.Individual;

public class Hedgehog extends ShooterCreature{

	static Color brown = new Color(156, 93, 82);

	public Hedgehog(double x, double y, Individual brain, 
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
				brain, HEDGEHOG, brown, 
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
			break;
		case RHINOCEROS:
			//brain.addScore(0);
			break;
		case BOMB:
			break;
		default:
			break;
		}
	}

	@Override
	protected void applyDecisions(List<Double> decisions) {
		cdShoot--;
		turn(2*(0.5-decisions.get(0)));
		moveFront(decisions.get(1));
		straff(2*(0.5-decisions.get(2)));
		if (decisions.get(3)>0.5)
			shoot();
		
	}
}
