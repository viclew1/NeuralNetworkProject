package limitations;

import java.awt.geom.Rectangle2D;

public class DelimitationBox extends Rectangle2D.Double
{

	private static final long serialVersionUID = -7544589978257213899L;
	
	private WallDelimitation[] walls;

	public DelimitationBox(int x, int y, int w, int h)
	{
		super(x,y,w,h);
		walls = new WallDelimitation[w*4+h*4];
		int cpt=0;
		for (int i = 0 ; i < 2*w ; i ++)
		{
			walls[cpt++] = new WallDelimitation((double)i/2, 0);
			walls[cpt++] = new WallDelimitation((double)i/2, h-0.5);
		}
		for (int i = 0 ; i < 2*h ; i ++)
		{
			walls[cpt++] = new WallDelimitation(0,(double)i/2);
			walls[cpt++] = new WallDelimitation(w-0.5,(double)i/2);
		}
	}
	
	public WallDelimitation[] getWalls()
	{
		return walls;
	}

}
