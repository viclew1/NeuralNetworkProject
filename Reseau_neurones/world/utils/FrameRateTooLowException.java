package utils;

public class FrameRateTooLowException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1651151775937570742L;

	public FrameRateTooLowException(int framerate)
	{
		super("Impossible d'avoir une framerate inf�rieure � "+ framerate +" images par seconde");
	}
}
