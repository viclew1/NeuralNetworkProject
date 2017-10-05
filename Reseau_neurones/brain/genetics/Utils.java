package genetics;

import java.util.Random;

public abstract class Utils
{
	public static int chancesMutation=10;
	public static int chancesCrossOver=65;
	
	
	private static final Random r = new Random();

	public static void bubbleSort(Individu array[]) {
		int n = array.length;
		for (int m = n; m >= 0; m--)
			for (int i = 0; i < n - 1; i++) 
				if (array[i].getScore() > array[i+1].getScore())
					swapNumbers(i, i+1, array);
	}

	private static void swapNumbers(int i, int j, Object[] array) 
	{
		Object temp;
		temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public static void shuffleArray(Object[] array)
	{
		  for (int i=array.length-1 ; i>= 1 ; i--)
		  {
			  int j = r.nextInt(i+1);
			  Object temp = array[j];
			  array[j] = array[i];
			  array[i] = temp;
		  }
	}
}
