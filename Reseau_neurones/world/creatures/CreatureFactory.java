package creatures;

import static utils.Constantes.ADAPTATIVE;
import static utils.Constantes.BEE;
import static utils.Constantes.COMPLEXDODGER;
import static utils.Constantes.DRAGON;
import static utils.Constantes.HEDGEHOG;
import static utils.Constantes.LAYERS_SIZES_ADAPTATIVE;
import static utils.Constantes.LAYERS_SIZES_BEE;
import static utils.Constantes.LAYERS_SIZES_COMPLEXDODGER;
import static utils.Constantes.LAYERS_SIZES_DRAGON;
import static utils.Constantes.LAYERS_SIZES_HEDGEHOG;
import static utils.Constantes.LAYERS_SIZES_RHINOCEROS;
import static utils.Constantes.LAYERS_SIZES_SIMPLEDODGER;
import static utils.Constantes.LAYERS_SIZES_SLUG;
import static utils.Constantes.LAYERS_SIZES_SOLDIER;
import static utils.Constantes.LAYERS_SIZES_TANK;
import static utils.Constantes.LAYERS_SIZES_WASP;
import static utils.Constantes.RHINOCEROS;
import static utils.Constantes.SIMPLEDODGER;
import static utils.Constantes.SLUG;
import static utils.Constantes.SOLDIER;
import static utils.Constantes.TANK;
import static utils.Constantes.TYPE_ADAPTATIVE;
import static utils.Constantes.TYPE_BEE;
import static utils.Constantes.TYPE_COMPLEXDODGER;
import static utils.Constantes.TYPE_DRAGON;
import static utils.Constantes.TYPE_HEDGEHOG;
import static utils.Constantes.TYPE_RHINOCEROS;
import static utils.Constantes.TYPE_SIMPLEDODGER;
import static utils.Constantes.TYPE_SLUG;
import static utils.Constantes.TYPE_SOLDIER;
import static utils.Constantes.TYPE_TANK;
import static utils.Constantes.TYPE_WASP;
import static utils.Constantes.WASP;

import java.util.Random;

import UI.World;
import creatures.adaptative_creatures.AdaptativeCreature;
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
import fr.lewon.Individual;

public class CreatureFactory
{
	/**
	 * G�n�rateurs de cr�atures
	 */
	
	public static Creature generate(String type, Individual intelligence, World world)
	{
		switch (type)
		{
		case TYPE_BEE:
			return generateBee(intelligence, world);
		case TYPE_WASP:
			return generateWasp(intelligence, world);
		case TYPE_SOLDIER:
			return generateSoldier(intelligence, world);
		case TYPE_TANK:
			return generateTank(intelligence, world);
		case TYPE_COMPLEXDODGER:
			return generateComplexDodger(intelligence, world);
		case TYPE_SIMPLEDODGER:
			return generateSimpleDodger(intelligence, world);
		case TYPE_SLUG:
			return generateSlug(intelligence, world);
		case TYPE_HEDGEHOG:
			return generateHedgehog(intelligence, world);
		case TYPE_RHINOCEROS:
			return generateRhinoceros(intelligence, world);
		case TYPE_DRAGON:
			return generateDragon(intelligence, world);
		case TYPE_ADAPTATIVE:
			return generateAdaptative(intelligence, world);
		default:
			System.out.println("Type inconnu - CreatureFactory.generateCreature");
			System.exit(0);
		}
		return null;
	}

	public static Creature generateAdaptative(Individual intelligence, World world)
	{
		return new AdaptativeCreature(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, world);
	}
	
	public static Creature generateBee(Individual intelligence, World world)
	{
		return new Bee(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, world);
	}

	public static Creature generateWasp(Individual intelligence, World world)
	{
		return new Wasp(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, world);
	}

	public static Creature generateSoldier(Individual intelligence, World world)
	{
		return new Soldier(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, world);
	}

	public static Creature generateTank(Individual intelligence, World world)
	{
		return new Tank(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, world);
	}

	public static Creature generateComplexDodger(Individual intelligence, World world)
	{
		return new ComplexDodger(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, world);
	}
	
	public static Creature generateSimpleDodger(Individual intelligence, World world)
	{
		return new SimpleDodger(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, world);
	}

	public static Creature generateSlug(Individual intelligence, World world)
	{
		return new Slug(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, world);
	}

	public static Creature generateHedgehog(Individual intelligence, World world)
	{
		return new Hedgehog(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, world);
	}

	public static Creature generateRhinoceros(Individual intelligence, World world)
	{
		return new Rhinoceros(3+new Random().nextDouble()*(world.box.getWidth()-6), 3+new Random().nextDouble()*(world.box.getHeight()-6), intelligence, world);
	}

	public static Creature generateDragon(Individual intelligence, World world)
	{
		return new Dragon(10+new Random().nextDouble()*(world.box.getWidth()-20), 10+new Random().nextDouble()*(world.box.getHeight()-20), intelligence, world);
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
		case TYPE_ADAPTATIVE:
			return LAYERS_SIZES_ADAPTATIVE;
		default:
			System.out.println("CreatureFactory.getLayersSize - Type inconnu");
			System.exit(0);
		}
		return null;
	}

	public static String getTypeStrFromTypeInt(int type)
	{
		switch (type)
		{
		case BEE:
			return TYPE_BEE;
		case TANK:
			return TYPE_TANK;
		case WASP:
			return TYPE_WASP;
		case SOLDIER:
			return TYPE_SOLDIER;
		case COMPLEXDODGER:
			return TYPE_COMPLEXDODGER;
		case SIMPLEDODGER:
			return TYPE_SIMPLEDODGER;
		case SLUG:
			return TYPE_SLUG;
		case HEDGEHOG:
			return TYPE_HEDGEHOG;
		case RHINOCEROS:
			return TYPE_RHINOCEROS;
		case DRAGON:
			return TYPE_DRAGON;
		case ADAPTATIVE:
			return TYPE_ADAPTATIVE;
		default:
			System.out.println("CreatureFactory.getTypeStrFromTypeInt - Type inconnu");
			System.exit(0);
		}
		return null;
	}
	
}
