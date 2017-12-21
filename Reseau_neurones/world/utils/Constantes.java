package utils;

public class Constantes
{

	public final static String TITRE_APPLICATION = "Neural Network Project";

	
	public static final int POPULATION_SIZE_BEE = 500;
	public static final int POPULATION_SIZE_WASP = 50;
	public static final int POPULATION_SIZE_TANK = 300;
	public static final int POPULATION_SIZE_SOLDIER = 300;
	public static final int POPULATION_SIZE_COMPLEXDODGER = 180;
	public static final int POPULATION_SIZE_SIMPLEDODGER = 180;
	
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
	
	
	public static final int VEGETABLE = 11;
	public static final int MEAT = 12;
	public static final int FUEL = 13;
	public static final int POWERUP = 14;
	public static final int BOMB = 15;
	
	public static final int PROJECTILE = 22;
	public static final int FIREBALL = 23;
	public static final int WALL = 24;
	
	public static final int LAVA = 31;
	public static final int WATER = 32;

	public static final String TYPE_BEE = "BEE";
	public static final String TYPE_WASP = "WASP";
	public static final String TYPE_TANK = "TANK";
	public static final String TYPE_SOLDIER = "SOLDIER";
	public static final String TYPE_COMPLEXDODGER = "COMPLEX DODGER";
	public static final String TYPE_SIMPLEDODGER = "SIMPLE DODGER";
	public static final String TYPE_SLUG = "SLUG";
	
	
	public static final int[] LAYERS_SIZES_BEE = new int[] {17,12,15,13,12,6,12,13,15,12,2};
	public static final int[] LAYERS_SIZES_TANK = new int[] {23,15,7,15,4};
	public static final int[] LAYERS_SIZES_WASP = new int[] {21,12,15,13,6,13,15,12,2};
	public static final int[] LAYERS_SIZES_SOLDIER = new int[] {23,15,7,15,4};
	public static final int[] LAYERS_SIZES_COMPLEXDODGER = new int[] {21,12,15,13,6,13,15,12,2};
	public static final int[] LAYERS_SIZES_SIMPLEDODGER = new int[] {21,12,13,6,13,12,2};
	public static final int[] LAYERS_SIZES_SLUG = new int[] {};
	public static final int[] LAYERS_SIZES_HEDGEHOG = new int[] {};
	
	public static int FOOD_AMOUNT=1000;
	public static int FUEL_AMOUNT=250;
	
	public static boolean DRAW_CAPTORS = false;
	public static boolean DRAW_HP = false;
	public static boolean DRAW_ALL = true;
	public static boolean PAUSE = false;
	
}
