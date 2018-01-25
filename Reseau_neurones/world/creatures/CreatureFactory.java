package creatures;

import java.util.List;
import java.util.Random;

import UI.World;
import collectables.Collectable;
import creatures.chargers.Rhinoceros;
import creatures.dodgers.ComplexDodger;
import creatures.dodgers.SimpleDodger;
import creatures.insects.Bee;
import creatures.insects.Wasp;
import creatures.shooters.Dragon;
import creatures.shooters.Hedgehog;
import creatures.shooters.Soldier;
import creatures.shooters.Tank;
import creatures.slugs.Slug;
import genetics.Individu;
import genetics.Selection;
import limitations.Delimitation;
import limitations.DelimitationBox;

import static utils.Constantes.*;

public class CreatureFactory
{
	/**
	 * Générateurs de créatures
	 */
	
	public static Creature generate(String type, Individu intelligence, Selection selec, World world)
	{
		switch (type)
		{
		case TYPE_BEE:
			return generateBee(intelligence, selec, world);
		case TYPE_WASP:
			return generateWasp(intelligence, selec, world);
		case TYPE_SOLDIER:
			return generateSoldier(intelligence, selec, world);
		case TYPE_TANK:
			return generateTank(intelligence, selec, world);
		case TYPE_COMPLEXDODGER:
			return generateComplexDodger(intelligence, selec, world);
		case TYPE_SIMPLEDODGER:
			return generateSimpleDodger(intelligence, selec, world);
		case TYPE_SLUG:
			return generateSlug(intelligence, selec, world);
		case TYPE_HEDGEHOG:
			return generateHedgehog(intelligence, selec, world);
		case TYPE_RHINOCEROS:
			return generateRhinoceros(intelligence, selec, world);
		case TYPE_DRAGON:
			return generateDragon(intelligence, selec, world);
		default:
			System.out.println("Type inconnu - CreatureFactory.generateCreature");
			System.exit(0);
		}
		return null;
	}

	public static Creature generateBee(Individu intelligence, Selection selec, World world)
	{
		return new Bee(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, selec,
				world);
	}

	public static Creature generateWasp(Individu intelligence, Selection selec, World world)
	{
		return new Wasp(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, selec,
				world);
	}

	public static Creature generateSoldier(Individu intelligence, Selection selec, World world)
	{
		return new Soldier(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, selec,
				world);
	}

	public static Creature generateTank(Individu intelligence, Selection selec, World world)
	{
		return new Tank(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, selec,
				world);
	}

	public static Creature generateComplexDodger(Individu intelligence, Selection selec, World world)
	{
		return new ComplexDodger(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, selec,
				world);
	}

	public static Creature generateSimpleDodger(Individu intelligence, Selection selec, World world)
	{
		return new SimpleDodger(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, selec,
				world);
	}

	public static Creature generateSlug(Individu intelligence, Selection selec, World world)
	{
		return new Slug(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, selec,
				world);
	}

	public static Creature generateHedgehog(Individu intelligence, Selection selec, World world)
	{
		return new Hedgehog(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, selec,
				world);
	}

	public static Creature generateRhinoceros(Individu intelligence, Selection selec, World world)
	{
		return new Rhinoceros(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, selec,
				world);
	}

	public static Creature generateDragon(Individu intelligence, Selection selec, World world)
	{
		return new Dragon(10+new Random().nextDouble()*(world.box.getWidth()-20), 10+new Random().nextDouble()*(world.box.getHeight()-20), intelligence, selec,
				world);
	}

	public static int[] getLayersSize(String type)
	{
		switch (type)
		{
		case TYPE_BEE:
			return LAYERS_SIZES_BEE;
		case TYPE_TANK:
			return LAYERS_SIZES_TANK;
		case TYPE_WASP:
			return LAYERS_SIZES_WASP;
		case TYPE_SOLDIER:
			return LAYERS_SIZES_SOLDIER;
		case TYPE_COMPLEXDODGER:
			return LAYERS_SIZES_COMPLEXDODGER;
		case TYPE_SIMPLEDODGER:
			return LAYERS_SIZES_SIMPLEDODGER;
		case TYPE_SLUG:
			return LAYERS_SIZES_SLUG;
		case TYPE_HEDGEHOG:
			return LAYERS_SIZES_HEDGEHOG;
		case TYPE_RHINOCEROS:
			return LAYERS_SIZES_RHINOCEROS;
		case TYPE_DRAGON:
			return LAYERS_SIZES_DRAGON;
		default:
			System.out.println("CreatureFactory.getLayersSize - Type inconnu");
			System.exit(0);
		}
		return null;
	}
	
}
