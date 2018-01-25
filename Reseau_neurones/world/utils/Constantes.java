package utils;

public class Constantes
{

	public final static String TITRE_APPLICATION = "Neural Network Project";

	public static final int POPULATION_SIZE_BEE = 5;
	public static final int POPULATION_SIZE_WASP = 2;
	public static final int POPULATION_SIZE_TANK = 15;
	public static final int POPULATION_SIZE_SOLDIER = 15;
	public static final int POPULATION_SIZE_COMPLEXDODGER = 30;
	public static final int POPULATION_SIZE_SIMPLEDODGER = 0;
	public static final int POPULATION_SIZE_SLUG = 6;
	public static final int POPULATION_SIZE_HEDGEHOG = 4;
	public static final int POPULATION_SIZE_RHINOCEROS = 3;
	public static final int POPULATION_SIZE_BLACKWITCH = 1;
	public static final int POPULATION_SIZE_DRAGON = 1;
	public static final int POPULATION_SIZE_ADAPTATIVE = 5;
	
	public static final int TEAM_SIZE = 6;
	
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
	public static final int RHINOCEROS = 9;
	public static final int DRAGON = 10;
	public static final int ADAPTATIVE = 11;
	public static final int BLACKWITCH = 11;
	
	public static final int VEGETABLE = 101;
	public static final int MEAT = 102;
	public static final int FUEL = 103;
	public static final int POWERUP = 104;
	public static final int BOMB = 105;
	
	public static final int PROJECTILE = 202;
	public static final int FIREBALL = 203;
	
	public static final int LAVA = 301;
	public static final int WATER = 302;

	public static final String TYPE_BEE = "BEE";
	public static final String TYPE_WASP = "WASP";
	public static final String TYPE_TANK = "TANK";
	public static final String TYPE_SOLDIER = "SOLDIER";
	public static final String TYPE_COMPLEXDODGER = "COMPLEX DODGER";
	public static final String TYPE_SIMPLEDODGER = "SIMPLE DODGER";
	public static final String TYPE_SLUG = "SLUG";
	public static final String TYPE_HEDGEHOG = "HEDGEHOG";
	public static final String TYPE_DRAGON = "DRAGON";
	public static final String TYPE_BLACKWITCH = "BLACKWITCH";
	public static final String TYPE_RHINOCEROS = "RHINOCEROS";
	public static final String TYPE_ADAPTATIVE = "ADAPTATIVE";
	
	
	public static final int[] LAYERS_SIZES_BEE = new int[] {22,5,17,2};
	public static final int[] LAYERS_SIZES_TANK = new int[] {26,6,23,4};
	public static final int[] LAYERS_SIZES_WASP = new int[] {23,25,13,8,2};
	public static final int[] LAYERS_SIZES_SOLDIER = new int[] {26,15,7,15,4};
	public static final int[] LAYERS_SIZES_COMPLEXDODGER = new int[] {23,4,12,2};
	public static final int[] LAYERS_SIZES_SIMPLEDODGER = new int[] {23,4,21,2};
	public static final int[] LAYERS_SIZES_SLUG = new int[] {30,11,18,12,2};
	public static final int[] LAYERS_SIZES_HEDGEHOG = new int[] {29,28,14,4};
	public static final int[] LAYERS_SIZES_DRAGON = new int[] {32,28,14,4};
	public static final int[] LAYERS_SIZES_RHINOCEROS = new int[] {28,26,12,3};
	public static final int[] LAYERS_SIZES_ADAPTATIVE = new int[] {28,26,12,3};
	public static final int[] LAYERS_SIZES_BLACKWITCH = new int[] {28,26,12,3};
	
	public static int FOOD_AMOUNT=1000;
	public static int FUEL_AMOUNT=250;
	
	public static boolean DRAW_CAPTORS = false;
	public static boolean DRAW_HP = false;
	public static boolean DRAW_ALL = true;
	public static boolean PAUSE = false;
	public static boolean SLOW_MO = false;
	public static long TIME_TO_WAIT = 1000000000 / 60;
	
}
