package utils;

public class Constantes
{

	public final static String TITRE_APPLICATION = "Neural Network Project";

	
	public static final int POPULATION_SIZE_BEE = 100;
	public static final int POPULATION_SIZE_WASP = 40;
	public static final int POPULATION_SIZE_TANK = 300;
	public static final int POPULATION_SIZE_SOLDIER = 300;
	public static final int POPULATION_SIZE_COMPLEXDODGER = 600;
	public static final int POPULATION_SIZE_SIMPLEDODGER = 0;
	public static final int POPULATION_SIZE_SLUG = 30;
	public static final int POPULATION_SIZE_HEDGEHOG = 20;
	
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
	public static final int SLUG = 7;
	public static final int HEDGEHOG = 8;
	
	public static final int DRAGON = 10;
	
	
	public static final int VEGETABLE = 11;
	public static final int MEAT = 12;
	public static final int FUEL = 13;
	public static final int POWERUP = 14;
	public static final int BOMB = 15;
	
	public static final int PROJECTILE = 22;
	public static final int FIREBALL = 23;
	
	public static final int LAVA = 31;
	public static final int WATER = 32;

	public static final String TYPE_BEE = "BEE";
	public static final String TYPE_WASP = "WASP";
	public static final String TYPE_TANK = "TANK";
	public static final String TYPE_SOLDIER = "SOLDIER";
	public static final String TYPE_COMPLEXDODGER = "COMPLEX DODGER";
	public static final String TYPE_SIMPLEDODGER = "SIMPLE DODGER";
	public static final String TYPE_SLUG = "SLUG";
	public static final String TYPE_HEDGEHOG = "HEDGEHOG";
	public static final String TYPE_DRAGON = "DRAGON";
	
	
	public static final int[] LAYERS_SIZES_BEE = new int[] {19,5,17,2};
	public static final int[] LAYERS_SIZES_TANK = new int[] {25,6,23,4};
	public static final int[] LAYERS_SIZES_WASP = new int[] {23,25,13,8,2};
	public static final int[] LAYERS_SIZES_SOLDIER = new int[] {25,15,7,15,4};
	public static final int[] LAYERS_SIZES_COMPLEXDODGER = new int[] {23,4,12,2};
	public static final int[] LAYERS_SIZES_SIMPLEDODGER = new int[] {23,4,21,2};
	public static final int[] LAYERS_SIZES_SLUG = new int[] {22,11,18,12,2};
	public static final int[] LAYERS_SIZES_HEDGEHOG = new int[] {22,28,14,4};
	public static final int[] LAYERS_SIZES_DRAGON = new int[] {25,28,14,4};
	
	public static int FOOD_AMOUNT=1000;
	public static int FUEL_AMOUNT=250;
	
	public static boolean DRAW_CAPTORS = false;
	public static boolean DRAW_HP = false;
	public static boolean DRAW_ALL = true;
	public static boolean PAUSE = false;
	public static boolean SLOW_MO = false;
	public static long TIME_TO_WAIT = 1000000000 / 60;
	
}
