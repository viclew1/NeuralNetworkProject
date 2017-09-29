package utils;

public class Constantes
{

	public final static String TITRE_APPLICATION = "Neural Network Project";

	private static int FRAME_RATE;
	public static long TimeBetweenTwoComputes;
	
	public static final int POPULATION_SIZE_BEE = 100;
	public static final int POPULATION_SIZE_WASP = 100;
	public static final int POPULATION_SIZE_TANK = 100;
	public static final int POPULATION_SIZE_SOLDIER = 100;
	public static final int POPULATION_SIZE_COMPLEXDODGER = 100;
	public static final int POPULATION_SIZE_SIMPLEDODGER = 100;
	
	public static final int GENERATION_COUNT = 100000;

	public static final int CREATURE 		= 1;
	public static final int COLLECTABLE 	= 2;
	public static final int DELIMITATION	= 3;
	
	public static double SIZE = 60;
	public static final double SIZE_MIN = 2;
	public static final double SIZE_MAX = 100;
	public static double SCROLL_X = 0,SCROLL_Y = 0;

	public static int GENERATION_LENGTH;

	public static int FOOD_AMOUNT=1000;
	public static int FUEL_AMOUNT=250;
	
	public static boolean DRAW_CAPTORS = false;
	public static boolean DRAW_HP = false;
	public static boolean SLOW_MO_MODE = false;
	public static final long SLOW_MO_TIME = 1000/120;
	public static boolean DRAW_ALL = true;
	public static boolean PAUSE = false;
	
	public static final int BEE  = 1;
	public static final int WASP = 2;
	public static final int TANK = 3;
	public static final int SOLDIER = 4;
	public static final int COMPLEXDODGER = 5;
	public static final int SIMPLEDODGER = 6;
	
	public static final int VEGETABLE = 11;
	public static final int MEAT = 12;
	public static final int FUEL = 13;
	public static final int POWERUP = 14;
	
	public static final int PROJECTILE = 22;
	public static final int FIREBALL = 23;
	public static final int WALL = 24;
	
	public static final int WATER = 31;
	public static final int LAVA = 32;

	public static final String TYPE_BEE = "BEE";
	public static final String TYPE_WASP = "WASP";
	public static final String TYPE_TANK = "TANK";
	public static final String TYPE_SOLDIER = "SOLDIER";
	public static final String TYPE_COMPLEXDODGER = "COMPLEX DODGER";
	public static final String TYPE_SIMPLEDODGER = "SIMPLE DODGER";
	
	public static final int[] LAYERS_SIZES_BEE = new int[] {20,12,6,12,1};
	public static final int[] LAYERS_SIZES_TANK = new int[] {23,15,7,15,4};
	public static final int[] LAYERS_SIZES_WASP = new int[] {20,12,6,12,1};
	public static final int[] LAYERS_SIZES_SOLDIER = new int[] {23,15,7,15,4};
	public static final int[] LAYERS_SIZES_COMPLEXDODGER = new int[] {15,14,6,14,2};
	public static final int[] LAYERS_SIZES_SIMPLEDODGER = new int[] {21,14,6,14,2};
	
	public static final String[] creatures = new String[] {"Abeilles","Guepes","Soldats","Tanks","Complex Dodger", "Simple Dodger"};
	
	
	
	public static void changePositionComputesPerSecond(int newFrequency) throws FrameRateTooLowException 
	{
		if (newFrequency < 5)
			throw new FrameRateTooLowException(5);
		FRAME_RATE = newFrequency;
		TimeBetweenTwoComputes = (long) (1000/FRAME_RATE);
	}
	
	public static void resetAllConstantes()
	{
		SIZE = 60;
		FRAME_RATE=1000;
		SCROLL_X = 0;
		SCROLL_Y = 0;
		DRAW_CAPTORS = false;
		PAUSE = false;
		try
		{
			changePositionComputesPerSecond(FRAME_RATE);
		} catch (FrameRateTooLowException e)
		{
			e.printStackTrace();
		}
	}
}
