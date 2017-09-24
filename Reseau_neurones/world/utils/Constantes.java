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
	
	public static final int DELIMITATION_BOX = 21;
	public static final int PROJECTILE = 22;
	public static final int FIREBALL = 23;

	public static final String TYPE_BEE = "BEE";
	public static final String TYPE_WASP = "WASP";
	public static final String TYPE_TANK = "TANK";
	public static final String TYPE_SOLDIER = "SOLDIER";
	public static final String TYPE_COMPLEXDODGER = "COMPLEX DODGER";
	public static final String TYPE_SIMPLEDODGER = "SIMPLE DODGER";
	
	public static int GENERATION_LENGTH;
	
	public static final int INPUT_COUNT_BEE=18;
	public static final int HIDDEN_COUNT_BEE=12;
	public static final int OUTPUT_COUNT_BEE=1;
	
	public static final int INPUT_COUNT_WASP=18;
	public static final int HIDDEN_COUNT_WASP=12;
	public static final int OUTPUT_COUNT_WASP=1;

	public static final int INPUT_COUNT_TANK=21;
	public static final int HIDDEN_COUNT_TANK=15;
	public static final int OUTPUT_COUNT_TANK=4;
	
	public static final int INPUT_COUNT_SOLDIER=21;
	public static final int HIDDEN_COUNT_SOLDIER=15;
	public static final int OUTPUT_COUNT_SOLDIER=4;
	
	public static final int INPUT_COUNT_COMPLEXDODGER=13;
	public static final int HIDDEN_COUNT_COMPLEXDODGER=15;
	public static final int OUTPUT_COUNT_COMPLEXDODGER=2;

	public static final int INPUT_COUNT_SIMPLEDODGER=11;
	public static final int HIDDEN_COUNT_SIMPLEDODGER=15;
	public static final int OUTPUT_COUNT_SIMPLEDODGER=2;
	
	public static int FOOD_AMOUNT=1000;
	public static int FUEL_AMOUNT=250;
	
	public static boolean DRAW_CAPTORS = false;
	public static boolean DRAW_HP = false;
	public static boolean SLOW_MO_MODE = false;
	public static final long SLOW_MO_TIME = 1000/120;
	public static boolean DRAW_ALL = true;
	public static boolean PAUSE = false;
	
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
