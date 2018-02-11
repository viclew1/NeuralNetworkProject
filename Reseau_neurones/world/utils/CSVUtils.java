package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class CSVUtils
{


	private static File f;
	private static Writer w;

	public static void saveEntry(double[] inputs, double[] outputs)
	{
		try
		{
				String ret = "";
				ret += outputs[0];
				for (int i = 1 ; i < outputs.length ; i++)
					ret += "," + outputs[i];
				for (int i = 0 ; i < inputs.length ; i++)
					ret += "," + inputs[i];
				ret += "\n";
				w.append(ret);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static Writer prepareSave(String name)
	{
		f = new File("csv/"+name +".csv");
		for (int i = 0 ; f.exists() ; i++)
			f = new File("csv/"+name + i + ".csv");

		try
		{
			f.createNewFile();
			w = new FileWriter(f);
			return w;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
