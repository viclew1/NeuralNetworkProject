package utils;

public class Constantes
{

	public final static String TITRE_APPLICATION = "Neural Network Project";

	private static int FRAME_RATE;
	public static long TimeBetweenTwoComputes;
	
	public static final int POPULATION_SIZE_BEE = 196;
	public static final int POPULATION_SIZE_WASP = 50;
	public static final int GENERATION_COUNT = 1000;

	public static final int CREATURE 		= 1;
	public static final int COLLECTABLE 	= 2;
	public static final int DELIMITATION	= 3;
	
	public static double SIZE = 60;
	public static final double SIZE_MIN = 2;
	public static final double SIZE_MAX = 100;
	public static double SCROLL_X = 0,SCROLL_Y = 0;

	public static final int BEE  = 10;
	public static final int WASP = 11;
	public static final int TANK = 14;
	public static final int VEGETABLE = 12;
	public static final int MEAT = 13;

	public static final String TYPE_BEE = "BEE";
	public static final String TYPE_WASP = "WASP";
	public static final String TYPE_TANK = "TANK";
	
	
	public static final int INPUT_COUNT_BEE=16;
	public static final int HIDDEN_COUNT_BEE=12;
	public static final int OUTPUT_COUNT_BEE=1;
	
	public static final int INPUT_COUNT_WASP=16;
	public static final int HIDDEN_COUNT_WASP=12;
	public static final int OUTPUT_COUNT_WASP=1;

	public static final int INPUT_COUNT_TANK=16;
	public static final int HIDDEN_COUNT_TANK=12;
	public static final int OUTPUT_COUNT_TANK=1;
	
	
	public static final int FOOD_AMOUNT=1000;
	
	public static boolean DRAW_CAPTORS = false;
	public static boolean DRAW_HP = false;
	public static boolean SLOW_MO_MODE = false;
	public static boolean DRAW_ALL = true;
	public static boolean PAUSE = false;
	
	public static final String[] creatures = new String[] {"Abeilles","Guepes"};
	
	
	
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
