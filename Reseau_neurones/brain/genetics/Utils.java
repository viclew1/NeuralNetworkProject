package genetics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

public abstract class Utils
{
	public static int chancesMutation=10;
	public static int chancesCrossOver=65;

	private static OutputStream os;
	private static final File f;

	static {
		String name = "Best_Creatures_"+Date.from(Instant.now())+".txt";
		name = name.replace(' ', '_');
		name = name.replace(':', '_');

		f = new File(name);
		try
		{
			f.createNewFile();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static final Random r = new Random();

	public static void bubbleSortAsc(Individu array[]) 
	{
		int n = array.length;
		for (int m = n; m >= 0; m--)
			for (int i = 0; i < n - 1; i++) 
				if (array[i].getScore() > array[i+1].getScore())
					swapNumbers(i, i+1, array);
	}

	public static void bubbleSortDesc(Individu array[]) 
	{
		int n = array.length;
		for (int m = n; m >= 0; m--)
			for (int i = 0; i < n - 1; i++) 
				if (array[i].getScore() < array[i+1].getScore())
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

	public static void saveBest(Individu deadOne)
	{
		try
		{
			if (deadOne!=null)
				os.write((deadOne.getName() + " : " + deadOne.getScore() + "\n" + deadOne + "\n\n").getBytes());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void initSave()
	{
		try
		{
			os = new FileOutputStream(f);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static void endSave()
	{
		try
		{
			os.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
