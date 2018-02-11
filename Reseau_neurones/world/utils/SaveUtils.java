package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import genetics.Individu;

public class SaveUtils
{

	private static OutputStream os;
	private static File f;
	
	public static void saveBest(Individu bestOne)
	{
		try
		{
			if (bestOne!=null)
				os.write((bestOne.getName() + " : " + bestOne.getScore() + "\n" + bestOne + "\n\n").getBytes());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void prepareSave(String name)
	{
		f = new File("saves/"+name);
		for (int i = 0 ; f.exists() ; i++)
			f = new File("saves/"+name + i);
		
		try
		{
			f.createNewFile();
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
		} catch (IOException e)
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
